package net.lucent.easygui.templating.deserializers;

import com.google.gson.JsonObject;
import net.lucent.easygui.elements.containers.panels.Panel;
import net.lucent.easygui.elements.controls.buttons.ColorButton;
import net.lucent.easygui.elements.other.SquareRenderable;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.lucent.easygui.templating.IRenderableDeserializer;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class SquareRenderableDeserializer extends BaseDeserializer{
    public SquareRenderableDeserializer(Supplier<? extends SquareRenderable> supplier) {
        super(supplier);
    }

    @Override
    public void buildRenderable(IEasyGuiScreen screen, IRenderableDeserializer parent, JsonObject obj) {
        super.buildRenderable(screen, parent, obj);
        if(obj.getAsJsonObject("on_hover") != null){
            ((ColorButton) getRenderable()).hoverAction =  parseAction("on_hover",obj);
        }

    }

    @Override
    public @NotNull IRenderableDeserializer createInstance() {
        return new SquareRenderableDeserializer((Supplier<? extends SquareRenderable>) supplier);
    }
}
