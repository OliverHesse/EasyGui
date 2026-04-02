package net.lucent.easygui.gui.textures;

import net.minecraft.client.gui.GuiGraphics;

public interface ITextureData {


    int getTextureWidth();
    int getTextureHeight();

    int getWidth();
    int getHeight();

    void render(GuiGraphics guiGraphics);
    void render(GuiGraphics guiGraphics,int widthOverride,int heightOverride);
    void renderAt(GuiGraphics guiGraphics,int x, int y);
    void renderAt(GuiGraphics guiGraphics,int x,int y, int widthOverride,int heightOverride);


}
