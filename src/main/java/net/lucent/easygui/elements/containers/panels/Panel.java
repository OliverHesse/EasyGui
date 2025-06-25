package net.lucent.easygui.elements.containers.panels;

import net.lucent.easygui.elements.other.SquareRenderable;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.minecraft.client.gui.GuiGraphics;
import org.joml.Vector4f;

public class Panel extends SquareRenderable {

    public int borderColor = -3750202;
    public int backgroundColor = -7631989;
    public int borderWidth = 2;

    public Panel(){

    }
    public Panel(IEasyGuiScreen easyGuiScreen,int x,int y,int width, int height) {
        super(easyGuiScreen);
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
    }



    @Override
    public void renderSelf(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        System.out.println("trying to render");
        System.out.println(getX());
        System.out.println(getGlobalScaledX());
        System.out.println((new Vector4f(0,0,0,1)).mul(getTotalPositionTransformation()).x());
        System.out.println((new Vector4f(0,0,0,1)).mul(getTotalTransformation()).x());
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
