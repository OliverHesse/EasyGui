package net.lucent.easygui.gui.textures;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public record TextureDataSubsection(
        ResourceLocation texture,
        int textureWidth,
        int textureHeight,
        int u,
        int v,
        int width,
        int height
) implements ITextureData{

    @Override
    public int getTextureWidth() {
        return textureWidth;
    }

    @Override
    public int getTextureHeight() {
        return textureHeight;
    }

    @Override
    public int getWidth() {
        return width();
    }

    @Override
    public int getHeight() {
        return height();
    }

    @Override
    public void render(GuiGraphics guiGraphics) {
        guiGraphics.blit(texture,0,0,u,v,getWidth(),getHeight(),getTextureWidth(),getTextureHeight());

    }

    @Override
    public void renderAt(GuiGraphics guiGraphics, int x, int y) {
        guiGraphics.blit(texture,x,y,0,0,getTextureWidth(),getTextureHeight(),getTextureWidth(),getTextureHeight());
    }

    @Override
    public void render(GuiGraphics guiGraphics, int widthOverride, int heightOverride){
        guiGraphics.blit(texture,0,0,u,v,widthOverride,heightOverride,getTextureWidth(),getTextureHeight());

    }

    @Override
    public void renderAt(GuiGraphics guiGraphics, int x, int y, int widthOverride, int heightOverride) {
        guiGraphics.blit(texture,x,y,0,0,widthOverride,heightOverride,getTextureWidth(),getTextureHeight());
    }
}
