package net.lucent.easygui.templating;

import com.google.gson.JsonObject;
import net.lucent.easygui.interfaces.ContainerRenderable;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * everything is handled as double. you can then change it to int at the end of the process
 */
public interface IRenderableDeserializer {

    @NotNull
    IRenderableDeserializer createInstance();

    ContainerRenderable getRenderable();

     Double call(String[] statement);

    default Double getX(){
        return (double) getRenderable().getX();
    };
    default Double getGlobalScaledX(){
        return  getRenderable().getGlobalScaledX();
    };

    default Double getWidth(){
        return (double) getRenderable().getWidth();
    };
    default Double getScaledWidth(){

        return (double) getRenderable().getScaledWidth();
    }

    default Double getY(){
        return (double) getRenderable().getY();
    }
    default Double getGlobalScaledY(){
        return getRenderable().getGlobalScaledY();
    }

    default Double getHeight(){
        return (double) getRenderable().getHeight();
    }
    default Double getScaledHeight(){
        return (double) getRenderable().getScaledHeight();
    }

    /**
     * returns a default value. is always a string since most fields can also take a string
     * */
    default String getOrDefault(JsonObject object,String key,String defaultValue){
        if(object.get(key) == null) return defaultValue;
        return object.get(key).getAsString();
    }
    default Boolean getOrDefault(JsonObject object,String key,Boolean defaultValue){
        if(object.get(key) == null) return defaultValue;
        return object.get(key).getAsBoolean();
    }
    default Integer getOrDefault(JsonObject object,String key,Integer defaultValue){
        if(object.get(key) == null) return defaultValue;
        return object.get(key).getAsInt();
    }
    default Double getOrDefault(JsonObject object,String key,Double defaultValue){
        if(object.get(key) == null) return defaultValue;
        return object.get(key).getAsDouble();
    }
    default Object[] getList(JsonObject object,String key){
        System.out.println(object.get(key));
        System.out.println(object.getAsJsonArray(key));
        if(object.getAsJsonArray(key) == null) return new Object[0];
        return object.getAsJsonArray(key).asList().toArray();
    }

    IRenderableDeserializer getParent();

    void buildRenderable(IEasyGuiScreen screen,IRenderableDeserializer parent,JsonObject obj);


    void buildChildren(IEasyGuiScreen screen,JsonObject object);

    default ContainerRenderable build(IEasyGuiScreen screen, IRenderableDeserializer parent, JsonObject obj){
        buildRenderable(screen,parent,obj);
        buildChildren(screen,obj);
        return getRenderable();
    }
}
