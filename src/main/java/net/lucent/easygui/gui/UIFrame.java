package net.lucent.easygui.gui;

import net.lucent.easygui.gui.events.EasyEvent;
import net.lucent.easygui.gui.events.EasyEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.event.ScreenEvent;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UIFrame {

    private RenderableElement root;

    private Matrix4f baseTransform;
    private int width;
    private int height;
    private boolean useMinecraftScale;

    private HashMap<String,RenderableElement> idMap = new HashMap<>();
    private HashMap<String, List<RenderableElement>> classMap = new HashMap<>();

    private RenderableElement highestPriorityHoveredElement;
    private RenderableElement focusedElement;

    private String carriedItemSlot;

    public double getMinecraftScale(){
        return Minecraft.getInstance().getWindow().getGuiScale();
    }

    public void updateBaseTransform(){
        baseTransform = new Matrix4f();
        if(isUsingMinecraftScale()) baseTransform.scale((float) (1/getMinecraftScale()));
    }

    public void updateDimensions(Minecraft minecraft){
        width = isUsingMinecraftScale() ? minecraft.getWindow().getGuiScaledWidth() : minecraft.getWindow().getWidth();
        height = isUsingMinecraftScale() ? minecraft.getWindow().getGuiScaledHeight() : minecraft.getWindow().getHeight();
    }

    public void onWindowResize(Minecraft minecraft){
        updateBaseTransform();
        updateDimensions(minecraft);
    }

    //======================= SETTERS =======================

    public void setUseMinecraftScale(boolean useMinecraftScale) {
        this.useMinecraftScale = useMinecraftScale;
        updateBaseTransform();
        //TODO trigger layout change
    }

    public void setId(String id,RenderableElement element){
        if(idMap.containsKey(id)) idMap.get(id).setId(null);
        idMap.remove(element.getId());
        idMap.put(id,element);
    }
    public void addClass(String classId, RenderableElement element){
        if(!classMap.containsKey(classId)) classMap.put(classId,new ArrayList<>());
        classMap.get(classId).add(element);
    }
    public void removeClass(String classId,RenderableElement element){
        if(!classMap.containsKey(classId)) return;
        classMap.get(classId).remove(element);
    }
    //TODO add focus enter and focus leave events
    public void trySetFocus(RenderableElement element,boolean focus){
        if(element == focusedElement && !focus){
            element = null;
            return;
        }
        if(element == focusedElement || !focus) return;

        focusedElement = element;
    }
    public RenderableElement getFocusedElement(){
        return focusedElement;
    }
    public void setCarriedItemSlot(int index){
        this.carriedItemSlot = "slot_index_"+index;
    }
    public void setCarriedItemSlot(String id){
        this.carriedItemSlot = id;
    }
    public void setRoot(RenderableElement element){
        root = element;
    }
    //======================= GETTERS =======================

    public boolean isUsingMinecraftScale(){return this.useMinecraftScale;}

    public int getHeight(){
        return height;
    }
    public int getWidth(){
        return width;
    }

    public Matrix4f getBaseTransform() {
        return baseTransform;
    }
    public RenderableElement getSlotLinkedRenderable(int index){
        return getElementById("slot_index_"+index);
    }
    public RenderableElement getElementById(String id){
        return idMap.get(id);
    }
    public String getCarriedItemSlot(){
        return carriedItemSlot;
    }
    //========================= RUNTIME ==========================
    public void run(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick){
        guiGraphics.pose().pushPose();
        guiGraphics.pose().mulPose(getBaseTransform());
        highestPriorityHoveredElement =  root.getHighestPriorityChildWithBoundedPoint(mouseX,mouseY);
        if(root != null) root.run(guiGraphics,mouseX,mouseY,partialTick);
        guiGraphics.pose().popPose();
    }
    //todo
    public boolean renderItemTooltip(GuiGraphics guiGraphics, AbstractContainerScreen<?> screen, int x, int y){
        if (screen.getMenu().getCarried().isEmpty() && screen.hoveredSlot != null && screen.hoveredSlot.hasItem()) {


            guiGraphics.pose().pushPose();
            if(hasRenderableLinkedToSlot(screen.hoveredSlot.index)){
                RenderableElement element = getSlotLinkedRenderable(screen.hoveredSlot.index);
                guiGraphics.pose().translate(x,y,0);
                guiGraphics.pose().mulPose(element.getTransform());
                if(!element.isVisible() || !element.isActive()){
                    guiGraphics.pose().popPose();
                    return false;
                }
            }

            ItemStack itemstack = screen.hoveredSlot.getItem();
            guiGraphics.renderTooltip(Minecraft.getInstance().font, screen.getTooltipFromContainerItem(itemstack), itemstack.getTooltipImage(), itemstack, 0,0);
            guiGraphics.pose().popPose();
            return true;


        }
        return false;
    }
    public boolean renderItemSlot(GuiGraphics guiGraphics,Slot slot){
        if(hasRenderableLinkedToSlot(slot.index)){
            guiGraphics.pose().pushPose();
            RenderableElement element = getSlotLinkedRenderable(slot.index);
            guiGraphics.pose().mulPose(element.getCompletePositioningTransform());
            guiGraphics.pose().mulPose(element.getTransform());
            if(!element.isVisible() || !element.isActive()){
                guiGraphics.pose().popPose();
                return false;
            }
            return true;
        }
        return false;

    }
    public boolean renderHoveredItemSlot(GuiGraphics guiGraphics,Slot slot, int mouseX, int mouseY, float partialTick){
        if(hasRenderableLinkedToSlot(slot.index)){
            guiGraphics.pose().pushPose();
            RenderableElement element = getSlotLinkedRenderable(slot.index);
            guiGraphics.pose().translate(mouseX,mouseY,0);
            guiGraphics.pose().mulPose(element.getTransform());
            if(!element.isVisible() || !element.isActive()){
                guiGraphics.pose().popPose();
                return false;
            }
            return true;
        }
        return false;
    }
    public boolean renderFloatingItem(GuiGraphics guiGraphics, ItemStack stack, int x, int y, String text){
        if(carriedItemSlot != null) {
            guiGraphics.pose().pushPose();
            RenderableElement renderable = getElementById(carriedItemSlot);

            guiGraphics.pose().translate(x,y,0);
            guiGraphics.pose().mulPose(renderable.getTransform());
            return  true;
        }
        return false;
    }
    public Boolean isHoveringSlot(Slot slot, double mouseX, double mouseY) {
        if(hasRenderableLinkedToSlot(slot.index)){
            return getSlotLinkedRenderable(slot.index).isPointBounded(mouseX,mouseY);
        }
        return null;
    }

    public void removeElementFromIdAndClasses(RenderableElement element){
        idMap.remove(element.getId());
        element.clearClasses();
    }
    //uses the slot index rather than the slot directly
    public boolean hasRenderableLinkedToSlot(int index){
        return idMap.containsKey("slot_index_"+index);
    }

    //===================================== EVENT HANDLER================================
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if(highestPriorityHoveredElement == null) return false;
        EasyEvent event = new EasyEvent(highestPriorityHoveredElement,EasyEvents.MOUSE_DOWN_EVENT,mouseX,mouseY,button);
        return event.didRunListener();
    }
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if(highestPriorityHoveredElement == null) return false;
        EasyEvent event = new EasyEvent(highestPriorityHoveredElement,EasyEvents.MOUSE_UP_EVENT,mouseX,mouseY,button);
        return event.didRunListener();
    }
    public void mouseMoved(double mouseX, double mouseY){
        if(highestPriorityHoveredElement == null) return;
        EasyEvent event = new EasyEvent(highestPriorityHoveredElement,EasyEvents.MOUSE_MOVE_EVENT,mouseX,mouseY);

    }

    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if(highestPriorityHoveredElement == null) return false;
        EasyEvent event = new EasyEvent(highestPriorityHoveredElement,EasyEvents.MOUSE_DRAG_EVENT,mouseX,mouseY,button,dragX,dragY);
        return event.didRunListener();
    }


    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if(highestPriorityHoveredElement == null) return false;
        EasyEvent event = new EasyEvent(highestPriorityHoveredElement,EasyEvents.MOUSE_SCROLL_EVENT,mouseX,mouseY,scrollX,scrollY);
        return event.didRunListener();
    }

    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if(focusedElement == null) return false;
        EasyEvent event = new EasyEvent(focusedElement,EasyEvents.KEY_DOWN_EVENT,keyCode,scanCode,modifiers);
        return event.didRunListener();
    }


    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        if(focusedElement == null) return false;
        EasyEvent event = new EasyEvent(focusedElement,EasyEvents.KEY_UP_EVENT,keyCode,scanCode,modifiers);
        return event.didRunListener();
    }


}
