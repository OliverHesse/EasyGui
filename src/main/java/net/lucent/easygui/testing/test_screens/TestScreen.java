package net.lucent.easygui.testing.test_screens;

import net.lucent.easygui.elements.containers.panels.DraggablePanel;
import net.lucent.easygui.elements.controls.buttons.ColorButton;
import net.lucent.easygui.elements.containers.scroll_boxes.DynamicScrollBox;
import net.lucent.easygui.elements.controls.buttons.ToggleButton;
import net.lucent.easygui.elements.other.Label;
import net.lucent.easygui.elements.containers.View;
import net.lucent.easygui.screens.EasyGuiBaseScreen;
import net.minecraft.network.chat.Component;

public class TestScreen extends EasyGuiBaseScreen {
    public TestScreen(Component title) {
        super(title);
        View view = new View(this,0,0);
        addView(view);
        view.setCustomScale(1);
        view.setUseMinecraftScale(true);

        ToggleButton toggleButton = new ToggleButton(this,0,0);
        toggleButton.setCustomScale(4);
        toggleButton.setSticky(true);
        toggleButton.setX(view.getScaledWidth()/2 - 15*4);
        toggleButton.setY(view.getScaledHeight()/2 - 15*4);
        view.addChild(
            toggleButton
        );
        /*
        view.addChild(
                new DraggablePanel(this,(view.getScaledWidth()/2)-100,(view.getScaledHeight()/2)-50,200,100)
        );


         */
        //ColorButton button = new ColorButton(this,view.getScaledWidth()/2,view.getScaledHeight()/2,200,40);

       /*
        button.addChild(new Label.Builder()
                .screen(this)
                .text( Component.literal("Some random text. cool right?"))
                .x(button.getWidth()/2)
                .y(button.getHeight()/2)
                .centered(true)
                .build());
        view.addChild(button);

        */
        /*
        AbstractScrollBox scrollBox = new AbstractScrollBox(this,
                view.getScaledWidth()/4,
                view.getScaledHeight()/4,
                200,200) {
            @Override
            public double getScrollHeight() {
                return 300;
            }

            @Override
            public double getScrollWidth() {
                return 0;
            }

            @Override
            public double getScrollRate() {
                return 9;
            }

        };

         */
        /*
        DynamicScrollBox scrollBox = new DynamicScrollBox(this,
                view.getScaledWidth()/4,
                view.getScaledHeight()/4,
                200,200);
        scrollBox.addChild(new Label.Builder().screen(this).text("some text").textColor(-1).build());
        scrollBox.addChild(new Label.Builder().screen(this).text("some text2").textColor(-1).y(400).build());
         scrollBox.addChild(new Label.Builder()
                .screen(this)
                .text("some text3").textColor(-1)
                .y(190)
                .build());
        view.addChild(scrollBox);

         */

        /*
        view.addChild(new MultiLineTextBox(
                this,
                view.getScaledWidth()/2,
                view.getScaledHeight()/2,
                100,100
        ));


         */
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
