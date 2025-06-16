package net.lucent.easygui.elements.other;

import net.lucent.easygui.elements.BaseRenderable;
import net.lucent.easygui.holders.EasyGuiEventHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.ContainerEventHandler;
import net.minecraft.network.chat.Component;

public class Label extends BaseRenderable {

    public int textColor;
    public boolean centered;
    public int width;
    public int height;
    public Component text;
    public Font font;

    /**
     * extra text gets culled
     */
    public boolean cull;

    public Label(EasyGuiEventHolder eventHandler,Font font, Component string,int x, int y) {
        this(eventHandler,font,string,x,y, font.width(string),font.lineHeight, false);
    }

    public Label(EasyGuiEventHolder eventHandler,Font font,Component string,int x, int y,int width,int height) {
        this(eventHandler,font,string,x,y, width,height, false);
    }
    public Label(EasyGuiEventHolder eventHandler, Font font,Component string,int x, int y,boolean centered) {
        this(eventHandler,font,string,x,y, font.width(string),font.lineHeight, centered);
    }
    public Label(EasyGuiEventHolder eventHandler,Font font, Component string,int x, int y,int width,int height,boolean centered) {
        super(eventHandler);
        this.width = width;
        this.height = height;
        setX(x);
        setY(y);
        setCentered(centered);
        text = string;
        setFont(font);
    }

    public void setCentered(boolean centered){
        this.centered = centered;
    }
    public boolean isCentered() {
        return centered;
    }

    public void setText(Component string){
        text= string;
    }
    public void setTextColor(int color){
        textColor = color;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font){
        this.font = font;
    }
    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void setRenderScale(GuiGraphics guiGraphics) {
        super.setRenderScale(guiGraphics);
        if(cull){guiGraphics.enableScissor(0,0,getScaledWidth(),getScaledHeight());}
    }

    @Override
    public void resetRenderScale(GuiGraphics guiGraphics) {
        super.resetRenderScale(guiGraphics);
        if(cull){guiGraphics.disableScissor();}
    }

    @Override
    public void renderSelf(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if(centered) guiGraphics.pose().translate((float) -getWidth() /2, (float) -getHeight() /2,0);
        guiGraphics.drawWordWrap(getFont(),text,0,0,getWidth(),textColor);

    }
}
