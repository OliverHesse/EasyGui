package net.lucent.easygui.elements.other;

import com.google.gson.JsonObject;
import net.lucent.easygui.elements.BaseRenderable;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.lucent.easygui.interfaces.ITextureData;
import net.lucent.easygui.templating.IRenderableDeserializer;
import net.lucent.easygui.templating.deserializers.BaseDeserializer;
import net.minecraft.client.gui.GuiGraphics;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class Image extends BaseRenderable {

    private ITextureData textureData;
    public Image(IEasyGuiScreen easyGuiScreen, ITextureData textureData, int x, int y) {
        super(easyGuiScreen);
        this.textureData = textureData;
        setX(x);
        setY(y);
    }

    public Image(){}

    public ITextureData getTextureData(){return textureData;}
    public void setTextureData(ITextureData textureData){this.textureData = textureData;}

    @Override
    public void renderSelf(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.textureData.renderTexture(guiGraphics,getX(),getY());
    }


    public static class Deserializer extends BaseDeserializer {

        public Deserializer(Supplier<? extends Image> supplier) {
            super(supplier);
        }

        @Override
        public void buildRenderable(IEasyGuiScreen screen, IRenderableDeserializer parent, JsonObject obj) {
            super.buildRenderable(screen, parent, obj);
            ((Image) getRenderable()).setTextureData(parseTexture("texture",obj));
        }

        @Override
        public @NotNull IRenderableDeserializer createInstance() {
            return new Deserializer((Supplier<? extends Image>) supplier);
        }
    }
}
