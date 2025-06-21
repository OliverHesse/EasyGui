package net.lucent.easygui.elements.controls.buttons;

import net.lucent.easygui.holders.EasyGuiEventHolder;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.lucent.easygui.interfaces.ITextureData;
import net.minecraft.client.gui.GuiGraphics;

public class TextureButton extends AbstractButton{

    public ITextureData defaultTexture;
    public ITextureData pressedTexture;
    public ITextureData hoveredTexture;
    public ITextureData focusedTexture;


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
}
