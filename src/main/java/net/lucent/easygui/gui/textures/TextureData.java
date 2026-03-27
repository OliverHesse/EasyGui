package net.lucent.easygui.gui.textures;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;

public record TextureData(ResourceLocation textureLocation,int textureWidth,int textureHeight) implements ITextureData {

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
        return textureWidth;
    }

    @Override
    public int getHeight() {
        return textureHeight;
    }

    @Override
    public void render(GuiGraphics guiGraphics) {
        guiGraphics.blit(textureLocation,0,0,0,0,getTextureHeight(),getTextureHeight(),getTextureHeight(),getTextureHeight());
    }
}
