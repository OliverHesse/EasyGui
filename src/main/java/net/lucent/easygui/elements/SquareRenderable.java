package net.lucent.easygui.elements;


import net.lucent.easygui.holders.EasyGuiEventHolder;
import net.lucent.easygui.interfaces.events.Hoverable;

public abstract class SquareRenderable extends BaseRenderable implements Hoverable {
    public SquareRenderable(EasyGuiEventHolder eventHandler) {
        super(eventHandler);
    }


    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        System.out.println("global X: "+ getGlobalScaledX() + " global Y: " + getGlobalScaledY());
        System.out.println("scaled width: " + getScaledWidth() + " scaled height: "+ getScaledHeight());
        System.out.println("x: :" +  getX() + "y: "+getY());
        System.out.println("mouse X: "+ mouseX + " mouse Y:" + mouseY);
        return mouseX > getGlobalScaledX() && mouseX < getGlobalScaledX() + getScaledWidth() &&
                mouseY > getGlobalScaledY() && mouseY < getGlobalScaledY() + getScaledHeight();
    }


}
