package net.lucent.easygui.elements.other;

import com.google.gson.JsonObject;
import net.lucent.easygui.elements.BaseRenderable;
import net.lucent.easygui.holders.EasyGuiEventHolder;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.lucent.easygui.templating.IRenderableDeserializer;
import net.lucent.easygui.templating.deserializers.BaseDeserializer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.ContainerEventHandler;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.beans.EventHandler;
import java.util.function.Supplier;

public class Label extends SquareRenderable {

    public int textColor;
    public boolean centered;

    public Component text;
    public Font font = Minecraft.getInstance().font;

    /**
     * extra text gets culled
     */

    public Label(){
    }

    private Label(IEasyGuiScreen easyGuiScreen, Font font, Component text, int x, int y, int width, int height, boolean centered, int textColor){
        super(easyGuiScreen);
        this.font = font;
        this.text = text;
        setY(y);
        setX(x);
        setWidth(width);
        setHeight(height);
        this.centered = centered;

        this.textColor = textColor;
    }

    public boolean isCentered() {
        return centered;
    }



    public Font getFont() {
        return font;
    }



    @Override
    public void renderSelf(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if(centered) guiGraphics.pose().translate((float) -getWidth() /2, (float) -getHeight() /2,0);
        guiGraphics.drawWordWrap(getFont(),text,0,0,getWidth(),textColor);

    }

    public static class Builder{

        private IEasyGuiScreen easyGuiScreen = null;

        private  int textColor = -16777216;
        private  boolean centered = false;
        private  Integer width;
        private  Integer height;
        private  Component text = Component.literal("");
        private  Font font = Minecraft.getInstance().font;
        private  boolean cull = false;
        private  boolean useCustomScaling = false;
        private  double customScaling = 1;
        private  int x = 0;
        private  int y = 0;

        public  Builder screen(IEasyGuiScreen easyGuiScreen){this.easyGuiScreen = easyGuiScreen; return this;}
        public  Builder textColor(int color){textColor = color; return  this;}
        public  Builder textColor(Color color){textColor = color.getRGB(); return  this;}
        public  Builder centered(boolean centered){this.centered = centered; return  this;}
        public  Builder width(int width){this.width = width; return this;}
        public  Builder height(int height){this.height = height; return this;}
        public  Builder text(Component component){this.text = component; return this;}
        public  Builder text(String text){this.text = Component.literal(text); return this;}
        public  Builder translatableText(String key){this.text = Component.translatable(key); return this;}
        public  Builder font(Font font){this.font = font; return this;}
        public  Builder cull(boolean cull){this.cull = cull;return this;}
        public Builder customScaling(double scale){
            useCustomScaling = true;
            customScaling = scale;return this;}
        public  Builder x(int x){this.x = x;return this;}
        public  Builder y(int y){this.y = y;return this;}

        public Label build(){

            if(easyGuiScreen == null){
                throw new IllegalArgumentException("Label must have a valid screen");
            }

            if(width == null){
                width = font.width(text);
            }
            if(height == null){
                height = font.lineHeight;
            }

            Label label = new Label(easyGuiScreen,font,text,x,y,width,height,centered,textColor);
            label.useCustomScaling = useCustomScaling;
            label.setCustomScale(customScaling);
            label.setCull(cull);
            return label;

        }



    }

    public static class Deserializer extends BaseDeserializer{

        public Deserializer(Supplier<? extends Label> supplier) {
            super(supplier);
        }

        @Override
        public void parseHeight(String expr) {
            Font font = Minecraft.getInstance().font;
            if(expr.equals("0")){
                getRenderable().setHeight(font.lineHeight);
            }else{
                super.parseHeight(expr);
            }
        }

        @Override
        public void parseWidth(String expr) {

            Font font = Minecraft.getInstance().font;
            if(expr.equals("0") && ((Label) getRenderable()).text != null){
                getRenderable().setWidth(font.width(((Label) getRenderable()).text));
            }else{
                super.parseWidth(expr);
            }
        }

        @Override
        public void buildRenderable(IEasyGuiScreen screen, IRenderableDeserializer parent, JsonObject obj) {
            super.buildRenderable(screen, parent, obj);
            ((Label) getRenderable()).text = parseComponent("text",obj);
            ((Label) getRenderable()).textColor = getOrDefault(obj,"text_color",-16777216);
            ((Label) getRenderable()).centered = getOrDefault(obj,"centered",false);

            parseWidth(getOrDefault(obj,"width","0"));
            parseHeight(getOrDefault(obj,"height","0"));
        }

        @Override
        public @NotNull IRenderableDeserializer createInstance() {
            return new Deserializer((Supplier<? extends Label>) supplier);
        }
    }

}
