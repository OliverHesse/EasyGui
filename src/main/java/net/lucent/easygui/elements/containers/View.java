package net.lucent.easygui.elements.containers;

import com.google.gson.JsonObject;
import com.mojang.blaze3d.platform.Window;

import net.lucent.easygui.elements.BaseRenderable;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.lucent.easygui.interfaces.events.GuiScaleListener;
import net.lucent.easygui.interfaces.events.ScreenResizeListener;
import net.lucent.easygui.templating.IRenderableDeserializer;
import net.lucent.easygui.templating.deserializers.BaseDeserializer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;


public class View extends BaseRenderable implements ScreenResizeListener, GuiScaleListener {
    /**
     * scales items relative to original viewport size
     * so if it was originally 1920x1080 and now 960x540
     * then the scale factor is 1/2
     */
    public boolean useViewportSize = false;
    public boolean useMinecraftScale = false;


    public View() {
        super();
    }
    public View(IEasyGuiScreen screen){
        super(screen);
    }
    public View(IEasyGuiScreen screen, int x, int y) {
        this(screen, x, y, Minecraft.getInstance().getWindow().getWidth(), Minecraft.getInstance().getWindow().getHeight());
    }

    public View(IEasyGuiScreen screen, int x, int y, int width, int height) {
        super(screen);
        this.setX(x);
        this.setY(y);
        this.setWidth(width);;
        this.setHeight(height);
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
    public double getScale() {

        double finalScale = 1;
        if (useCustomScaling) {
            finalScale = finalScale* getCustomScale();
        }
        if (usesMinecraftScaling()) {

            finalScale = finalScale * Minecraft.getInstance().getWindow().getGuiScale();
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
        double scaleFactor = useCustomScaling ? getCustomScale() : 1;
        return (int) (getHeight()/scaleFactor);
    }

    @Override
    public int getScaledWidth() {
        double scaleFactor = useCustomScaling ? getCustomScale() : 1;
        return (int) (getWidth()/scaleFactor);
    }

    @Override
    public boolean isSticky() {
        return false;
    }

    /**
     * this could be breaking things..
     */
    @Override
    public void onResize(int oldWidth, int oldHeight,double oldScale) {
        recalculateDimensions();
    }

    // bugged when opening menu in fullscreen mode
    public void recalculateDimensions() {

        if (useMinecraftScale) {
            setWidth(Minecraft.getInstance().getWindow().getGuiScaledWidth());
            setHeight(Minecraft.getInstance().getWindow().getGuiScaledHeight());
        } else {
            setWidth(Minecraft.getInstance().getWindow().getWidth());
            setHeight(Minecraft.getInstance().getWindow().getHeight());
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

    public static class Deserializer extends BaseDeserializer {

        @Override
        public void parseWidth(String expr) {
            ((View) getRenderable()).recalculateDimensions();
        }

        @Override
        public void parseHeight(String expr) {
            ((View) getRenderable()).recalculateDimensions();
        }

        @Override
        public void buildRenderable(IEasyGuiScreen screen, IRenderableDeserializer parent, JsonObject obj) {
            super.buildRenderable(screen, parent, obj);
            ((View) getRenderable()).useMinecraftScale = getOrDefault(obj,"use_minecraft_scaling",false);
            //does not treat width and height the same as others

        }

        public Deserializer(Supplier<? extends View> supplier) {
            super(supplier);
        }

        @Override
        public @NotNull IRenderableDeserializer createInstance() {
            return new Deserializer((Supplier<? extends View>) supplier);
        }
    }
}
