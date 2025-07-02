package net.lucent.easygui.util.textures;

import net.lucent.easygui.interfaces.ITextureData;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

//make all layers the same size
public class LayeredTextureData implements ITextureData {

    List<ITextureData> layers;

    public LayeredTextureData(List<ITextureData> layers){
        this.layers = layers;
    }

    @Override
    public int getTextureWidth() {
        if(layers.isEmpty()) return 0;
        return layers.getFirst().getTextureWidth();
    }

    @Override
    public int getTextureHeight() {
        if(layers.isEmpty()) return 0;
        return layers.getFirst().getTextureHeight();
    }

    @Override
    public ResourceLocation getTexture() {
        return null;
    }

    @Override
    public void renderTexture(GuiGraphics guiGraphics) {
        for (ITextureData layer:layers){
            layer.renderTexture(guiGraphics);
        }
    }
}
