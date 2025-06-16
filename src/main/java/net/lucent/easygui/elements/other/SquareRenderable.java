package net.lucent.easygui.elements.other;


import net.lucent.easygui.elements.BaseRenderable;
import net.lucent.easygui.holders.EasyGuiEventHolder;
import net.lucent.easygui.interfaces.events.Hoverable;

public abstract class SquareRenderable extends BaseRenderable implements Hoverable {
    public SquareRenderable(EasyGuiEventHolder eventHandler) {
        super(eventHandler);
    }


    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {

        return mouseX > getGlobalScaledX() && mouseX < getGlobalScaledX() + getScaledWidth() &&
                mouseY > getGlobalScaledY() && mouseY < getGlobalScaledY() + getScaledHeight();
    }


}
