package net.Lucent.EasyGui.elements.controls.buttons;

import net.Lucent.EasyGui.holders.EasyGuiEventHolder;
import net.minecraft.client.gui.GuiGraphics;

public class TextureButton extends AbstractButton{
    public TextureButton(EasyGuiEventHolder eventHandler, int x, int y, int width, int height) {
        super(eventHandler, x, y, width, height);
    }

    @Override
    public void renderSelf(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {

    }
}
