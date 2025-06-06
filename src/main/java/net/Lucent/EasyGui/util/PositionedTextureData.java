package net.Lucent.EasyGui.util;

import net.minecraft.resources.ResourceLocation;

public record PositionedTextureData(ResourceLocation texture, int x, int y, int textureWidth, int textureHeight, TextureSubSection subSection) {
}
