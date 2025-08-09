package net.lucent.easygui.interfaces.events;

import net.lucent.easygui.EasyGui;
import net.lucent.easygui.interfaces.ContainerRenderable;
import net.lucent.easygui.templating.actions.IAction;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface MouseMovedListener extends ContainerRenderable {
    public interface IMouseMovedAction extends IAction {

        /**
         DO NOT OVERRIDE THIS UNLESS YOU KNOW WHAT YOU ARE DOING
         */
        @Override
        default void accept(ContainerRenderable renderable, Object[] eventArgs, Object[] customArgs){
            if(eventArgs.length < 2){
                EasyGui.LOGGER.error("error expected at least 2 args got {}",eventArgs.length);
                return;
            }
            Double mouseX = cast(eventArgs[0],Double.class);
            Double mouseY = cast(eventArgs[1],Double.class);


            if(mouseX == null || mouseY == null ) return;

            run(renderable,mouseX,mouseY,customArgs);
        };

        /**
         * Override this
         */
        default void run(ContainerRenderable renderable, double mouseX, double mouseY, Object[] args){

        };

    }
    void onMouseMoved(double mouseX, double mouseY);
}
