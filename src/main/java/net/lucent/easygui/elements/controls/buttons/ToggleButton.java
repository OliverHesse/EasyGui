package net.lucent.easygui.elements.controls.buttons;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.lucent.easygui.EasyGui;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.lucent.easygui.interfaces.ITextureData;
import net.lucent.easygui.util.TextureData;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class ToggleButton extends AbstractButton{
    public ITextureData backgroundTexture = new TextureData(ResourceLocation.fromNamespaceAndPath(EasyGui.MOD_ID,"built_in_textures/check_box_background.png"),32,32);

    public ITextureData onStateTexture = new TextureData(ResourceLocation.fromNamespaceAndPath(EasyGui.MOD_ID,"built_in_textures/check_box_tick.png"),32,32);

    public ITextureData offStateTexture = new TextureData(ResourceLocation.fromNamespaceAndPath(EasyGui.MOD_ID,"built_in_textures/check_box_x.png"),32,32);


    public ToggleButton(IEasyGuiScreen easyGuiScreen, int x, int y) {
        super(easyGuiScreen, x, y, 0, 0);
        unPressNaturally = false;
    }

    public void setBackgroundTexture(ITextureData backgroundTexture){
        this.backgroundTexture = backgroundTexture;
    }
    public void setOnStateTexture(ITextureData on){
        this.onStateTexture = on;
    }
    public void setOffStateTexture(ITextureData off){
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
        backgroundTexture.renderTexture(guiGraphics);
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


}
