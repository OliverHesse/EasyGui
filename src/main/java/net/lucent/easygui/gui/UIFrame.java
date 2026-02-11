package net.lucent.easygui.gui;

import net.lucent.easygui.gui.events.type.EasyEvent;
import net.lucent.easygui.gui.events.EasyEvents;
import net.lucent.easygui.gui.events.EventHandler;
import net.lucent.easygui.gui.events.type.EasyKeyEvent;
import net.lucent.easygui.gui.events.type.EasyMouseEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UIFrame {

    private RenderableElement root;

    private Matrix4f baseTransform;
    private boolean useMinecraftScale = true;

    private final HashMap<String,RenderableElement> idMap = new HashMap<>();
    private final HashMap<String, List<RenderableElement>> classMap = new HashMap<>();

    private RenderableElement highestPriorityHoveredElement;
    private RenderableElement focusedElement;

    private String carriedItemSlot;

    public UIFrame(){
        updateBaseTransform();
        updateDimensions(Minecraft.getInstance());
    }

    public double getMinecraftScale(){
        return Minecraft.getInstance().getWindow().getGuiScale();
    }

    public void updateBaseTransform(){
        baseTransform = new Matrix4f();
        if(!isUsingMinecraftScale()) baseTransform.scale((float) (1/getMinecraftScale()));

    }

    public void updateDimensions(Minecraft minecraft){
        EventHandler.runForAllChildren(new EasyEvent(null,EasyEvents.FRAME_DIMENSIONS_CHANGE_EVENT),this);

    }


    public void onWindowResize(Minecraft minecraft){
        updateBaseTransform();
        updateDimensions(minecraft);
    }

    //======================= SETTERS =======================

    public void setUseMinecraftScale(boolean useMinecraftScale) {
        this.useMinecraftScale = useMinecraftScale;
        updateBaseTransform();
        updateDimensions(Minecraft.getInstance());
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

    public RenderableElement getRoot(){return root;}

    public boolean isUsingMinecraftScale(){return this.useMinecraftScale;}

    public int getHeight(){
        return isUsingMinecraftScale() ? Minecraft.getInstance().getWindow().getGuiScaledHeight() : Minecraft.getInstance().getWindow().getHeight();

    }
    public int getWidth(){
        return isUsingMinecraftScale() ? Minecraft.getInstance().getWindow().getGuiScaledWidth() : Minecraft.getInstance().getWindow().getWidth();
    }

    /*
        because i am not calculating the elements transform using the actual guiGraphics pose
        this is needed so it actually includes the scale in the pose
     */
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
                guiGraphics.pose().mulPose(element.getTransformMatrix());
                guiGraphics.pose().translate(0,0,element.getTotalZIndex());
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
            guiGraphics.pose().mulPose(element.getCompletePositioningMatrix());
            guiGraphics.pose().mulPose(element.getTransformMatrix());
            guiGraphics.pose().translate(0,0,element.getTotalZIndex());
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
            guiGraphics.pose().mulPose(element.getCompletePositioningMatrix());
            guiGraphics.pose().mulPose(element.getTransformMatrix());

            guiGraphics.pose().translate(0,0,element.getTotalZIndex());
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
            guiGraphics.pose().mulPose(renderable.getTransformMatrix());
            guiGraphics.pose().translate(0,0,renderable.getTotalZIndex());
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
        EasyEvent globalEvent = EasyMouseEvent.MouseDownEvent(highestPriorityHoveredElement,mouseX,mouseY,button,true);
        EventHandler.runForAllChildren(globalEvent,this);

        if(highestPriorityHoveredElement == null) return false;
        EasyEvent targetedEvent = EasyMouseEvent.MouseDownEvent(highestPriorityHoveredElement,mouseX,mouseY,button);
        EventHandler.runEvent(targetedEvent);
        return targetedEvent.didRunListener() || globalEvent.didRunListener();
    }
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        EasyEvent globalEvent = EasyMouseEvent.MouseUpEvent(highestPriorityHoveredElement,mouseX,mouseY,button,true);
        EventHandler.runForAllChildren(globalEvent,this);
        if(highestPriorityHoveredElement == null) return false;
        EasyEvent targetedEvent = EasyMouseEvent.MouseUpEvent(highestPriorityHoveredElement,mouseX,mouseY,button);
        EventHandler.runEvent(targetedEvent);

        return targetedEvent.didRunListener() || globalEvent.didRunListener();
    }
    public void mouseMoved(double mouseX, double mouseY){
        EasyEvent globalEvent = EasyMouseEvent.MouseMoveEvent(highestPriorityHoveredElement,mouseX,mouseY,true);
        EventHandler.runForAllChildren(globalEvent,this);
        if(highestPriorityHoveredElement == null) return;

        EasyEvent targetedEvent = EasyMouseEvent.MouseMoveEvent(highestPriorityHoveredElement,mouseX,mouseY);
        EventHandler.runEvent(targetedEvent);



    }

    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        EasyEvent globalEvent = EasyMouseEvent.MouseDragEvent(highestPriorityHoveredElement,mouseX,mouseY,button,dragX,dragY,true);
        EventHandler.runForAllChildren(globalEvent,this);

        if(highestPriorityHoveredElement == null) return false;
        EasyEvent targetedEvent = EasyMouseEvent.MouseDragEvent(highestPriorityHoveredElement,mouseX,mouseY,button,dragX,dragY);
        EventHandler.runEvent(targetedEvent);
        return targetedEvent.didRunListener() || globalEvent.didRunListener();
    }


    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        EasyEvent globalEvent = EasyMouseEvent.MouseScrollEvent(highestPriorityHoveredElement,mouseX,mouseY,scrollX,scrollY,true);
        EventHandler.runForAllChildren(globalEvent,this);
        if(highestPriorityHoveredElement == null) return false;
        EasyEvent targetedEvent = EasyMouseEvent.MouseScrollEvent(highestPriorityHoveredElement,mouseX,mouseY,scrollX,scrollY);
        EventHandler.runEvent(targetedEvent);
        return targetedEvent.didRunListener() || globalEvent.didRunListener();
    }

    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        EasyEvent globalEvent = EasyKeyEvent.KeyDownEvent(highestPriorityHoveredElement,keyCode,scanCode,modifiers,true);
        EventHandler.runForAllChildren(globalEvent,this);

        if(focusedElement == null) return false;
        EasyEvent targetedEvent = EasyKeyEvent.KeyDownEvent(highestPriorityHoveredElement,keyCode,scanCode,modifiers);
        EventHandler.runEvent(targetedEvent);
        return targetedEvent.didRunListener() || globalEvent.didRunListener();
    }


    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        EasyEvent globalEvent = EasyKeyEvent.KeyUpEvent(highestPriorityHoveredElement,keyCode,scanCode,modifiers,true);
        EventHandler.runForAllChildren(globalEvent,this);

        if(focusedElement == null) return false;
        EasyEvent targetedEvent = EasyKeyEvent.KeyUpEvent(highestPriorityHoveredElement,keyCode,scanCode,modifiers);
        EventHandler.runEvent(targetedEvent);
        return targetedEvent.didRunListener() || globalEvent.didRunListener();
    }


}
