package net.lucent.easygui.util;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class TextureDataSubSection extends TextureData{

    private final int u;
    private final int v;
    private final int uOffset;
    private final int vOffset;



    public TextureDataSubSection(ResourceLocation texture, int textureWidth, int textureHeight,int u, int v,int uOffset,int vOffset){
        super(texture, textureWidth, textureHeight);
        this.u = u;
        this.v = v;
        this.uOffset = uOffset;
        this.vOffset = vOffset;
    }

    @Override
    public int getWidth() {
        return uOffset;
    }

    @Override
    public int getHeight() {
        return vOffset;
    }

    @Override
    public void renderTexture(GuiGraphics guiGraphics, int x, int y) {
        this.renderTexture(guiGraphics,x,y,u,v);
    }

    @Override
    public void renderTexture(GuiGraphics guiGraphics, int x, int y, int u, int v) {
        super.renderTexture(guiGraphics, x, y, u, v,uOffset,vOffset);
    }
}
