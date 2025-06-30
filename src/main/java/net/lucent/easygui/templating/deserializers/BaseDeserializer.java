package net.lucent.easygui.templating.deserializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.lucent.easygui.EasyGui;
import net.lucent.easygui.elements.BaseRenderable;
import net.lucent.easygui.elements.containers.View;
import net.lucent.easygui.elements.containers.panels.DraggablePanel;
import net.lucent.easygui.elements.containers.panels.Panel;
import net.lucent.easygui.elements.other.Label;
import net.lucent.easygui.interfaces.ContainerRenderable;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.lucent.easygui.interfaces.ITextureData;
import net.lucent.easygui.templating.IRenderableDeserializer;
import net.lucent.easygui.templating.actions.Action;
import net.lucent.easygui.templating.parsers.RPNParser;
import net.lucent.easygui.templating.parsers.ShuntingYardExprParser;
import net.lucent.easygui.templating.registry.EasyGuiRegistries;
import net.lucent.easygui.util.textures.TextureDataSubSection;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import javax.management.AttributeNotFoundException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Supplier;

public class BaseDeserializer implements IRenderableDeserializer {

    private BaseRenderable renderable;
    private IRenderableDeserializer parent;


    private final HashMap<String,Supplier<Double>> dataSuppliers = new HashMap<>(){{
        put("x",BaseDeserializer.this::getX);
        put("y",BaseDeserializer.this::getY);
        put("globalX",BaseDeserializer.this::getGlobalScaledX);
        put("globalY",BaseDeserializer.this::getGlobalScaledY);

        put("width",BaseDeserializer.this::getWidth);
        put("height",BaseDeserializer.this::getHeight);
        put("scaledWidth",BaseDeserializer.this::getScaledWidth);
        put("scaledHeight",BaseDeserializer.this::getScaledHeight);
    }};

    public final Supplier<? extends BaseRenderable> supplier;

    public BaseDeserializer(Supplier<? extends BaseRenderable> supplier){
        this.supplier = supplier;
    }


    @Override
    public @NotNull IRenderableDeserializer createInstance() {
        return new BaseDeserializer(supplier);
    }

    @Override
    public BaseRenderable getRenderable() {
        return renderable;
    }

    @Override
    public Double call(String[] functionName) {
        System.out.println("called");
        System.out.println(Arrays.toString(functionName));
        if(functionName.length == 0){
            return null;
        }
        if(functionName[0].equals("parent")){
            return getParent().call(Arrays.stream(functionName,1,functionName.length).toArray(String[]::new));
        }
        return dataSuppliers.get(functionName[0]).get();

    }

    @Override
    public IRenderableDeserializer getParent() {
        return parent;
    }

    public void parseWidth(String expr){
        renderable.setWidth(RPNParser.parse(ShuntingYardExprParser.parseInput(expr),this).intValue());
    };
    public void parseHeight(String expr){
        renderable.setHeight(RPNParser.parse(ShuntingYardExprParser.parseInput(expr),this).intValue());
    };
    public void parseX(String expr){
        renderable.setX(RPNParser.parse(ShuntingYardExprParser.parseInput(expr),this).intValue());
    };
    public void parseY(String expr){
        renderable.setY(RPNParser.parse(ShuntingYardExprParser.parseInput(expr),this).intValue());
    };

    public void parseRotation(String expr1, String expr2,String expr3){
        double x = RPNParser.parse(ShuntingYardExprParser.parseInput(expr1),this);
        double y = RPNParser.parse(ShuntingYardExprParser.parseInput(expr2),this);
        double z = RPNParser.parse(ShuntingYardExprParser.parseInput(expr3),this);
        renderable.setRotation(x,y,z);
    }

    public Action parseAction(String key,JsonObject obj){
        System.out.println("parsing action");
        JsonObject functionObj = obj.getAsJsonObject(key);
        String function = getOrDefault(functionObj,"function", (String) null);
        String functionName;
        String functionModId;

        Object[] args = getList(functionObj,"args");
        System.out.println(args);
        if(function != null){
            System.out.println(function);
            functionName = function.split(":")[1];
            functionModId = function.split(":")[0];
            return new Action(EasyGuiRegistries.Actions.ACTION_REGISTRY.get(
                    ResourceLocation.fromNamespaceAndPath(functionModId,functionName)),
                    args);
        }
        return null;
    }

    public void throwMissingField(String msg) {
        throw new RuntimeException("Parsing Error: "+msg);
    }

    public ITextureData parseTexture(String key,JsonObject object){
        if(object.getAsJsonObject(key) == null) return null;
        JsonObject textureObj = object.getAsJsonObject(key);

        String resourceLocation = textureObj.get("file").getAsString();
        Integer textureWidth = getOrDefault(textureObj,"texture_width",(Integer) null);
        Integer textureHeight = getOrDefault(textureObj,"texture_height",(Integer) null);
        if(textureWidth == null) throwMissingField("expected field texture_width");
        if(textureHeight == null) throwMissingField("expected field texture_height");
        Integer width = getOrDefault(textureObj,"width",textureWidth);
        Integer height = getOrDefault(textureObj,"height",textureHeight);
        Integer u = getOrDefault(textureObj,"u",0);
        Integer v = getOrDefault(textureObj,"v",0);
        return new TextureDataSubSection(ResourceLocation.fromNamespaceAndPath(resourceLocation.split(":")[0],resourceLocation.split(":")[1]),
                textureWidth,
                textureHeight,
                u,
                v,
                width,
                height);
    }
    public Component parseComponent(String key, JsonObject obj){
        if(obj.getAsJsonObject(key) != null){
            JsonObject textObject = obj.getAsJsonObject(key);
            System.out.println(textObject);
            String type = textObject.get("type").getAsString();
            String text = textObject.get("data").getAsString();
            System.out.println(type);
            System.out.println(text);
            if(type == null) throwMissingField("missing field Type for label text");
            if(text == null) throwMissingField("missing data field for label text");
            if(type.equals("raw")){
                return Component.literal(text);
            }else if(type.equals("key")){
                return Component.translatable(text);
            }else if(type.equals("key_bind")){
                return Component.keybind(text);
            }

        }
        return null;
    }
    @Override
    public void buildRenderable(IEasyGuiScreen screen, IRenderableDeserializer parent, JsonObject obj) {

        this.parent = parent;
        renderable = supplier.get();
        System.out.println("parsing renderable of type: "+renderable);
        renderable.setScreen(screen);
        String sX = getOrDefault(obj,"x","0");
        String sY = getOrDefault(obj,"y","0");
        String sWidth = getOrDefault(obj,"width","0");
        String sHeight = getOrDefault(obj,"height","0");

        System.out.println("got x y width and height");

        Boolean cull = getOrDefault(obj,"cull",false);
        Boolean visible = getOrDefault(obj,"visible",true);
        Boolean active = getOrDefault(obj,"active",true);
        Integer cullBorder = getOrDefault(obj,"cull_border",0);
        Double customScaling = getOrDefault(obj,"scale",1.0);
        Boolean useCustomScaling = getOrDefault(obj,"use_scale",true);
        Boolean sticky = getOrDefault(obj,"sticky",false);
        System.out.println("got a bunch of random stuff");
        getRenderable().setCustomScale(customScaling);
        getRenderable().setSticky(sticky);
        getRenderable().setActive(active);
        getRenderable().setCull(cull);
        getRenderable().setActive(active);
        getRenderable().setVisible(visible);
        getRenderable().setCullBorder(cullBorder);
        getRenderable().useCustomScaling= useCustomScaling;
        String sRotationX = "0";
        String sRotationY = "0";
        String sRotationZ = "0";
        if(obj.getAsJsonObject("rotation") != null){
            JsonObject rotationObj = obj.getAsJsonObject("rotation");
            sRotationX = getOrDefault(rotationObj,"x","0");
            sRotationY = getOrDefault(rotationObj,"y","0");
            sRotationZ= getOrDefault(rotationObj,"z","0");
        }
        System.out.println("got rotation");
        String id = getOrDefault(obj,"id", (String) null);
        String classes = getOrDefault(obj,"class", (String) null);
        if(classes != null){
            for(String item:classes.trim().split("\\s+")){
                getRenderable().addClass(item.trim());
            }
        }
        getRenderable().setID(id);
        System.out.println("got class and id");

        if(obj.getAsJsonObject("on_tick") != null){
            renderable.setTickAction(parseAction("on_tick",obj));
        }
        if(obj.getAsJsonObject("on_resize") != null){
            renderable.setTickAction(parseAction("on_resize",obj));
        }
        if(obj.getAsJsonObject("on_scale_changed") != null){
            renderable.setTickAction(parseAction("on_scale_changed",obj));
        }

        System.out.println("got tick");

        parseRotation(sRotationX,sRotationY,sRotationZ);
        parseWidth(sWidth);
        parseHeight(sHeight);
        parseX(sX);
        parseY(sY);

        System.out.println("parsed rotation data properly");
        System.out.println("parsing "+id);

        System.out.println("x : "+ renderable.getX());
        System.out.println("y: "+renderable.getY());
        System.out.println("scaledWidth : " + renderable.getScaledWidth());

    }



    @Override
    public void buildChildren(IEasyGuiScreen screen, JsonObject object) {
        if(object.getAsJsonArray("children") == null) return;
        for(JsonElement childElement : object.getAsJsonArray("children")){
            if(childElement.getAsJsonObject() == null){
                EasyGui.LOGGER.error("unexpected value expected view object");
                return;
            }

            JsonObject childObj = childElement.getAsJsonObject();
            if(childObj.get("type")==null) continue;
            String[] args = childObj.get("type").getAsString().split(":");
            IRenderableDeserializer deserializer = EasyGuiRegistries.Deserializers.DESERIALIZERS.getRegistry().get().get(ResourceLocation.fromNamespaceAndPath(
                    args[0],args[1]
            ));
            if(deserializer != null) deserializer = deserializer.createInstance();
            else continue;

            ContainerRenderable renderable = deserializer.build(screen,this,childElement.getAsJsonObject());
            getRenderable().addChild(renderable);
        }
    }
}
