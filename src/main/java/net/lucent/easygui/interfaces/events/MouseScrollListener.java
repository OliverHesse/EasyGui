package net.lucent.easygui.interfaces.events;

import net.lucent.easygui.EasyGui;
import net.lucent.easygui.interfaces.ContainerRenderable;
import net.lucent.easygui.templating.actions.IAction;

public interface MouseScrollListener extends ContainerRenderable {
    public interface IClickAction extends IAction {

        /**
         DO NOT OVERRIDE THIS UNLESS YOU KNOW WHAT YOU ARE DOING
         */
        @Override
        default void accept(ContainerRenderable renderable, Object[] eventArgs, Object[] customArgs){
            if(eventArgs.length < 4){
                EasyGui.LOGGER.error("error expected at least 4 args got {}",eventArgs.length);
                return;
            }
            Double mouseX = cast(eventArgs[0],Double.class);
            Double mouseY = cast(eventArgs[1],Double.class);
            Double scrollX = cast(eventArgs[2],Double.class);
            Double scrollY = cast(eventArgs[3],Double.class);

            if(mouseX == null || mouseY == null || scrollX == null || scrollY == null) return;

            run(renderable,mouseX,mouseY,scrollX,scrollY,customArgs);
        };

        /**
         * Override this
         */
        default void run(ContainerRenderable renderable, double mouseX, double mouseY, double scrollX, double scrollY, Object[] args){

        };

    }
    void onMouseScroll(double mouseX, double mouseY, double scrollX, double scrollY);

}
