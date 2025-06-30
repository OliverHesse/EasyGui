package net.lucent.easygui.elements.containers.panels;

import com.google.gson.JsonObject;
import net.lucent.easygui.elements.other.SquareRenderable;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.lucent.easygui.templating.IRenderableDeserializer;
import net.lucent.easygui.templating.deserializers.BaseDeserializer;
import net.minecraft.client.gui.GuiGraphics;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class Panel extends SquareRenderable {

    public int borderColor = -3750202;
    public int backgroundColor = -7631989;
    public int borderWidth = 2;

    public Panel(){

    }
    public Panel(IEasyGuiScreen easyGuiScreen,int x,int y,int width, int height) {
        super(easyGuiScreen);
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
    }



    @Override
    public void renderSelf(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        System.out.println("p1: "+getGlobalPoint().x + ", "+getGlobalPoint().y);
        System.out.println("width: "+getScaledWidth());
        System.out.println("height: "+getScaledHeight());
        if(borderWidth != 0){
            //draw borders properly
            guiGraphics.fill(-borderWidth,-borderWidth,0,getHeight()+borderWidth,borderColor);
            guiGraphics.fill(-borderWidth,-borderWidth,getWidth()+borderWidth,0,borderColor);
            guiGraphics.fill(getWidth(),-borderWidth,getWidth()+borderWidth,getHeight()+borderWidth,borderColor);
            guiGraphics.fill(-borderWidth,getHeight(),getWidth()+borderWidth,getHeight()+borderWidth,borderColor);
        }

        guiGraphics.fill(0,0,getWidth(),getHeight(),backgroundColor);
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
    }

    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
    }
    public static class Deserializer extends BaseDeserializer {


        @Override
        public void buildRenderable(IEasyGuiScreen screen, IRenderableDeserializer parent, JsonObject obj) {
            super.buildRenderable(screen, parent, obj);

            Integer backgroundColor = getOrDefault(obj,"background_color",-7631989);
            Integer borderColor = getOrDefault(obj,"border_color", -3750202);
            Integer borderWidth = getOrDefault(obj, "border_width",2);

            ((Panel)getRenderable()).setBackgroundColor(backgroundColor);
            ((Panel)getRenderable()).setBorderColor(borderColor);
            ((Panel)getRenderable()).setBorderWidth(borderWidth);

        }

        public Deserializer(Supplier<? extends Panel> supplier) {
            super(supplier);
        }

        @Override
        public @NotNull IRenderableDeserializer createInstance() {
            return new Deserializer((Supplier<? extends Panel>) supplier);
        }
    }
}
