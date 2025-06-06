package net.Lucent.EasyGui.testing.testOverlay;

import net.Lucent.EasyGui.elements.controls.buttons.BaseButton;
import net.Lucent.EasyGui.elements.other.View;
import net.Lucent.EasyGui.holders.EasyGuiEventHolder;
import net.Lucent.EasyGui.interfaces.EasyGuiScreen;
import net.Lucent.EasyGui.overlays.EasyGuiOverlay;

public class RandomOverlay extends EasyGuiOverlay implements EasyGuiScreen {
    public RandomOverlay() {
        View view = new View(this.eventHolder,this,0,0,1920,1080);
        BaseButton button = new BaseButton(this.eventHolder,1920/2,1080/2,200,40);
        view.addChild(button);
        this.view = view;
        button.customScale = 2;
    }

    @Override
    public void removeView(View view) {
        this.view = null;
    }
}
