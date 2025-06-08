package net.Lucent.EasyGui.elements.other;

import net.Lucent.EasyGui.elements.BaseRenderable;
import net.Lucent.EasyGui.holders.EasyGuiEventHolder;
import net.Lucent.EasyGui.util.TextureData;
import net.minecraft.client.gui.GuiGraphics;

public abstract class ProgressBar extends BaseRenderable {

    public TextureData background;
    public TextureData barTexture;

    public boolean vertical = false;

    public double progress = 0;

    public int x;
    public int y;

    public ProgressBar(EasyGuiEventHolder eventHandler,TextureData progressBarTexture,int x,int y) {
        super(eventHandler);
        setX(x);
        setY(y);
        this.barTexture = progressBarTexture;

    }

    public ProgressBar(EasyGuiEventHolder eventHandler,TextureData progressBarTexture,boolean vertical,int x, int y) {
        this(eventHandler,progressBarTexture,x,y);
        this.barTexture = progressBarTexture;
        this.vertical = vertical;
    }
    public ProgressBar(EasyGuiEventHolder eventHolder,TextureData progressBarTexture, TextureData background,boolean vertical,int x,int y){
        this(eventHolder,progressBarTexture,vertical,x,y);
        this.background = background;
    }
    public ProgressBar(EasyGuiEventHolder eventHolder,TextureData progressBarTexture, TextureData background,int x,int y){
        this(eventHolder,progressBarTexture,x,y);
        this.background = background;
    }

    public void updateProgress(double incr){
        progress += incr;
    }
    public void setProgress(double progress){
        this.progress = progress;
    }

    public abstract double getProgress();

    public int getProgressLength(){
        if(vertical) return (int) (getHeight()*(getProgress()));
        return (int) (getWidth()*(getProgress()));
    }


    @Override
    public int getWidth() {
        return background.textureWidth();
    }

    @Override
    public int getHeight() {
        return background.textureHeight();
    }

    @Override
    public void renderSelf(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {

        if(background != null){
            background.renderTexture(guiGraphics);
        }
        if(vertical) barTexture.renderTexture(guiGraphics,0,0,0,0, barTexture.textureWidth(),getProgressLength());
        else barTexture.renderTexture(guiGraphics,0,0,0,0, getProgressLength(), barTexture.textureHeight());
    }
}
