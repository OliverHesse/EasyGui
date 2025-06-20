package net.lucent.easygui.testing.test_screens;

import net.lucent.easygui.elements.controls.buttons.ColorButton;
import net.lucent.easygui.elements.controls.inputs.MultiLineTextBox;
import net.lucent.easygui.elements.other.Label;
import net.lucent.easygui.elements.other.View;
import net.lucent.easygui.screens.EasyGuiBaseScreen;
import net.minecraft.network.chat.Component;

public class TestScreen extends EasyGuiBaseScreen {
    public TestScreen(Component title) {
        super(title);
        View view = new View(this,0,0);
        addView(view);
        view.setCustomScale(1);
        view.setUseMinecraftScale(true);
        ColorButton button = new ColorButton(this,view.getScaledWidth()/2,view.getScaledHeight()/2,200,40);
        button.addChild(new Label.Builder()
                .screen(this)
                .text( Component.literal("Some random text. cool right?"))
                .x(button.getWidth()/2)
                .y(button.getHeight()/2)
                .centered(true)
                .build());
        view.addChild(button);


        view.addChild(new MultiLineTextBox(
                this,
                view.getScaledWidth()/4,
                view.getScaledHeight()/4,
                100,100
        ));

        /*
        view.addChild(new TextBox(
                this,
                view.getScaledWidth()/4,
                view.getScaledHeight()/4,
                100,1
        ));

         */











    }
}
