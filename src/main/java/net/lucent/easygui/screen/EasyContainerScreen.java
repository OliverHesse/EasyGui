package net.lucent.easygui.screen;

import net.lucent.easygui.gui.UIFrame;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class EasyContainerScreen<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> implements IEasyScreen {

    UIFrame frame = new UIFrame();

    public EasyContainerScreen(T menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    public UIFrame getUIFrame() {
        return frame;
    }

    @Override
    protected void renderTooltip(GuiGraphics guiGraphics, int x, int y) {

        if(!getUIFrame().renderItemTooltip(guiGraphics,this,x,y)) super.renderTooltip(guiGraphics, x, y);

    }

    @Override
    protected void renderSlot(GuiGraphics guiGraphics, Slot slot) {
        if(getUIFrame().renderItemSlot(guiGraphics,slot)){
            super.renderSlot(guiGraphics, slot);
            guiGraphics.pose().popPose();
            return;
        }
        super.renderSlot(guiGraphics, slot);
    }

    @Override
    protected void renderSlotHighlight(GuiGraphics guiGraphics, Slot slot, int mouseX, int mouseY, float partialTick) {
        if(getUIFrame().renderHoveredItemSlot(guiGraphics,slot,mouseX,mouseY,partialTick)){
            super.renderSlotHighlight(guiGraphics, slot, mouseX, mouseY, partialTick);
            guiGraphics.pose().popPose();
            return;
        }
        super.renderSlotHighlight(guiGraphics, slot, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean isHovering(Slot slot, double mouseX, double mouseY) {
        Boolean result = getUIFrame().isHoveringSlot(slot,mouseX,mouseY);
        if(result == null) return super.isHovering(slot,mouseX,mouseY);
        return result;
    }

    @Override
    public void renderFloatingItem(GuiGraphics guiGraphics, ItemStack stack, int x, int y, String text) {
        if(getUIFrame().renderFloatingItem(guiGraphics,stack,x,y,text)){

            super.renderFloatingItem(guiGraphics, stack, x, y, text);
            guiGraphics.pose().popPose();
            return;
        }
        super.renderFloatingItem(guiGraphics, stack, x, y, text);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {

    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        getUIFrame().run(guiGraphics,mouseX,mouseY,partialTick);
    }


    @Override
    protected void init() {
        getUIFrame().onWindowResize(Minecraft.getInstance());
    }

    @Override
    public void resize(Minecraft minecraft, int width, int height) {
        getUIFrame().onWindowResize(minecraft);
    }
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        boolean result = getUIFrame().mouseClicked(mouseX,mouseY,button);
        if(menu.getCarried() == ItemStack.EMPTY && getUIFrame().getCarriedItemSlot() != null ) getUIFrame().setCarriedItemSlot(null);
        else if(menu.getCarried() != ItemStack.EMPTY && hoveredSlot != null && getUIFrame().hasRenderableLinkedToSlot(hoveredSlot.index)){
            getUIFrame().setCarriedItemSlot(hoveredSlot.index);
        }
        return  super.mouseClicked(mouseX,mouseY,button) || result;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        boolean result = getUIFrame().mouseReleased(mouseX,mouseY,button);
        return  super.mouseReleased(mouseX,mouseY,button) || result;
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        super.mouseMoved(mouseX, mouseY);
        getUIFrame().mouseMoved(mouseX,mouseY);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        boolean result = getUIFrame().mouseDragged(mouseX,mouseY,button,dragX,dragY);
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY) || result;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        boolean result = getUIFrame().mouseScrolled(mouseX,mouseY,scrollX,scrollY);
        return super.mouseScrolled(mouseX,mouseY,scrollX,scrollY) || result;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        boolean result = getUIFrame().keyPressed(keyCode,scanCode,modifiers);
        return super.keyPressed(keyCode, scanCode, modifiers) || result;
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        boolean result = getUIFrame().keyReleased(keyCode,scanCode,modifiers);
        return super.keyReleased(keyCode, scanCode, modifiers) || result;
    }

}
