package net.lucent.easygui.elements.other;

import com.google.gson.JsonObject;
import net.lucent.easygui.elements.BaseRenderable;
import net.lucent.easygui.holders.EasyGuiEventHolder;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.lucent.easygui.interfaces.ITextureData;
import net.lucent.easygui.templating.IRenderableDeserializer;
import net.lucent.easygui.templating.actions.Action;
import net.lucent.easygui.templating.deserializers.BaseDeserializer;
import net.lucent.easygui.util.textures.TextureDataSubSection;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.EnderpearlItem;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ProgressBar extends BaseRenderable {

    public ITextureData background;
    public ITextureData barTexture;




    public boolean vertical = false;



    public double progressPercent = 0;

    public ProgressBar(){}

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


    public double getProgress(){
        return progressPercent;
    };

    public int getProgressLength(){
        if(vertical) return (int) (getHeight()*(getProgress()));
        return (int) (getWidth()*(getProgress()));
    }


    @Override
    public int getWidth() {
        if(barTexture == null) return 0;
        if(background == null) return barTexture.getWidth();

        return Math.max(background.getWidth(), barTexture.getWidth());
    }

    @Override
    public int getHeight() {
        if(barTexture == null) return 0;
        if(background == null) return barTexture.getHeight();
        return Math.max(background.getHeight(), barTexture.getHeight());
    }

    @Override
    public void renderSelf(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {

        if(background != null){
            background.renderTexture(guiGraphics);
        }
        if(vertical) barTexture.renderTexture(guiGraphics,0,0, 0,0, barTexture.getWidth(),getProgressLength());
        else barTexture.renderTexture(guiGraphics,0,0,0,0, getProgressLength(), barTexture.getHeight());
    }

    public void setBackground(ITextureData background) {

        this.background = background;
    }

    public void setBarTexture(ITextureData barTexture) {
        this.barTexture = barTexture;
    }

    public static class Deserializer extends BaseDeserializer{


        public Deserializer(Supplier<? extends ProgressBar> supplier) {
            super(supplier);
        }

        @Override
        public void parseWidth(String expr) {

        }

        @Override
        public void parseHeight(String expr) {

        }

        @Override
        public void buildRenderable(IEasyGuiScreen screen, IRenderableDeserializer parent, JsonObject obj) {
            super.buildRenderable(screen, parent, obj);

            ((ProgressBar) getRenderable()).setBackground( parseTexture("background_texture",obj));
            ((ProgressBar) getRenderable()).setBarTexture(  parseTexture("progress_texture",obj));
            ((ProgressBar) getRenderable()).vertical =  getOrDefault(obj,"is_vertical",false);

        }

        @Override
        public @NotNull IRenderableDeserializer createInstance() {
            return new Deserializer((Supplier<? extends ProgressBar>) supplier);
        }
    }

}
