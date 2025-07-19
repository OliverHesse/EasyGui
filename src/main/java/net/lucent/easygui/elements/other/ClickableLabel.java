package net.lucent.easygui.elements.other;

import net.lucent.easygui.elements.controls.buttons.ColorButton;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

import java.awt.*;

public class ClickableLabel extends ColorButton {
    public int textColor;
    public boolean centered;

    public Component text;
    public Font font = Minecraft.getInstance().font;

    /**
     * extra text gets culled
     */

    public ClickableLabel(){
    }

    private ClickableLabel(IEasyGuiScreen easyGuiScreen, Font font, Component text, int x, int y, int width, int height, boolean centered, int textColor,int hoverColor){
        super(easyGuiScreen,x,y,width,height);
        this.font = font;
        this.text = text;
        this.centered = centered;
        this.textColor = textColor;
        setDefaultColor(0);
        setFocusColor(0);
        setPressColor(hoverColor);
        setHoverColor(hoverColor);
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
        super.renderSelf(guiGraphics,mouseX,mouseY,partialTick);

    }
    public static class Builder{

        private IEasyGuiScreen easyGuiScreen = null;

        private  int textColor = -16777216;
        private  boolean centered = false;
        private  Integer width;
        private  Integer height;
        private Component text = Component.literal("");
        private Font font = Minecraft.getInstance().font;
        private  boolean cull = false;
        private  boolean useCustomScaling = false;
        private  double customScaling = 1;
        private  int x = 0;
        private  int y = 0;
        private int hoverColor;

        public Builder screen(IEasyGuiScreen easyGuiScreen){this.easyGuiScreen = easyGuiScreen; return this;}
        public Builder textColor(int color){textColor = color; return  this;}
        public Builder textColor(Color color){textColor = color.getRGB(); return  this;}
        public Builder centered(boolean centered){this.centered = centered; return  this;}
        public Builder width(int width){this.width = width; return this;}
        public Builder height(int height){this.height = height; return this;}
        public Builder text(Component component){this.text = component; return this;}
        public Builder text(String text){this.text = Component.literal(text); return this;}
        public Builder translatableText(String key){this.text = Component.translatable(key); return this;}
        public Builder font(Font font){this.font = font; return this;}
        public Builder cull(boolean cull){this.cull = cull;return this;}
        public Builder customScaling(double scale){
            useCustomScaling = true;
            customScaling = scale;return this;}
        public Builder x(int x){this.x = x;return this;}
        public Builder y(int y){this.y = y;return this;}
        public Builder hoverColor(int hoverColor){this.hoverColor = hoverColor;return this;}
        public ClickableLabel build(){

            if(easyGuiScreen == null){
                throw new IllegalArgumentException("Label must have a valid screen");
            }

            if(width == null){
                width = font.width(text);
            }
            if(height == null){
                height = font.lineHeight;
            }

            ClickableLabel label = new ClickableLabel(easyGuiScreen,font,text,x,y,width,height,centered,textColor,hoverColor);
            useCustomScaling = useCustomScaling;
            label.setCustomScale(customScaling);
            label.setCull(cull);
            return label;

        }



    }

}
