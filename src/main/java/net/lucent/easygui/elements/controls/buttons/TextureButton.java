package net.lucent.easygui.elements.controls.buttons;

import com.google.gson.JsonObject;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.lucent.easygui.interfaces.ITextureData;
import net.lucent.easygui.templating.IRenderableDeserializer;
import net.lucent.easygui.templating.deserializers.SquareRenderableDeserializer;
import net.minecraft.client.gui.GuiGraphics;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class TextureButton extends AbstractButton{

    public ITextureData defaultTexture;
    public ITextureData pressedTexture;
    public ITextureData hoveredTexture;
    public ITextureData focusedTexture;

    public TextureButton(){}

    public TextureButton(
            IEasyGuiScreen easyGuiScreen,
            int x, int y,
            ITextureData defaultTexture,
            ITextureData pressedTexture,
            ITextureData hoveredTexture,
            ITextureData focusedTexture

    ){
        super(easyGuiScreen,x,y,defaultTexture.getWidth(), defaultTexture.getHeight());
        this.defaultTexture =defaultTexture;
        this.pressedTexture = pressedTexture;
        this.hoveredTexture = hoveredTexture;
        this.focusedTexture = focusedTexture;
    }


    public TextureButton(
            IEasyGuiScreen easyGuiScreen,
            int x, int y,
            ITextureData defaultTexture,
            ITextureData pressedTexture,
            ITextureData hoveredTexture

    ){
        this(easyGuiScreen,x,y,defaultTexture,pressedTexture,hoveredTexture,defaultTexture);
    }


    @Override
    public void renderSelf(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        ITextureData finalTexture = defaultTexture;
        if(isFocused()) finalTexture = focusedTexture;
        if(isHovered()) finalTexture = hoveredTexture;
        if(isPressed()) finalTexture = pressedTexture;

        finalTexture.renderTexture(guiGraphics,getX(),getY());
    }

    public void setDefaultTexture(ITextureData defaultTexture) {
        this.defaultTexture = defaultTexture;
    }

    public void setHoveredTexture(ITextureData hoveredTexture) {
        this.hoveredTexture = hoveredTexture;
    }

    public void setPressedTexture(ITextureData pressedTexture) {
        this.pressedTexture = pressedTexture;
    }

    public void setFocusedTexture(ITextureData focusedTexture) {
        this.focusedTexture = focusedTexture;
    }
    public static class Deserializer extends AbstractButton.Deserializer{
        public Deserializer(Supplier<? extends TextureButton> supplier) {
            super(supplier);
        }

        @Override
        public void buildRenderable(IEasyGuiScreen screen, IRenderableDeserializer parent, JsonObject obj) {
            super.buildRenderable(screen, parent, obj);

            ((TextureButton)getRenderable()).setDefaultTexture(parseTexture("default_texture",obj));
            ((TextureButton)getRenderable()).setHoveredTexture(parseTexture("hovered_texture",obj));
            ((TextureButton)getRenderable()).setPressedTexture(parseTexture("pressed_texture",obj));
            ITextureData data = parseTexture("focused_texture",obj);
            if(data == null) data = parseTexture("default_texture",obj);
            ((TextureButton)getRenderable()).setFocusedTexture(data);
        }

        @Override
        public @NotNull IRenderableDeserializer createInstance() {
            return new Deserializer((Supplier<? extends TextureButton>) supplier);
        }
    }
}
