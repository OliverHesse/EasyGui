package net.lucent.easygui.elements.other;


import net.lucent.easygui.elements.BaseRenderable;
import net.lucent.easygui.holders.EasyGuiEventHolder;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.lucent.easygui.interfaces.events.Hoverable;
import net.minecraft.client.Minecraft;

public abstract class SquareRenderable extends BaseRenderable implements Hoverable {
    public SquareRenderable(IEasyGuiScreen easyGuiScreen) {
        super(easyGuiScreen);
    }


    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        /* used for debugging
        System.out.println("x: " + getGlobalScaledX() + " y: "+ getGlobalScaledY());
        System.out.println("mx: " + mouseX + " my: "+ mouseY);
        System.out.println("sw: "+ getScaledWidth() + " sh: "+getScaledHeight());
         */
        return mouseX > getGlobalScaledX() && mouseX < getGlobalScaledX() + getScaledWidth() &&
                mouseY > getGlobalScaledY() && mouseY < getGlobalScaledY()+ getScaledHeight();
    }


}
