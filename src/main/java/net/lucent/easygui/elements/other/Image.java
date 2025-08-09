package net.lucent.easygui.elements.other;

import com.google.gson.JsonObject;
import net.lucent.easygui.elements.BaseRenderable;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.lucent.easygui.interfaces.ITextureData;
import net.lucent.easygui.templating.IRenderableDeserializer;
import net.lucent.easygui.templating.deserializers.BaseDeserializer;
import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;
@OnlyIn(Dist.CLIENT)
public class Image extends SquareRenderable {

    private ITextureData textureData;
    public Image(IEasyGuiScreen easyGuiScreen, ITextureData textureData, int x, int y) {
        super(easyGuiScreen);
        this.textureData = textureData;
        setX(x);
        setY(y);
    }

    @Override
    public int getHeight() {
        if(textureData == null) return 0;
        return textureData.getHeight();
    }

    @Override
    public int getWidth() {
        if(textureData == null) return 0;
        return textureData.getWidth();
    }

    public Image(){}

    public ITextureData getTextureData(){return textureData;}
    public void setTextureData(ITextureData textureData){this.textureData = textureData;}

    @Override
    public void renderSelf(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.textureData.renderTexture(guiGraphics);
    }


    public static class Deserializer extends BaseDeserializer {

        @Override
        public void parseWidth(String expr) {

        }

        @Override
        public void parseHeight(String expr) {

        }

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
