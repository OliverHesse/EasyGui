package net.lucent.easygui.elements.controls.buttons;

import com.google.gson.JsonObject;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.lucent.easygui.templating.IRenderableDeserializer;
import net.lucent.easygui.templating.deserializers.SquareRenderableDeserializer;
import net.minecraft.client.gui.GuiGraphics;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ColorButton extends AbstractButton{

    private  int defaultColor = -6118750;
    private  int hoverColor = -4013374;
    private  int pressColor = -7829368;
    private  int focusColor =-6118750;

    public ColorButton(){}
    public ColorButton(IEasyGuiScreen easyGuiScreen, int x, int y, int width, int height) {
        super(easyGuiScreen, x, y, width, height);

    }

    @Override
    public void renderSelf(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {

        int finalColor = defaultColor;
        if(focused){
            finalColor = focusColor;
            guiGraphics.fill(-1,-1,getWidth()+2,getHeight()+2,-1);
        }
        if(hovered)finalColor = hoverColor;
        if(pressed)finalColor = pressColor;


        guiGraphics.fill(0,0,getWidth(),getHeight(),finalColor);

    }

    public void setDefaultColor(int color){
        this.defaultColor = color;
    }

    public void setHoverColor(int hoverColor) {
        this.hoverColor = hoverColor;
    }

    public void setFocusColor(int focusColor) {
        this.focusColor = focusColor;
    }

    public void setPressColor(int pressColor) {
        this.pressColor = pressColor;
    }
    public static class Deserializer extends AbstractButton.Deserializer {
        public Deserializer(Supplier<? extends ColorButton> supplier) {
            super(supplier);
        }

        @Override
        public void buildRenderable(IEasyGuiScreen screen, IRenderableDeserializer parent, JsonObject obj) {
            super.buildRenderable(screen, parent, obj);

            ((ColorButton) getRenderable()).setDefaultColor(getOrDefault(obj,"default_color",-6118750));
            ((ColorButton) getRenderable()).setHoverColor(getOrDefault(obj,"hovered_color",-4013374));
            ((ColorButton) getRenderable()).setPressColor(getOrDefault(obj,"pressed_color",-7829368));
            ((ColorButton) getRenderable()).setFocusColor(getOrDefault(obj,"focused_color",-6118750));

        }

        @Override
        public @NotNull IRenderableDeserializer createInstance() {
            return new Deserializer((Supplier<? extends ColorButton>) supplier);
        }
    }

}
