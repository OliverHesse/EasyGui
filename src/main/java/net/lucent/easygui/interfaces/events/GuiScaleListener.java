package net.lucent.easygui.interfaces.events;

import net.lucent.easygui.EasyGui;
import net.lucent.easygui.interfaces.ContainerRenderable;
import net.lucent.easygui.templating.actions.IAction;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface GuiScaleListener extends ContainerRenderable {
    public interface IScaleChanged extends IAction {

        /**
         DO NOT OVERRIDE THIS UNLESS YOU KNOW WHAT YOU ARE DOING
         */
        @Override
        default void accept(ContainerRenderable renderable, Object[] eventArgs, Object[] customArgs){
            if(eventArgs.length !=  1){
                EasyGui.LOGGER.error("error expected 1 arg got {}",eventArgs.length);
                return;
            }
            Double scale = cast(eventArgs[0],Double.class);

            if(scale == null) return;

            run(renderable,scale,customArgs);
        };

        /**
         * Override this
         */
        default void run(ContainerRenderable renderable,double state, Object[] args){

        };

    }
    void onGuiScaleChanged(double oldScale);
}
