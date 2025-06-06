package net.Lucent.EasyGui.util;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public record TextureData (ResourceLocation texture, int textureWidth, int textureHeight){

    public void renderTexture(GuiGraphics guiGraphics){
        renderTexture(guiGraphics,0,0);
    }

    public void renderTexture(GuiGraphics guiGraphics,int x, int y){
        renderTexture(guiGraphics,x,y,0,0);
    }
    public void renderTexture(GuiGraphics guiGraphics,int x, int y,int u,int v){
        renderTexture(guiGraphics,x,y,u,v,textureWidth,textureHeight);
    }
    public void renderTexture(GuiGraphics guiGraphics,int x, int y,int u,int v,int uOffset,int vOffset){
        guiGraphics.blit(texture,x,y,u,v,uOffset,vOffset,textureWidth,textureHeight);
    }

}
