package net.Lucent.EasyGui.elements.controls.other;

import net.Lucent.EasyGui.elements.other.BaseRenderable;
import net.Lucent.EasyGui.holders.EasyGuiEventHolder;
import net.Lucent.EasyGui.util.TextureData;
import net.Lucent.EasyGui.util.TextureSubSection;
import net.minecraft.client.gui.GuiGraphics;

public class DisplayTexture extends BaseRenderable {

    public TextureData textureData;
    public TextureSubSection subSection;

    public DisplayTexture(EasyGuiEventHolder eventHandler, TextureData textureData, TextureSubSection subSection) {
        super(eventHandler);
        this.textureData = textureData;
        this.subSection = subSection;
    }

    @Override
    public void renderSelf(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {

    }
}
