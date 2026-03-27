package net.lucent.easygui.gui.textures;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;

public class TextureSpriteData implements ITextureData{

    private ResourceLocation textureSprite;

    public TextureAtlasSprite getTextureAtlasSprite(){
        return Minecraft.getInstance().getTextureAtlas(ResourceLocation.withDefaultNamespace("gui")).apply(textureSprite);
    }
    @Override
    public int getTextureWidth() {
        TextureAtlasSprite sprite = getTextureAtlasSprite();
        return sprite.contents().width();
    }

    @Override
    public int getTextureHeight() {
        TextureAtlasSprite sprite = getTextureAtlasSprite();
        return sprite.contents().height();
    }

    @Override
    public int getWidth() {
        TextureAtlasSprite sprite = getTextureAtlasSprite();
        return sprite.contents().width();
    }

    @Override
    public int getHeight() {
        TextureAtlasSprite sprite = getTextureAtlasSprite();
        return sprite.contents().height();
    }

    @Override
    public void render(GuiGraphics guiGraphics) {
        guiGraphics.blitSprite(textureSprite,0,0,getWidth(),getHeight());
    }

    public void render(GuiGraphics guiGraphics,int width,int height) {
        guiGraphics.blitSprite(textureSprite,0,0,width,height);
    }
}
