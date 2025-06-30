package net.lucent.easygui.elements.controls.buttons;

import com.google.gson.JsonObject;
import com.mojang.blaze3d.platform.InputConstants;
import net.lucent.easygui.EasyGui;
import net.lucent.easygui.elements.BaseRenderable;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.lucent.easygui.interfaces.ITextureData;
import net.lucent.easygui.templating.IRenderableDeserializer;
import net.lucent.easygui.templating.deserializers.BaseDeserializer;
import net.lucent.easygui.templating.deserializers.SquareRenderableDeserializer;
import net.lucent.easygui.util.textures.TextureData;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ToggleButton extends AbstractButton{



    private ITextureData backgroundTexture = new TextureData(ResourceLocation.fromNamespaceAndPath(EasyGui.MOD_ID,"built_in_textures/check_box_background.png"),32,32);

    private ITextureData onStateTexture = new TextureData(ResourceLocation.fromNamespaceAndPath(EasyGui.MOD_ID,"built_in_textures/check_box_tick.png"),32,32);

    private ITextureData offStateTexture = new TextureData(ResourceLocation.fromNamespaceAndPath(EasyGui.MOD_ID,"built_in_textures/check_box_x.png"),32,32);

    public ToggleButton(){}
    public ToggleButton(IEasyGuiScreen easyGuiScreen, int x, int y) {
        super(easyGuiScreen, x, y, 0, 0);
        unPressNaturally = false;
    }

    public void setBackgroundTexture(ITextureData backgroundTexture){
        this.backgroundTexture = backgroundTexture;
    }
    public void setOnStateTexture(ITextureData on){
        if(on == null){
            this.onStateTexture = new TextureData(ResourceLocation.fromNamespaceAndPath(EasyGui.MOD_ID,"built_in_textures/check_box_tick.png"),32,32);

            return;
        }
        this.onStateTexture = on;
    }
    public void setOffStateTexture(ITextureData off){
        if (off == null) {
            this.offStateTexture = new TextureData(ResourceLocation.fromNamespaceAndPath(EasyGui.MOD_ID,"built_in_textures/check_box_x.png"),32,32);

            return;
        }
        this.offStateTexture = off;
    }

    @Override
    public int getWidth() {
        return backgroundTexture.getTextureWidth();
    }

    @Override
    public int getHeight() {
        return backgroundTexture.getTextureHeight();
    }

    @Override
    public void renderSelf(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if(backgroundTexture != null) backgroundTexture.renderTexture(guiGraphics);
        if(isPressed()) onStateTexture.renderTexture(guiGraphics);
        else offStateTexture.renderTexture(guiGraphics);
    }

    @Override
    public void onClick(double mouseX, double mouseY, int button, boolean clicked) {
        if(clicked && button == InputConstants.MOUSE_BUTTON_LEFT){
            setPressed(!isPressed());
        }

    }


    public boolean getState(){
        return isPressed();
    }



    public static class Deserializer extends SquareRenderableDeserializer {

        public Deserializer(Supplier<? extends ToggleButton> supplier) {
            super(supplier);
        }

        @Override
        public void buildRenderable(IEasyGuiScreen screen, IRenderableDeserializer parent, JsonObject obj) {
            super.buildRenderable(screen, parent, obj);
            if(obj.getAsJsonObject("on_click") != null){
                ((ToggleButton) getRenderable()).clickAction =  parseAction("on_click",obj);
            }
            ((ToggleButton)getRenderable()).setBackgroundTexture(parseTexture("background_texture",obj));
            ((ToggleButton)getRenderable()).setOnStateTexture(parseTexture("on_texture",obj));
            ((ToggleButton)getRenderable()).setOffStateTexture(parseTexture("off_texture",obj));
        }

        @Override
        public @NotNull IRenderableDeserializer createInstance() {
            return new Deserializer((Supplier<? extends ToggleButton>) supplier);
        }
    }
}
