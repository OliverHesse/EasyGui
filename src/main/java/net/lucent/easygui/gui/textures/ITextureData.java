package net.lucent.easygui.gui.textures;

import net.minecraft.client.gui.GuiGraphics;

public interface ITextureData {


    int getTextureWidth();
    int getTextureHeight();

    int getWidth();
    int getHeight();

    void render(GuiGraphics guiGraphics);
}
