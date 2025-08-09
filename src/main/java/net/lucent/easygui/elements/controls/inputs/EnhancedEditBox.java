package net.lucent.easygui.elements.controls.inputs;

import com.mojang.blaze3d.platform.InputConstants;
import net.lucent.easygui.interfaces.ContainerRenderable;
import net.lucent.easygui.templating.actions.Action;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * will be used to override rendering logic
 */@OnlyIn(Dist.CLIENT)
public class EnhancedEditBox extends EditBox {
    public Action valueChangedAction;
    public ContainerRenderable parent;
    public EnhancedEditBox(Font font, int width, int height, Component message) {
        super(font, width, height, message);
    }

    @Override
    public void onValueChange(String newText) {
        super.onValueChange(newText);
        if(valueChangedAction != null) valueChangedAction.accept(parent);
    }

    public void enhancedOnClick(double mouseX, double mouseY, int button, int globalX, double totalScalingX){
        if(button != InputConstants.MOUSE_BUTTON_LEFT) return;

        int i = Mth.floor(mouseX) - globalX;
        int scaled = (int) (i/totalScalingX);
        if (isBordered()) {
            scaled -= 4;
        }


        String s = font.plainSubstrByWidth(getValue().substring(displayPos), this.getInnerWidth());
        this.moveCursorTo(this.font.plainSubstrByWidth(s, scaled).length() + this.displayPos, Screen.hasShiftDown());
    }
    public void highlightSubstring(int distance){

        String s = font.plainSubstrByWidth(getValue().substring(displayPos), this.getInnerWidth());

        this.moveCursorTo(this.font.plainSubstrByWidth(s, distance).length()+this.displayPos, true);
    }
}
