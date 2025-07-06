package net.lucent.easygui.interfaces;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public interface ITextureData {

    int getTextureWidth();
    int getTextureHeight();

    default int getWidth(){
        return  getTextureWidth();
    }
    default int getHeight(){
        return getTextureHeight();
    }

    ResourceLocation getTexture();

    default void renderTexture(GuiGraphics guiGraphics){
        renderTexture(guiGraphics,0,0);
    }

    default void renderTexture(GuiGraphics guiGraphics,int x, int y){
        renderTexture(guiGraphics,x,y,0,0);
    }
    default void renderTexture(GuiGraphics guiGraphics,int x, int y,int u,int v){
        renderTexture(guiGraphics,x,y,u,v,getTextureWidth(),getTextureHeight());
    }
    default void renderTexture(GuiGraphics guiGraphics,int x, int y,int u,int v,int uOffset,int vOffset){
        if(getTexture() == null) return;
        System.out.println("rendering_texture");
        guiGraphics.blit(getTexture(),x,y,u,v,uOffset,vOffset,getTextureWidth(), getTextureHeight());
    }


}
