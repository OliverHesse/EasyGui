package net.lucent.easygui.util.textures;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class TextureDataSubSection extends TextureData{

    public int u;
    public int v;
    public int uOffset;
    public int vOffset;


    public TextureDataSubSection(ResourceLocation texture, int textureWidth, int textureHeight,int u, int v,int uOffset,int vOffset){
        super(texture, textureWidth, textureHeight);
        this.u = u;
        this.v = v;
        this.uOffset = uOffset;
        this.vOffset = vOffset;
    }
    public TextureDataSubSection(ResourceLocation texture, int textureWidth, int textureHeight){
        super(texture, textureWidth, textureHeight);
        this.vOffset = textureHeight;
        this.uOffset = textureWidth;
        this.v = 0;
        this.u = 0;
    }

    @Override
    public int getWidth() {
        return uOffset-u;
    }

    @Override
    public int getHeight() {
        return vOffset-v;
    }

    @Override
    public void renderTexture(GuiGraphics guiGraphics, int x, int y) {
        this.renderTexture(guiGraphics,x,y,u,v);
    }

    @Override
    public void renderTexture(GuiGraphics guiGraphics, int x, int y, int u, int v) {
        renderTexture(guiGraphics, x, y, u, v,getWidth(),getHeight());
    }

    @Override
    public void renderTexture(GuiGraphics guiGraphics, int x, int y, int u, int v, int uOffset, int vOffset) {
        super.renderTexture(guiGraphics, x, y, this.u, this.v, uOffset, vOffset);
    }

    public void setU(int u) {
        this.u = u;
    }

    public void setUOffset(int uOffset) {
        this.uOffset = uOffset;
    }

    public void setV(int v) {
        this.v = v;
    }

    public void setVOffset(int vOffset) {
        this.vOffset = vOffset;
    }
}
