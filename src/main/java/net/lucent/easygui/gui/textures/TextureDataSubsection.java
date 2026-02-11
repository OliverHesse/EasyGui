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
        guiGraphics.blit(texture,0,0,u,v,u+getWidth(),v+getHeight(),getTextureWidth(),getTextureHeight());

    }
    public void render(GuiGraphics guiGraphics, int widthOverride,int heightOverride){
        guiGraphics.blit(texture,0,0,u,v,u+widthOverride,v+heightOverride,getTextureWidth(),getTextureHeight());

    }
}
