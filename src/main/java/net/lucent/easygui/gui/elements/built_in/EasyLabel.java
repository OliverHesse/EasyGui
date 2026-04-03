package net.lucent.easygui.gui.elements.built_in;

import net.lucent.easygui.gui.RenderableElement;
import net.lucent.easygui.gui.UIFrame;
import net.lucent.easygui.gui.layout.positioning.Positioning;
import net.lucent.easygui.gui.layout.positioning.rules.IPositioningRule;
import net.lucent.easygui.gui.layout.positioning.rules.PositioningRules;
import net.lucent.easygui.gui.layout.transform.Transform;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.util.FormattedCharSequence;

/*
    split into 2 elements essentially, the container, and the text
    you can set the container to always match the text,
    OR you can set the container and then set up different positioning sand scale for the text
 */
public class EasyLabel extends RenderableElement {
    public enum TextPositionRule{
        START,
        CENTER,
        END
    }
    private TextPositionRule textPositioningX = TextPositionRule.START;
    private TextPositionRule textPositioningY = TextPositionRule.START;
    private float textScale = 1;

    private boolean fitWidth = true;
    private boolean fitHeight = true;

    private int maxHeight;
    private boolean useMaxHeight = false;
    private int maxWidth;
    private boolean useMaxWidth = false;

    private Component text;

    private Font font = Minecraft.getInstance().font;

    public EasyLabel(UIFrame frame) {
        super(frame);
        setShouldCull(true);

    }

    public int getTextX(FormattedCharSequence charSequence){
        if(textPositioningX == TextPositionRule.START) return 0;
        else if(textPositioningX == TextPositionRule.CENTER){
            return getWidth()/2- font.width(charSequence)/2;
        }
        else{
            return getWidth()-font.width(charSequence);
        }
    }
    public int getTextStartHeight(int lines){
        if(textPositioningX == TextPositionRule.START) return 0;
        else if(textPositioningX == TextPositionRule.CENTER){
            return getHeight()/2- lines*font.lineHeight/2;
        }
        else{
            return getWidth()-lines*font.lineHeight;
        }
    }
    public void setTextPositioningX(TextPositionRule textPositioningX) {
        this.textPositioningX = textPositioningX;
    }
    public void setTextPositioningY(TextPositionRule textPositioningY){
        this.textPositioningY=textPositioningY;
    }

    public void renderCharSequence(GuiGraphics guiGraphics, FormattedCharSequence charSequence){
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(getTextX(charSequence),0,0);
        guiGraphics.drawString(font,charSequence,0,0,0xFF000000,false);
        guiGraphics.pose().popPose();

    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.pose().pushPose();
        guiGraphics.pose().scale(textScale,textScale,0);
        //TODO set up such that if we have fixed size dont scale with text, otherwise scale with text
        guiGraphics.pose().translate(0,getTextStartHeight(font.split(text,(int)(getWidth()/textScale)).size())/textScale,0);

        for (FormattedCharSequence charSequence : font.split(text,getWidth())){
            renderCharSequence(guiGraphics,charSequence);
            guiGraphics.pose().translate(0,font.lineHeight,0);
        }

        guiGraphics.pose().popPose();


    }

    //======================== GETTERS ===========================
    @Override
    public int getHeight() {
        int newHeight = 0;
        if(fitHeight){
            newHeight=  font.split(text,getWidth()).size()*font.lineHeight;
        }else newHeight =  super.getHeight();
        if(useMaxHeight){
            return Math.min(newHeight,maxHeight);
        }
        return newHeight;
    }

    @Override
    public int getWidth() {
        int newWidth = 0;
        if(fitWidth){
            for(FormattedCharSequence sequence : font.split(text,Integer.MAX_VALUE)){
                if(font.width(sequence) > newWidth) newWidth = font.width(sequence);
            }
        } else newWidth = super.getWidth();
        if(useMaxWidth){
            return Math.min(newWidth,maxWidth);
        }
        return newWidth;
    }

    //======================= SETTERS =======================



    @Override
    public void setHeight(int height) {
        super.setHeight(height);
        fitHeight = false;
    }

    @Override
    public void setWidth(int width) {
        super.setWidth(width);
        fitWidth = false;
    }

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
        useMaxHeight = true;
    }

    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
        useMaxWidth = true;
    }

    public void setUseMaxHeight(boolean use){
        useMaxHeight=use;
    }
    public void setUseMaxWidth(boolean use){
        useMaxWidth=use;
    }

    public void setFitWidth(boolean fit){
        this.fitWidth = fit;

    }
    public void setFitHeight(boolean fit){
        this.fitHeight =fit;
    }
    public void setTextScale(float scale) {
        this.textScale = scale;
    }

    public void setText(Component text) {
        this.text = text;
    }
}
