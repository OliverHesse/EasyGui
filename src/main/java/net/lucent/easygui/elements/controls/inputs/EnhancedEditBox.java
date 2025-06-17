package net.lucent.easygui.elements.controls.inputs;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

import java.util.function.Consumer;

public class EnhancedEditBox extends EditBox {
    public EnhancedEditBox(Font font, int width, int height, Component message) {
        super(font, width, height, message);
    }



    public void enhancedOnClick(double mouseX, double mouseY, int button,int globalX,double totalScalingX){
        if(button != InputConstants.MOUSE_BUTTON_LEFT) return;

        int i = Mth.floor(mouseX) - globalX;
        int scaled = (int) (i/totalScalingX);
        if (isBordered()) {
            scaled -= 4;
        }


        String s = font.plainSubstrByWidth(getValue().substring(displayPos), this.getInnerWidth());
        this.moveCursorTo(this.font.plainSubstrByWidth(s, scaled).length() + this.displayPos, Screen.hasShiftDown());
    }
}
