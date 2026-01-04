package net.lucent.easygui.screen;

import net.lucent.easygui.gui.UIFrame;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;

public class EasyScreen extends Screen implements IEasyScreen{
    UIFrame frame;

    protected EasyScreen(Component title) {
        super(title);
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
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
    }
}
