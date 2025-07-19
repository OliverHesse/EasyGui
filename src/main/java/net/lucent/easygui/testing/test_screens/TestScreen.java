package net.lucent.easygui.testing.test_screens;

import net.lucent.easygui.EasyGui;
import net.lucent.easygui.elements.containers.panels.DraggablePanel;
import net.lucent.easygui.elements.containers.scroll_boxes.AbstractScrollBox;
import net.lucent.easygui.elements.controls.buttons.ColorButton;
import net.lucent.easygui.elements.containers.scroll_boxes.DynamicScrollBox;
import net.lucent.easygui.elements.controls.buttons.ToggleButton;
import net.lucent.easygui.elements.controls.inputs.ComboBox;
import net.lucent.easygui.elements.controls.inputs.MultiLineTextBox;
import net.lucent.easygui.elements.controls.inputs.TextBox;
import net.lucent.easygui.elements.inventory.DisplayEntity;
import net.lucent.easygui.elements.inventory.DisplayPlayer;
import net.lucent.easygui.elements.other.Label;
import net.lucent.easygui.elements.containers.View;
import net.lucent.easygui.screens.EasyGuiScreen;
import net.lucent.easygui.templating.EasyGuiBuilder;
import net.lucent.easygui.util.math.BoundChecker;
import net.lucent.easygui.util.math.Curves;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector4f;

import java.awt.*;

public class TestScreen extends EasyGuiScreen {
    public TestScreen(Component title) {
        super(title);
        View view = new View(this,0,0);
        view.setID("view");
        addView(view);
        view.setUseMinecraftScale(false);
        view.setCustomScale(2);
        //view.setCustomScale(1);
        //createToggleButton(view);
        //renderPlayer(view);
        //createDraggablePanel(view);
        createLinkedDraggablePanel(view);
        //createColorButton(view);
        //createFixedSizedScrollBox(view);
        //createDynamicScrollBox(view);
        //createMultiLineTextBox(view);
        //createTextBox(view);
        //createComboBox(view);
        //createViewManagerTest(view);
        //genFromFile();








    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);

    }

    public void renderPlayer(View view){
        LocalPlayer player = Minecraft.getInstance().player;
        DisplayPlayer dPlayer =  new DisplayPlayer(this,player,view.getScaledWidth()/2,view.getScaledHeight()/2,30);
        dPlayer.setRotation(0,0,0);
        view.addChild(
                dPlayer
        );
    }
    public void genFromFile(){

        EasyGuiBuilder builder = new EasyGuiBuilder(ResourceLocation.fromNamespaceAndPath(EasyGui.MOD_ID,"screen_templates/combo_box.json"));

        try{
            System.out.println("trying to build");
            builder.build(this);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void createViewManagerTest(View view){
        ColorButton button = new ColorButton(this,0,0,100,100){
            @Override
            public void onClick(double mouseX, double mouseY, int button, boolean clicked) {
                super.onClick(mouseX, mouseY, button, clicked);

                if(clicked) getScreen().setActiveView((View) getScreen().getElementByID("view2"));
            }
        };
        button.addChild(new Label.Builder()
                .screen(this)
                .text( Component.literal("swap to view 2"))
                .x(button.getWidth()/2)
                .y(button.getHeight()/2)
                .centered(true)
                .build());
        view.addChild(button);
        View view2 = new View(this,0,0);
        view2.setID("view2");
        ColorButton button2 = new ColorButton(this,20,20,100,100){
            @Override
            public void onClick(double mouseX, double mouseY, int button, boolean clicked) {
                super.onClick(mouseX, mouseY, button, clicked);
                if(clicked) getScreen().setActiveView((View) getScreen().getElementByID("view"));
            }
        };
        button2.addChild(new Label.Builder()
                .screen(this)
                .text( Component.literal("swap to view 1"))
                .x(button.getWidth()/2)
                .y(button.getHeight()/2)
                .centered(true)
                .build());
        view2.addChild(button2);
        addView(view2);
    }

    public void createComboBox(View view){
        ComboBox comboBox = new ComboBox(this,view.getScaledWidth()/2-40,view.getScaledHeight()/2-10,80,29, System.out::println);
        comboBox.addValue("Test 12323232323");
        comboBox.addValue("Test 232323232323");
        comboBox.addValue("Test 33232323232");
        comboBox.addValue("Test 43232323232");
        view.addChild(comboBox);
    }


    public void createToggleButton(View view){
        ToggleButton toggleButton = new ToggleButton(this,0,0);
        toggleButton.setCustomScale(4);
        toggleButton.setSticky(true);
        toggleButton.setX(view.getScaledWidth()/2 - 15*4);
        toggleButton.setY(view.getScaledHeight()/2 - 15*4);
        view.addChild(
                toggleButton
        );
    }
    public void createLinkedDraggablePanel(View view){
        DraggablePanel panel = new DraggablePanel(this,(view.getScaledWidth()/2)-100,(view.getScaledHeight()/2)-50,200,100);
        view.addChild(panel);
        DraggablePanel panel2 = new DraggablePanel(this,(view.getScaledWidth()/2)-100,(view.getScaledHeight()/2)-50,200,100){
            @Override
            public void renderSelf(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
                super.renderSelf(guiGraphics, mouseX, mouseY, partialTick);
                Curves.drawCurve(guiGraphics,screenToLocalPoint(panel.getGlobalPoint().x,panel.getGlobalPoint().y),screenToLocalPoint(this.getGlobalPoint().x,this.getGlobalPoint().y),500,false);
            }
        };
        panel2.setSticky(true);
        panel.setSticky(true);
        view.addChild(panel2);

    }
    public void createDraggablePanel(View view){

        //view.setCustomScale(2);
        DraggablePanel panel =  new DraggablePanel(this,(view.getScaledWidth()/2)-100,(view.getScaledHeight()/2)-50,200,100);
        //panel.setRotation(0,0,90);
        view.addChild(panel);
        panel.setCustomScale(2);
        panel.setCull(true);
        panel.setRotation(0,0,0);
        panel.addChild(new ColorButton(this,-20,-20,100,40));
        ColorButton button = (new ColorButton(this,-20,panel.getHeight()-20,100,40){
            @Override
            public void onClick(double mouseX, double mouseY, int button, boolean clicked) {
                super.onClick(mouseX, mouseY, button, clicked);
                if(clicked) System.out.println("was clicked");
            }
        });
        button.setRotation(0,0,90);
        panel.addChild(button);
        //panel.addChild(new Label.Builder().screen(this).x(-50).y(-50).text("test culling").build());
        panel.addChild(new Label.Builder().screen(this).x(panel.getWidth()/2).y(panel.height/2).text("test stuff").centered(true).build());
    }
    public void createColorButton(View view){
        ColorButton button = new ColorButton(this,view.getScaledWidth()/4,view.getScaledHeight()/4,200,40);


        button.addChild(new Label.Builder()
                .screen(this)
                .text( Component.literal("Some random text. cool right?"))
                .x(button.getWidth()/2)
                .y(button.getHeight()/2)
                .centered(true)
                .build());
        view.addChild(button);

    }
    public void createFixedSizedScrollBox(View view){
        view.setUseMinecraftScale(false);
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
        scrollBox.setCustomScale(1);
        scrollBox.setCull(false);
        ColorButton button = new ColorButton(this,0,0,100,40){
            @Override
            public void onClick(double mouseX, double mouseY, int button, boolean clicked) {
                super.onClick(mouseX, mouseY, button, clicked);
                System.out.println("reacted to event");
                System.out.println(clicked);
            }
        };

        button.setCull(false);
        button.addChild(new Label.Builder()
                .screen(this)
                .text( Component.literal("Some random text. cool right?"))
                .x(button.getWidth()/2)
                .y(button.getHeight()/2)
                .centered(true)
                .customScaling(1)
                .build());

        scrollBox.addChild(button);
        scrollBox.addChild(new Label.Builder().screen(this).text("some text2").textColor(-1).y(400).build());
        scrollBox.addChild(new Label.Builder()
                .screen(this)
                .text("some text3").textColor(-1)
                .y(190)
                .build());
        view.addChild(scrollBox);

    }
    public void createDynamicScrollBox(View view){
        DynamicScrollBox scrollBox = new DynamicScrollBox(this,
                view.getScaledWidth()/4,
                view.getScaledHeight()/4,
                200,200);
        scrollBox.addChild(new Label.Builder().screen(this).text("some textahyuhsguihaeruigharehuaierhioauraighri").textColor(-1).build());
        scrollBox.addChild(new Label.Builder().screen(this).text("some text2").textColor(-1).y(400).build());
        scrollBox.addChild(new Label.Builder()
                .screen(this)
                .text("some text3").textColor(-1)
                .y(190)
                .build());
        view.addChild(scrollBox);



    }
    public void createMultiLineTextBox(View view){
        view.addChild(new MultiLineTextBox(
                this,
                view.getScaledWidth()/2,
                view.getScaledHeight()/2,
                100,100
        ));
    }
    public void createTextBox(@NotNull View view){
        view.addChild(new TextBox(
                this,
                view.getScaledWidth()/4,
                view.getScaledHeight()/4,
                100
        ));

    }
}
