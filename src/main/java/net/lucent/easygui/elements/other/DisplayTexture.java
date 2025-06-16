package net.lucent.easygui.elements.other;

import net.lucent.easygui.elements.BaseRenderable;
import net.lucent.easygui.holders.EasyGuiEventHolder;
import net.lucent.easygui.interfaces.ITextureData;
import net.minecraft.client.gui.GuiGraphics;

public class DisplayTexture extends BaseRenderable {

    private ITextureData textureData;
    public DisplayTexture(EasyGuiEventHolder eventHandler,ITextureData textureData,int x,int y) {
        super(eventHandler);
        this.textureData = textureData;
        setX(x);
        setY(y);
    }


    public ITextureData getTextureData(){return textureData;}
    public void setTextureData(ITextureData textureData){this.textureData = textureData;}

    @Override
    public void renderSelf(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.textureData.renderTexture(guiGraphics,getX(),getY());
    }
}
