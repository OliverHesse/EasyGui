package net.lucent.easygui.templating.actions;

import net.lucent.easygui.EasyGui;
import net.lucent.easygui.interfaces.ContainerRenderable;

public interface IAction{

    default <T> T cast(Object obj, Class<T> type){
        if(type.isInstance(obj)) return ((T) obj);
        EasyGui.LOGGER.error("Action argument cast expected Type {} but got {}", type, obj.getClass());
        return null;
    }

    default void accept(ContainerRenderable renderable,Object[] eventArgs, Object[] customArgs){
        run(renderable,customArgs);
    }

    //raw actions don't care about the event
    default void run(ContainerRenderable renderable,Object[] customArgs){

    }
}
