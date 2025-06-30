package net.lucent.easygui.elements.containers.scroll_boxes;

import com.google.gson.JsonObject;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.lucent.easygui.templating.IRenderableDeserializer;
import net.lucent.easygui.templating.deserializers.SquareRenderableDeserializer;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class FixedSizedScrollBox extends AbstractScrollBox{

    public double scrollHeight;
    public double scrollWidth;

    public FixedSizedScrollBox(){}

    public FixedSizedScrollBox(IEasyGuiScreen easyGuiScreen, int x, int y, int width, int height) {
        super(easyGuiScreen, x, y, width, height);
    }

    @Override
    public double getScrollHeight() {
        return scrollHeight;
    }

    @Override
    public double getScrollWidth() {
        return scrollWidth;
    }

    @Override
    public double getScrollRate() {
        return 9;
    }

    public void setScrollWidth(double scrollWidth) {
        this.scrollWidth = scrollWidth;
    }

    public void setScrollHeight(double scrollHeight) {
        this.scrollHeight = scrollHeight;
    }
    public static class Deserializer extends SquareRenderableDeserializer {

        public Deserializer(Supplier<? extends FixedSizedScrollBox> supplier) {
            super(supplier);
        }

        @Override
        public void buildRenderable(IEasyGuiScreen screen, IRenderableDeserializer parent, JsonObject obj) {
            super.buildRenderable(screen, parent, obj);
            ((FixedSizedScrollBox) getRenderable()).setScrollHeight(getOrDefault(obj,"scroll_height",0.0));
            ((FixedSizedScrollBox) getRenderable()).setScrollWidth(getOrDefault(obj,"scroll_width",0.0));

        }

        @Override
        public @NotNull IRenderableDeserializer createInstance() {
            return new Deserializer((Supplier<? extends FixedSizedScrollBox>) supplier);
        }
    }
}
