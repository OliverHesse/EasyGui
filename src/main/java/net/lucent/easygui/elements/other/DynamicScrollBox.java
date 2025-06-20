package net.lucent.easygui.elements.other;

import net.lucent.easygui.interfaces.ContainerRenderable;
import net.lucent.easygui.interfaces.IEasyGuiScreen;

public class DynamicScrollBox extends AbstractScrollBox{
    private double  maxScrollY;
    private double maxScrollX;
    public DynamicScrollBox(IEasyGuiScreen easyGuiScreen, int x, int y, int width, int height) {
        super(easyGuiScreen, x, y, width, height);
    }

    @Override
    public double getScrollHeight() {
        return maxScrollY;
    }

    @Override
    public void addChild(ContainerRenderable child) {
        super.addChild(child);
        //get largest yValue pos child
        if(child.getY()+child.getHeight() > getScrollHeight()){
            maxScrollY =Math.max(0,child.getY()+child.getHeight()-getInnerHeight());
        }
        if(child.getX()+getWidth() > getScrollWidth())   maxScrollX =Math.max(0,child.getX()+child.getWidth()-getInnerWidth());
    }

    @Override
    public double getScrollWidth() {

        return maxScrollX;
    }

    @Override
    public double getScrollRate() {
        return 9;
    }
}
