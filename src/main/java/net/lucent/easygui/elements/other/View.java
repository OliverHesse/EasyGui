package net.lucent.easygui.elements.other;

import com.google.gson.JsonObject;
import com.mojang.blaze3d.platform.Window;

import net.lucent.easygui.elements.BaseRenderable;
import net.lucent.easygui.holders.EasyGuiEventHolder;
import net.lucent.easygui.interfaces.ContainerRenderable;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.lucent.easygui.interfaces.events.GuiScaleListener;
import net.lucent.easygui.interfaces.events.ScreenResizeListener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import org.joml.Quaternionf;


public class View extends BaseRenderable implements ScreenResizeListener, GuiScaleListener {
    /**
     * scales items relative to original viewport size
     * so if it was originally 1920x1080 and now 960x540
     * then the scale factor is 1/2
     */
    public boolean useViewportSize = false;
    public boolean useMinecraftScale = false;

    public int width;
    public int height;


    public View(IEasyGuiScreen screen, int x, int y) {
        this(screen, x, y, Minecraft.getInstance().getWindow().getWidth(), Minecraft.getInstance().getWindow().getHeight());
    }

    public View(IEasyGuiScreen screen, int x, int y, int width, int height) {
        super(screen);
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


    /**
     * use when overall texture matches dimensions, but you have a subSection you would rather use
     */


    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public double getScale() {

        double finalScale = 1;
        if (useCustomScaling) {
            finalScale *= getCustomScale();
        }
        if (useMinecraftScale) {
            finalScale *= Minecraft.getInstance().getWindow().getGuiScale();
        }

        return finalScale / Minecraft.getInstance().getWindow().getGuiScale();
    }


    @Override
    public void renderSelf(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {

    }

    /**
     * this is only for the view object
     * since it is responsible for most of the fucky wucky scaling shit
     */
    @Override
    public double getTotalScaleFactorX() {
        //magic shit dont worry about it
        return getScaleX();
        //if(useMinecraftScale) return getScaleX()*Minecraft.getInstance().getWindow().getGuiScale();
        //return getScaleX()*Minecraft.getInstance().getWindow().getGuiScale();
    }

    @Override
    public double getTotalScaleFactorY() {
        return getScaleY();
        //magic shit dont worry about it
        //if(useMinecraftScale) return getScaleY();
        //return getScaleY()*Minecraft.getInstance().getWindow().getGuiScale();
    }

    @Override
    public double getScaleX() {

        double finalScale = getScale();
        if (useViewportSize) {

            Window win = Minecraft.getInstance().getWindow();

            finalScale *= (double) win.getWidth() / win.getScreenWidth();
        }
        return finalScale;
    }

    @Override
    public double getScaleY() {
        Window win = Minecraft.getInstance().getWindow();
        double finalScale = getScale();
        if (useViewportSize) {
            finalScale *= (double) win.getHeight() / win.getScreenHeight();
        }
        return finalScale;
    }

    @Override
    public void remove() {
        screen.unregister(this);
        screen.removeView(this);
    }

    @Override
    public int getScaledHeight() {
        return (int) (getHeight()/getTotalScaleFactorY());
    }

    @Override
    public int getScaledWidth() {
        return (int) (getWidth()/getTotalScaleFactorX());
    }

    /**
     * this could be breaking things..
     */
    @Override
    public void onResize(int oldWidth, int oldHeight) {
        recalculateDimensions();
    }

    // bugged when opening menu in fullscreen mode
    public void recalculateDimensions() {

        if (useMinecraftScale) {
            width = Minecraft.getInstance().getWindow().getGuiScaledWidth() ;
            height = Minecraft.getInstance().getWindow().getGuiScaledHeight();
        } else {
            width = Minecraft.getInstance().getWindow().getWidth();
            height = Minecraft.getInstance().getWindow().getHeight();
        }
    }

    @Override
    public void setCustomScale(double scale){
        super.setCustomScale(scale);
        recalculateDimensions();
    }
    public void setUseMinecraftScale(boolean useMinecraftScale) {
        this.useMinecraftScale = useMinecraftScale;
        recalculateDimensions();
    }

    @Override
    public boolean usesMinecraftScaling() {
        return useMinecraftScale;
    }


    @Override
    public void onGuiScaleChanged(double oldScale) {
        recalculateDimensions();
    }

}
