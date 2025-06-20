package net.lucent.easygui.elements.controls.buttons;

import net.lucent.easygui.holders.EasyGuiEventHolder;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.minecraft.client.gui.GuiGraphics;

import java.awt.*;

public class ColorButton extends AbstractButton{

    private final Color defaultColor = new Color(162,162,162,255);
    private final Color hoverColor = new Color(194,194,194,255);
    private final Color pressColor = new Color(136,136,136,255);
    private final Color focusColor = new Color(162,162,162,255);


    public ColorButton(IEasyGuiScreen easyGuiScreen, int x, int y, int width, int height) {
        super(easyGuiScreen, x, y, width, height);
        System.out.println("=====");
        System.out.println("x: "+x +" y: "+y);
    }

    @Override
    public void renderSelf(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {

        Color finalColor = defaultColor;
        if(focused){
            finalColor = focusColor;
            guiGraphics.fill(-1,-1,getWidth()+2,getHeight()+2,(new Color(255,255,255,255)).getRGB());
        }
        if(hovered){
            finalColor = hoverColor;
        }
        if(pressed){
            finalColor = pressColor;
            time_pressed += partialTick;
            if (time_pressed >= PRESSED_TIME) {
                pressed = false;
                time_pressed = 0;
            }
        }

        guiGraphics.fill(0,0,getWidth(),getHeight(),finalColor.getRGB());

    }


}
