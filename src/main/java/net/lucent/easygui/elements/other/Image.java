package net.lucent.easygui.elements.other;

import net.lucent.easygui.elements.BaseRenderable;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.lucent.easygui.interfaces.ITextureData;
import net.minecraft.client.gui.GuiGraphics;

public class Image extends BaseRenderable {

    private ITextureData textureData;
    public Image(IEasyGuiScreen easyGuiScreen, ITextureData textureData, int x, int y) {
        super(easyGuiScreen);
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
