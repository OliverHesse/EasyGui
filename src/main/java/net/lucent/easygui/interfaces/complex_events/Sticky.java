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
     * @param oldWidth old width. includes gui scaling if root uses it
     * @param oldHeight old height. includes gui scaling if root uses it
     */
    void recalculatePos(int oldWidth, int oldHeight);
    @Override
    default void onGuiScaleChanged(double oldScale){
        //old height = current height. because only scale factor changed
        recalculatePos((int) (Minecraft.getInstance().getWindow().getWidth()/oldScale), (int) (Minecraft.getInstance().getWindow().getHeight()/oldScale));
    }

    @Override
    default void onResize(int oldWidth, int oldHeight){

        if(getRoot().usesMinecraftScaling()){
            double scale = Minecraft.getInstance().getWindow().getGuiScale();
            recalculatePos((int) (oldWidth/scale), (int) (oldHeight/scale));
        }
    };
}
