package net.lucent.easygui.testing.test_screens;

import net.lucent.easygui.elements.controls.buttons.ColorButton;
import net.lucent.easygui.elements.controls.inputs.TextBox;
import net.lucent.easygui.elements.other.Label;
import net.lucent.easygui.elements.other.View;
import net.lucent.easygui.screens.EasyGuiBaseScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class TestScreen extends EasyGuiBaseScreen {
    public TestScreen(Component title) {
        super(title);
        View view = new View(this.eventHolder,this,0,0);
        /*
        ColorButton button = new ColorButton(this.eventHolder,1920/4,1080/4,200,40);
        button.sticky = true;

        button.addChild(new Label.Builder()
                .eventHandler(eventHolder)
                .text( Component.literal("Some random text. cool right?"))
                .x(button.getWidth()/2)
                .y(button.getHeight()/2)
                .centered(true)
                .build());
        view.addChild(button);


         */

        view.addChild(new TextBox(
                eventHolder,
                1920/4,
                1080/4,
                200,20
        ));







        view.useMinecraftScale = false;

        views.add(view);
        view.customScale = 1;


    }
}
