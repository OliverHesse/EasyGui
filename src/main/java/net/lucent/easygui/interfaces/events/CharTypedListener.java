package net.lucent.easygui.interfaces.events;

import net.lucent.easygui.EasyGui;
import net.lucent.easygui.interfaces.ContainerRenderable;
import net.lucent.easygui.templating.actions.IAction;

//TODO
public interface CharTypedListener extends ContainerRenderable {
    public interface ICharTypedAction extends IAction {

        /**
         DO NOT OVERRIDE THIS UNLESS YOU KNOW WHAT YOU ARE DOING
         */
        @Override
        default void accept(ContainerRenderable renderable, Object[] eventArgs, Object[] customArgs){
            if(eventArgs.length !=  2){
                EasyGui.LOGGER.error("error expected 2 args got {}",eventArgs.length);
                return;
            }
            Character character = cast(eventArgs[0],Character.class);
            Integer mod = cast(eventArgs[1],Integer.class);

            if(character == null || mod == null) return;

            run(renderable,character,mod,customArgs);
        };

        /**
         * Override this
         */
        default void run(ContainerRenderable renderable,char codePoint,int modifiers, Object[] args){

        };

    }
    void onCharTyped(char codePoint, int modifiers);
}
