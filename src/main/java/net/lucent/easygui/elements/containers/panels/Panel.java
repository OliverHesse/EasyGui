package net.lucent.easygui.elements.containers.panels;

import net.lucent.easygui.elements.other.SquareRenderable;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.minecraft.client.gui.GuiGraphics;

public abstract class Panel extends SquareRenderable {

    public int borderColor = -3750202;
    public int backgroundColor = -7631989;
    public int borderWidth = 2;


    public Panel(IEasyGuiScreen easyGuiScreen,int x,int y,int width, int height) {
        super(easyGuiScreen);
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
    }



    @Override
    public void renderSelf(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if(borderWidth != 0){
            //draw borders properly
            guiGraphics.fill(-borderWidth,-borderWidth,0,getHeight()+borderWidth,borderColor);
            guiGraphics.fill(-borderWidth,-borderWidth,getWidth()+borderWidth,0,borderColor);
            guiGraphics.fill(getWidth(),-borderWidth,getWidth()+borderWidth,getHeight()+borderWidth,borderColor);
            guiGraphics.fill(-borderWidth,getHeight(),getWidth()+borderWidth,getHeight()+borderWidth,borderColor);
        }

        guiGraphics.fill(0,0,getWidth(),getHeight(),backgroundColor);
    }
}
