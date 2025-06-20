package net.lucent.easygui.elements.other;

import net.lucent.easygui.elements.BaseRenderable;
import net.lucent.easygui.holders.EasyGuiEventHolder;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.lucent.easygui.interfaces.ITextureData;
import net.minecraft.client.gui.GuiGraphics;

public abstract class ProgressBar extends BaseRenderable {

    public ITextureData background;
    public ITextureData barTexture;

    public boolean vertical = false;

    public int x;
    public int y;

    public ProgressBar(IEasyGuiScreen easyGuiScreen, ITextureData progressBarTexture, int x, int y) {
        super(easyGuiScreen);
        setX(x);
        setY(y);
        this.barTexture = progressBarTexture;

    }

    public ProgressBar(IEasyGuiScreen easyGuiScreen, ITextureData progressBarTexture, boolean vertical, int x, int y) {
        this(easyGuiScreen,progressBarTexture,x,y);
        this.barTexture = progressBarTexture;
        this.vertical = vertical;
    }
    public ProgressBar(IEasyGuiScreen easyGuiScreen, ITextureData progressBarTexture, ITextureData background, boolean vertical, int x, int y){
        this(easyGuiScreen,progressBarTexture,vertical,x,y);
        this.background = background;
    }
    public ProgressBar(IEasyGuiScreen easyGuiScreen, ITextureData progressBarTexture, ITextureData background, int x, int y){
        this(easyGuiScreen,progressBarTexture,x,y);
        this.background = background;
    }


    public abstract double getProgress();

    public int getProgressLength(){
        if(vertical) return (int) (getHeight()*(getProgress()));
        return (int) (getWidth()*(getProgress()));
    }


    @Override
    public int getWidth() {
        return background.getWidth();
    }

    @Override
    public int getHeight() {
        return background.getWidth();
    }

    @Override
    public void renderSelf(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {

        if(background != null){
            background.renderTexture(guiGraphics);
        }
        if(vertical) barTexture.renderTexture(guiGraphics,0,0,0,0, barTexture.getWidth(),getProgressLength());
        else barTexture.renderTexture(guiGraphics,0,0,0,0, getProgressLength(), barTexture.getHeight());
    }
}
