package net.lucent.easygui.interfaces.events;

import net.lucent.easygui.EasyGui;
import net.lucent.easygui.interfaces.ContainerRenderable;
import net.lucent.easygui.templating.actions.IAction;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

//doesn't need variables since they can all be gotten through getInstance
@OnlyIn(Dist.CLIENT)
public interface ScreenResizeListener extends ContainerRenderable {
    public interface IScreenResizedAction extends IAction {

        /**
         DO NOT OVERRIDE THIS UNLESS YOU KNOW WHAT YOU ARE DOING
         */
        @Override
        default void accept(ContainerRenderable renderable, Object[] eventArgs, Object[] customArgs){
            if(eventArgs.length < 3){
                EasyGui.LOGGER.error("error expected at least 3 args got {}",eventArgs.length);
                return;
            }

            Integer oldWidth = cast(eventArgs[0],Integer.class);
            Integer oldHeight = cast(eventArgs[1],Integer.class);
            Double oldScale = cast(eventArgs[2],Double.class);

            if(oldHeight == null || oldWidth == null || oldScale == null ) return;

            run(renderable,oldWidth,oldHeight,oldScale,customArgs);
        };

        /**
         * Override this
         */
        default void run(ContainerRenderable renderable, int oldWidth,int oldHeight,double oldScale, Object[] args){

        };

    }
    void onResize(int oldWidth, int oldHeight,double oldScale);
}
