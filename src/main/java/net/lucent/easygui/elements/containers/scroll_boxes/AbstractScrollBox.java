package net.lucent.easygui.elements.containers.scroll_boxes;

import com.mojang.blaze3d.platform.InputConstants;
import net.lucent.easygui.elements.other.SquareRenderable;
import net.lucent.easygui.interfaces.ContainerRenderable;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.lucent.easygui.interfaces.events.Clickable;
import net.lucent.easygui.interfaces.events.MouseReleaseListener;
import net.lucent.easygui.interfaces.events.MouseScrollListener;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;

public abstract class AbstractScrollBox extends SquareRenderable implements MouseScrollListener, Clickable, MouseReleaseListener {

    private int scrollBarColor = -8026747;
    private int backgroundColor = -16777216;
    private int scrollBarThickness = 4;
    private boolean scrollBarVisible = true;


    private double xOffset;
    private double yOffset;

    private int borderColor = -8026747;
    private boolean borderVisible = true;
    private int borderWidth = 1;

    private boolean dragging;
    private int draggingAxis = 0;

    public AbstractScrollBox(IEasyGuiScreen easyGuiScreen, int x, int y, int width, int height) {
        super(easyGuiScreen);
        setX(x);
        setY(y);
        this.width = width;
        this.height = height;
        setCull(true);

    }
    public abstract double getScrollHeight();
    public abstract double getScrollWidth();
    public abstract double getScrollRate();
    @Override
    public int getWidth() {
        return getInnerWidth()+(isScrollBarYVisible() ? scrollBarThickness :0);
    }

    public int getInnerWidth(){
        return  width;
    }
    public boolean isScrollBarXVisible(){
        return scrollBarVisible && getScrollWidth() > 0;
    }
    public boolean isScrollBarYVisible(){
        return scrollBarVisible && getScrollHeight() > 0;

    }
    public void setBorderColor(int color){ this.borderColor= color;}
    public void setBorderWidth(int width){
        this.borderWidth = width;
        this.setCullBorder(width);
    }
    public void setBorderVisible(boolean visible){
        if(!visible) setCullBorder(0);
        else setCullBorder(borderWidth);
        this.borderVisible = visible;}
    public void setScrollBarVisible(boolean visible){
        scrollBarVisible  =visible;
    }

    public int getScrollBarThickness(){
        return scrollBarThickness;
    }

    @Override
    public int getHeight() {
        return getInnerHeight()+(isScrollBarXVisible() ? scrollBarThickness :0);
    }
    public int getInnerHeight(){
        return height;
    }

    public void updatePositions(double xChange,double yChange){

        for(ContainerRenderable child:getChildren()){
            child.setX((int) (child.getX()-xChange));
            child.setY((int) (child.getY()-yChange));

        }
    }

    public void setYOffset(double yOffset) {
        if(yOffset == this.yOffset) return;

        updatePositions(0,yOffset-this.yOffset);
        this.yOffset = yOffset;
    }
    public void setXOffset(double xOffset){
        if(xOffset == this.xOffset) return;
        updatePositions(xOffset-this.xOffset,0);
        this.xOffset = xOffset;
    }

    public static double getScrollAmount(double scrollAmount, double scrollDelta,double maxScrollAmount){
        return Mth.clamp(scrollAmount + scrollDelta, 0.0, maxScrollAmount);
    }

    @Override
    public void onMouseScroll(double mouseX, double mouseY, double scrollX, double scrollY) {
        if(isMouseOver(mouseX,mouseY)){
            setYOffset(getScrollAmount(yOffset,-scrollY*getScrollRate(),getScrollHeight()));
        }
    }

    private int getScrollBarHeight() {
        return Mth.clamp((int) ((this.getInnerHeight() * this.getInnerHeight()) /(this.getInnerHeight()+getScrollHeight())),
                32, this.getInnerHeight());
    }
    private int getScrollBarWidth() {
        return Mth.clamp((int) ((this.getInnerWidth() * this.getInnerWidth()) /(this.getInnerWidth()+getScrollWidth())),
                32, this.getInnerHeight());
    }

    @Override
    public void renderSelf(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if(borderVisible) guiGraphics.fill(-borderWidth,-borderWidth,getWidth()+borderWidth,getHeight()+borderWidth,borderColor);
        guiGraphics.fill(0,0,getWidth(),getHeight(),backgroundColor);
     }

    @Override
    public void renderChildren(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderChildren(guiGraphics, mouseX, mouseY, partialTick);
        renderScrollBar(guiGraphics);
    }

    public void renderScrollBar(GuiGraphics guiGraphics){
        if(isScrollBarYVisible()){

            double height = getScrollBarHeight();
            double progress = (getInnerHeight()-height)*(yOffset/getScrollHeight());
            double width = getScrollBarThickness();
            guiGraphics.fill(getInnerWidth(), (int) (progress),(int) (getInnerWidth()+width),(int) (progress+height),scrollBarColor);
        }
        if(isScrollBarXVisible()){

            double width = getScrollBarWidth();
            double progress = (getInnerWidth()-width)*(xOffset/getScrollWidth());
            double height = getScrollBarThickness();
            guiGraphics.fill((int)progress,getInnerHeight(),(int) (progress+width),(int) (getInnerHeight()+height),scrollBarColor);
        }
    }


    @Override
    public void onClick(double mouseX, double mouseY, int button, boolean clicked) {
        if(clicked){
            //check if it was the X or y
            if(screenToLocalX(mouseX) > getInnerWidth()){
                //y scroll
                dragging = true;
                draggingAxis = 0;
                /*
                screenToLocalY(mouseY) /getInnerHeight() percentage location of the mouse click
                 */
                double progress = getScrollHeight()*((double) screenToLocalY(mouseY) /getInnerHeight());
                setYOffset(getScrollAmount(progress,0,getScrollHeight()));
            }else if(screenToLocalY(mouseY)>getInnerHeight()){
                //x scroll
                dragging = true;
                draggingAxis = 1;
                double progress = getScrollWidth()*((double) screenToLocalX(mouseX) /getInnerWidth());

                setXOffset(getScrollAmount(progress,0,getScrollWidth()));
            }

        }
    }

    @Override
    public void onMouseReleased(double mouseX, double mouseY, int button) {
        if(button == InputConstants.MOUSE_BUTTON_LEFT) dragging = false;
    }

}