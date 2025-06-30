package net.lucent.easygui.interfaces.events;

import net.lucent.easygui.EasyGui;
import net.lucent.easygui.interfaces.ContainerRenderable;
import net.lucent.easygui.templating.actions.IAction;

//TODO
public interface KeyReleasedListener extends ContainerRenderable {
    public interface IKeyReleaseAction extends IAction {

        /**
         DO NOT OVERRIDE THIS UNLESS YOU KNOW WHAT YOU ARE DOING
         */
        @Override
        default void accept(ContainerRenderable renderable, Object[] eventArgs, Object[] customArgs){
            if(eventArgs.length < 3){
                EasyGui.LOGGER.error("error expected at least 3 args got {}",eventArgs.length);
                return;
            }


            Integer keyCode = cast(eventArgs[0],Integer.class);
            Integer scanCode = cast(eventArgs[1],Integer.class);
            Integer modifier = cast(eventArgs[2],Integer.class);

            if(keyCode == null || scanCode == null || modifier == null) return;

            run(renderable,keyCode,scanCode,modifier,customArgs);
        };

        /**
         * Override this
         */
        default void run(ContainerRenderable renderable, int keyCode,int scanCode,int modifier, Object[] args){

        };

    }
    void onKeyReleased(int keyCode, int scanCode,int modifier);
}
