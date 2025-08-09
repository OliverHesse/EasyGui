package net.lucent.easygui.interfaces.events;

import net.lucent.easygui.EasyGui;
import net.lucent.easygui.interfaces.ContainerRenderable;
import net.lucent.easygui.templating.actions.IAction;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

/**
 * just calls when mouse is released regardless of location
 */@OnlyIn(Dist.CLIENT)
public interface MouseReleaseListener extends ContainerRenderable {
    public interface IClickAction extends IAction {

        /**
         DO NOT OVERRIDE THIS UNLESS YOU KNOW WHAT YOU ARE DOING
         */
        @Override
        default void accept(ContainerRenderable renderable, Object[] eventArgs, Object[] customArgs){
            if(eventArgs.length < 3){
                EasyGui.LOGGER.error("error expected at least 3 args got {}",eventArgs.length);
                return;
            }
            Double mouseX = cast(eventArgs[0],Double.class);
            Double mouseY = cast(eventArgs[1],Double.class);
            Integer button = cast(eventArgs[2],Integer.class);

            if(mouseX == null || mouseY == null || button == null ) return;

            run(renderable,mouseX,mouseY,button,customArgs);
        };

        /**
         * Override this
         */
        default void run(ContainerRenderable renderable, double mouseX, double mouseY, int button, Object[] args){

        };

    }
    void onMouseReleased(double mouseX, double mouseY, int button);
}
