package net.lucent.easygui.testing.test_screens;

import net.lucent.easygui.elements.controls.buttons.BaseButton;
import net.lucent.easygui.elements.other.View;
import net.lucent.easygui.screens.EasyGuiBaseScreen;
import net.minecraft.network.chat.Component;

public class TestScreen extends EasyGuiBaseScreen {
    public TestScreen(Component title) {
        super(title);
        View view = new View(this.eventHolder,this,0,0,1920,1080);
        BaseButton button = new BaseButton(this.eventHolder,1920/2,1080/2,200,40);
        view.useMinecraftScale = false;
        view.addChild(button);
        views.add(view);
        button.customScale = 2;


    }
}
