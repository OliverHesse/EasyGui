package net.Lucent.EasyGui.elements.other;

import com.mojang.blaze3d.platform.Window;

import net.Lucent.EasyGui.holders.EasyGuiEventHolder;
import net.Lucent.EasyGui.interfaces.EasyGuiScreen;
import net.Lucent.EasyGui.interfaces.events.ScreenResizeListener;
import net.Lucent.EasyGui.util.PositionedTextureData;
import net.Lucent.EasyGui.util.TextureSubSection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;


public class View extends BaseRenderable implements ScreenResizeListener {
    /**
     * scales items relative to original viewport size
     * so if it was originally 1920x1080 and now 960x540
     * then the scale factor is 1/2
     */
    public boolean useViewportSize = false;
    public boolean useMinecraftScale = false;
    public boolean adjustPositionOnResize = true;

    public PositionedTextureData bgTexture;

    public int width;
    public int height;

    public EasyGuiScreen screen;

    public View(EasyGuiEventHolder eventHandler, EasyGuiScreen screen, int x, int y, int width, int height) {
        super(eventHandler);
        this.setX(x);
        this.setY(y);
        this.width = width;
        this.height = height;
        this.screen = screen;
    }

    /**
     * use when texture matches dimensions
     * @param texture texture for background
     */
    public void setBackgroundTexture(ResourceLocation texture){
        bgTexture = new PositionedTextureData(texture,0,0,getWidth(),getHeight(),new TextureSubSection(0,0,getWidth(),getHeight()));
    }

    /**
     * use when overall texture matches dimensions, but you have a subSection you would rather use
     */
    public void setBackgroundTexture(ResourceLocation texture,TextureSubSection subSection){
        bgTexture = new PositionedTextureData(texture,0,0,getWidth(),getHeight(), subSection);
    }

    public void setBackgroundTexture(PositionedTextureData data){
        bgTexture = data;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public double getScale(){

        double finalScale = 1;
        if(useCustomScaling){
            finalScale *= customScale;
        }
        if(useMinecraftScale){
            finalScale *= Minecraft.getInstance().getWindow().getGuiScale();
        }

        return finalScale/Minecraft.getInstance().getWindow().getGuiScale();
    }

    @Override
    public void renderSelf(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {

        if(bgTexture == null) return;
        TextureSubSection section = bgTexture.subSection();
        guiGraphics.blit(bgTexture.texture(),bgTexture.x(),bgTexture.y(),
                section.u(),section.v(),section.offsetU(),section.offsetV(),
                bgTexture.textureWidth(),bgTexture.textureHeight());
    }

    @Override
    public double getTotalScaleFactorX() {
        //magic shit dont worry about it
        /*
        if you are worrying then this is because this is because of how minecraft's scaling works.
        when it scales something "up" it is actually just reducing the available pixels by that amount
        so a 2x scales is each pixel is worth 2. but the result is stuff like screen width is actually /2....
        so in the new screen size the width of your texture has technically not changed at all... i know really fucky wucky
        i know fucky wucky shit
         */
        if(useMinecraftScale) return getScaleX()/Minecraft.getInstance().getWindow().getGuiScale();
        //ok i dont know anymore man
        return getScaleX()*Minecraft.getInstance().getWindow().getGuiScale();
    }

    @Override
    public double getTotalScaleFactorY() {
        //magic shit dont worry about it
        if(useMinecraftScale) return getScaleY()/Minecraft.getInstance().getWindow().getGuiScale();
        return getScaleY()*Minecraft.getInstance().getWindow().getGuiScale();
    }

    @Override
    public double getScaleX() {

        double finalScale = getScale();
        if(useViewportSize) {

            Window win = Minecraft.getInstance().getWindow();

            finalScale *=(double) win.getWidth() / win.getScreenWidth();
        }
        return finalScale;
    }

    @Override
    public double getScaleY() {
        Window win = Minecraft.getInstance().getWindow();
        double finalScale = getScale();
        if(useViewportSize) {
            finalScale *=(double) win.getHeight() / win.getScreenHeight();
        }
        return finalScale;
    }

    @Override
    public void remove() {
        eventHandler.unregister(this);
        screen.removeView(this);
    }

    @Override
    public void onResize(int oldWidth, int oldHeight) {
        width = Minecraft.getInstance().getWindow().getWidth();
        height = Minecraft.getInstance().getWindow().getHeight();
    }
}
