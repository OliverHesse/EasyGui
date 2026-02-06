package net.lucent.easygui.screen;

import net.lucent.easygui.gui.UIFrame;
import net.lucent.easygui.gui.events.EasyEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;

public class EasyScreen extends Screen implements IEasyScreen{
    UIFrame frame = new UIFrame();


    public EasyScreen(Component title,UIFrame frame){
        super(title);
        this.frame = frame;
    }
    public EasyScreen(Component title) {
        super(title);
    }


    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
    }
    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        frame.run(guiGraphics,mouseX,mouseY,partialTick);
    }


    @Override
    protected void init() {
        frame.onWindowResize(Minecraft.getInstance());
    }

    @Override
    public void resize(Minecraft minecraft, int width, int height) {
        frame.onWindowResize(minecraft);
    }
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        boolean result = getUIFrame().mouseClicked(mouseX,mouseY,button);
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

    @Override
    public UIFrame getUIFrame() {
        return frame;
    }
}
