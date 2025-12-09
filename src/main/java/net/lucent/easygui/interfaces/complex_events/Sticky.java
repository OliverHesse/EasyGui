package net.lucent.easygui.interfaces.complex_events;

import net.lucent.easygui.interfaces.events.GuiScaleListener;
import net.lucent.easygui.interfaces.events.ScreenResizeListener;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@Deprecated
@OnlyIn(Dist.CLIENT)
public interface Sticky extends ScreenResizeListener, GuiScaleListener {

    void setSticky(boolean sticky);
    boolean isSticky();

    //TODO still bugged. because im doing multipliers everything about the position is bugged.
    //  because im placing elements using viewdimension/2. im placing stuff relative to the center not 0,0
    //  the displacement from the center should be the same
    //  so if im -20,-20 from center even after enlarging it should stay as such
    //  so i should calc that. then apply it to the center of the new ui.
    /**
     * you will need to implement this yourself. i know... annoying... but it is what it is
     * mainly because the formula for gui scale change is not the same as the formula for window size change
     * I will work to see if i can implement a general solution but my head hurts
     * @param oldWidth old width. includes minecraft gui scaling if root uses it
     * @param oldHeight old height. includes minecraft gui scaling if root uses it
     */
    default void recalculatePos(int oldWidth, int oldHeight){
        if(isSticky()) {
            oldWidth = (int) (oldWidth/getRoot().getTotalScaleFactorX());
            oldHeight = (int) (oldHeight/getRoot().getTotalScaleFactorY());
            int centerX = oldWidth/2;
            int centerY = oldHeight/2;
            /*
            int xOffset = centerX-getX();
            int yOffset = centerY-getY();

            int newX = getRoot().getScaledWidth()+xOffset;
            int newY = getRoot().getScaledHeight()+yOffset;
            setX(newX);
            setY(newY);
            */
            int x = (int) (getX()+getWidth()*getScaleX()/2);
            int y = (int) (getY()+getHeight()*getScaleY()/2);
            double mX = (double) x /oldWidth;
            double mY = (double) y /oldHeight;

            int newX = (int) (getRoot().getScaledWidth()*mX - getWidth()*getScaleX()/2);
            int newY = (int) (getRoot().getScaledHeight()*mY - getHeight()*getScaleY()/2);
            setX(newX);
            setY(newY);


        }
    };
    //TODO is it this?
    @Override
    default void onGuiScaleChanged(double oldScale){
        //old height = current height. because only scale factor changed
        recalculatePos((int) (Minecraft.getInstance().getWindow().getWidth()/oldScale), (int) (Minecraft.getInstance().getWindow().getHeight()/oldScale));
    }


    //TODO bruhhh
    @Override
    default void onResize(int oldWidth, int oldHeight,double oldScale){
        if(getRoot().usesMinecraftScaling()){
            recalculatePos((int) (oldWidth/oldScale), (int) (oldHeight/oldScale));
        }else{
            recalculatePos((int) (oldWidth/oldScale), (int) (oldHeight/oldScale));
        }
    };
}
