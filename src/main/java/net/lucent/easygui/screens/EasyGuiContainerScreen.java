package net.lucent.easygui.screens;

import net.lucent.easygui.elements.containers.View;
import net.lucent.easygui.holders.EasyGuiEventHolder;
import net.lucent.easygui.interfaces.ContainerRenderable;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
//TODO ACTUALY TEST
public class EasyGuiContainerScreen<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> implements IEasyGuiScreen {
    public int windowWidth = Minecraft.getInstance() != null ? Minecraft.getInstance().getWindow().getWidth() : 0;
    public int windowHeight = Minecraft.getInstance() != null ? Minecraft.getInstance().getWindow().getHeight() : 0;;
    public double guiScale = Minecraft.getInstance() != null ? Minecraft.getInstance().getWindow().getGuiScale() : 0;;

    private final List<View> views = new ArrayList<>();

    private final EasyGuiEventHolder eventHolder = new EasyGuiEventHolder();
    private final HashMap<String,ContainerRenderable> idMap= new HashMap<>();
    private final HashMap<String,List<ContainerRenderable>> classMap = new HashMap<>();

    public EasyGuiContainerScreen(T menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }
    public EasyGuiEventHolder getEventHolder(){
        return eventHolder;
    }

    @Override
    public void addView(View view){
        views.add(view);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        for(View view : views){
            if(view.isActive()) view.render(guiGraphics,mouseX,mouseY,partialTick);
        }
    }

    @Override
    protected void init() {
        Minecraft mc = Minecraft.getInstance();

        if(mc.getWindow().getHeight() != windowHeight || windowWidth != mc.getWindow().getWidth()){
            eventHolder.SCREEN_RESIZE_EVENT.call(windowWidth,windowHeight,guiScale);
            windowWidth = mc.getWindow().getWidth();
            windowHeight = mc.getWindow().getHeight();
            guiScale = mc.getWindow().getGuiScale();


        }else if(mc.getWindow().getGuiScale() != guiScale){
            //if(guiScale == 0) eventHolder.GUI_SCALE_CHANGED_EVENT.call(mc.getWindow().getGuiScale());
            guiScale = mc.getWindow().getGuiScale();

        }
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {

    }


    @Override
    public void register(ContainerRenderable renderable) {
        eventHolder.register(renderable);
    }

    @Override
    public void unregister(ContainerRenderable renderable) {
        eventHolder.unregister(renderable);
    }

    @Override
    public void removeView(View view) {
        views.remove(view);
    }


    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        eventHolder.MOUSE_MOVED_EVENT.call(mouseX, mouseY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        eventHolder.MOUSE_SCROLL_EVENT.call(mouseX, mouseY, scrollX, scrollY);
        return super.mouseScrolled(mouseX,mouseY,scrollX,scrollY);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        eventHolder.KEY_PRESS_EVENT.call(keyCode,scanCode,modifiers);
        return super.keyPressed(keyCode,scanCode,modifiers);
    }
    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        eventHolder.KEY_RELEASED_EVENT.call(keyCode, scanCode, modifiers);
        return super.keyReleased(keyCode,scanCode,modifiers);
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        eventHolder.CHAR_TYPED_EVENT.call(codePoint,modifiers);
        return super.charTyped(codePoint,modifiers);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {

        eventHolder.CLICK_EVENT.call(mouseX, mouseY, button);
        return super.mouseClicked(mouseX,mouseY,button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {

        eventHolder.MOUSE_RELEASE_EVENT.call(mouseX, mouseY, button);
        return super.mouseReleased(mouseX,mouseY,button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {

        eventHolder.MOUSE_DRAG_EVENT.call(mouseX, mouseY, button,dragX,dragY);
        return super.mouseDragged(mouseX,mouseY,button,dragX,dragY);
    }


    @Override
    protected void containerTick() {
        super.containerTick();
        double scale = Minecraft.getInstance().options.guiScale().get();
        double mouseX = Minecraft.getInstance().mouseHandler.xpos();
        double mouseY = Minecraft.getInstance().mouseHandler.ypos();
        eventHolder.MOUSE_OVER_EVENT.call(mouseX/scale,mouseY/scale);
        eventHolder.TICK_EVENT.call();

    }
    @Override
    public void childIdSet(ContainerRenderable obj, String id) {
        idMap.put(id,obj);
    }

    @Override
    public void childClassAdded(ContainerRenderable obj, String className) {
        if(!classMap.containsKey(className)) classMap.put(className,new ArrayList<>());
        classMap.get(className).add(obj);
    }

    @Override
    public void childClassRemoved(ContainerRenderable obj, String className) {
        if(!classMap.containsKey(className)) return;

        classMap.get(className).remove(obj);
    }

    @Override
    public ContainerRenderable getElementByID(String id) {
        return idMap.get(id);
    }

    @Override
    public List<ContainerRenderable> getElementsByClassName(String className) {
        if(!classMap.containsKey(className)) return List.of();
        return List.copyOf(classMap.get(className));
    }

    @Override
    public void renderSlot(GuiGraphics guiGraphics, Slot slot) {
        super.renderSlot(guiGraphics, slot);
    }
}
