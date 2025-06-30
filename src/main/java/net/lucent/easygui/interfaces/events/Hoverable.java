package net.lucent.easygui.interfaces.events;

import net.lucent.easygui.EasyGui;
import net.lucent.easygui.interfaces.ContainerRenderable;
import net.lucent.easygui.templating.actions.IAction;

public interface Hoverable extends ContainerRenderable {
    public interface IHoverAction extends IAction {

        /**
         DO NOT OVERRIDE THIS UNLESS YOU KNOW WHAT YOU ARE DOING
         */
        @Override
        default void accept(ContainerRenderable renderable, Object[] eventArgs, Object[] customArgs){
            if(eventArgs.length !=  1){
                EasyGui.LOGGER.error("error expected 1 arg got {}",eventArgs.length);
                return;
            }
            Boolean state = cast(eventArgs[0],Boolean.class);

            if(state == null) return;

            run(renderable,state,customArgs);
        };

        /**
         * Override this
         */
        default void run(ContainerRenderable renderable,boolean state, Object[] args){

        };

    }
    default boolean isMouseOver(double mouseX,double mouseY) {return false;}
    default void onMouseOver(boolean state){};
}
