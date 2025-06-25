package net.lucent.easygui.templating.deserializers;

import com.google.gson.JsonObject;
import net.lucent.easygui.elements.containers.View;
import net.lucent.easygui.interfaces.ContainerRenderable;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.lucent.easygui.templating.IRenderableDeserializer;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ViewDeserializer extends BaseDeserializer{

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

    public ViewDeserializer(Supplier<View> supplier) {
        super(supplier);
    }

    @Override
    public @NotNull IRenderableDeserializer createInstance() {
        return new ViewDeserializer((Supplier<View>) supplier);
    }
}
