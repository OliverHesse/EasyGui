package net.lucent.easygui.testing.test_screens;

import net.lucent.easygui.elements.controls.buttons.ColorButton;
import net.lucent.easygui.elements.other.Label;
import net.lucent.easygui.elements.other.View;
import net.lucent.easygui.screens.EasyGuiBaseScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class TestScreen extends EasyGuiBaseScreen {
    public TestScreen(Component title) {
        super(title);
        View view = new View(this.eventHolder,this,0,0,1920,1080);
        ColorButton button = new ColorButton(this.eventHolder,1920/2,1080/2,200,40);
        button.sticky = true;
        button.addChild(new Label(eventHolder, Minecraft.getInstance().font, Component.literal("Some random text. cool right?"),button.getWidth()/2,button.getHeight()/2,true));
        view.useMinecraftScale = false;
        view.addChild(button);
        views.add(view);
        button.customScale = 2;


    }
}
