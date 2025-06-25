package net.lucent.easygui.templating;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.lucent.easygui.EasyGui;
import net.lucent.easygui.elements.containers.View;
import net.lucent.easygui.interfaces.ContainerRenderable;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.lucent.easygui.templating.deserializers.BaseDeserializer;
import net.lucent.easygui.templating.deserializers.ViewDeserializer;
import net.lucent.easygui.templating.registry.EasyGuiRegistries;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class EasyGuiBuilder {


    public ResourceLocation templateFile;

    public EasyGuiBuilder(ResourceLocation template_file){
        this.templateFile =template_file;
    }
    public JsonObject getJsonData() throws IOException {
        try (InputStream in = Minecraft.getInstance().getResourceManager().open(templateFile)) {

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            JsonElement je = JsonParser.parseReader(reader);
            return je.getAsJsonObject();
        }
    }
    public void build(IEasyGuiScreen screen) throws IOException {



        System.out.println("fetching data");
        JsonObject data = getJsonData();
        System.out.println("building");
        if(data.getAsJsonArray("views") == null){
            EasyGui.LOGGER.error("cannot parse screen template could not find list views");
            return;
        }

        for(JsonElement obj : data.getAsJsonArray("views")){
            if(obj.getAsJsonObject() == null){
                EasyGui.LOGGER.error("unexpected value expected view object");
                return;
            }
            System.out.println("building view");
            JsonObject childObj = obj.getAsJsonObject();
            if(childObj.get("type")==null) continue;
            String[] args = childObj.get("type").getAsString().split(":");
            IRenderableDeserializer deserializer = EasyGuiRegistries.Deserializers.DESERIALIZERS.getRegistry().get().get(ResourceLocation.fromNamespaceAndPath(
                args[0],args[1]
            ));
            if(deserializer != null) deserializer = deserializer.createInstance();
            else continue;
            ContainerRenderable renderable = deserializer.build(screen,null,obj.getAsJsonObject());
            System.out.println("added View" + renderable);
            screen.addView((View) renderable);
        }

    }
}
