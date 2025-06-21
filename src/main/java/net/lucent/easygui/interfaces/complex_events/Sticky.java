package net.lucent.easygui.interfaces.complex_events;

import net.lucent.easygui.interfaces.events.GuiScaleListener;
import net.lucent.easygui.interfaces.events.ScreenResizeListener;
import net.minecraft.client.Minecraft;

public interface Sticky extends ScreenResizeListener, GuiScaleListener {

    void setSticky(boolean sticky);
    boolean isSticky();

    /**
     * you will need to implement this yourself. i know... annoying... but it is what it is
     * mainly because the formula for gui scale change is not the same as the formula for window size change
     * I will work to see if i can implement a general solution but my head hurts
     * @param oldWidth old width. includes minecraft gui scaling if root uses it
     * @param oldHeight old height. includes minecraft gui scaling if root uses it
     */
    default void recalculatePos(int oldWidth, int oldHeight){
        System.out.println("old Width: "+ oldWidth);
        System.out.println("old Height" + oldHeight);
        System.out.println(getScaleX());
        oldWidth = (int) (oldWidth/getRoot().getTotalScaleFactorX());
        oldHeight = (int) (oldHeight/getRoot().getTotalScaleFactorY());
        int x = (int) (getX()+getWidth()*getScaleX()/2);
        int y = (int) (getY()+getHeight()*getScaleY()/2);
        double mX = (double) x /oldWidth;
        double mY = (double) y /oldHeight;

        int newX = (int) (getRoot().getScaledWidth()*mX - getWidth()*getScaleX()/2);
        int newY = (int) (getRoot().getScaledHeight()*mY - getHeight()*getScaleY()/2);
        setX(newX);
        setY(newY);
    };
    @Override
    default void onGuiScaleChanged(double oldScale){
        System.out.println("scale changed");
        //old height = current height. because only scale factor changed
        recalculatePos((int) (Minecraft.getInstance().getWindow().getWidth()/oldScale), (int) (Minecraft.getInstance().getWindow().getHeight()/oldScale));
    }

    @Override
    default void onResize(int oldWidth, int oldHeight,double oldScale){
        System.out.println("screen resized");
        if(getRoot().usesMinecraftScaling()){
            recalculatePos((int) (oldWidth/oldScale), (int) (oldHeight/oldScale));
        }
    };
}
