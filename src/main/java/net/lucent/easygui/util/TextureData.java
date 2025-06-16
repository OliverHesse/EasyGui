package net.lucent.easygui.util;

import net.lucent.easygui.interfaces.ITextureData;
import net.minecraft.resources.ResourceLocation;

public class TextureData implements ITextureData {

    private final ResourceLocation texture;
    private final int textureWidth;
    private final int textureHeight;

    public TextureData(ResourceLocation texture,int textureWidth,int textureHeight){
        this.texture = texture;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
    }

    @Override
    public int getTextureWidth() {
        return textureWidth;
    }

    @Override
    public int getTextureHeight() {
        return textureHeight;
    }

    @Override
    public ResourceLocation getTexture() {
        return texture;
    }
}
