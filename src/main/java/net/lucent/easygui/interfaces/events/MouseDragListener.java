package net.lucent.easygui.interfaces.events;

import net.lucent.easygui.EasyGui;
import net.lucent.easygui.interfaces.ContainerRenderable;
import net.lucent.easygui.templating.actions.IAction;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface MouseDragListener extends ContainerRenderable {
    public interface IMouseDragAction extends IAction {

        /**
         DO NOT OVERRIDE THIS UNLESS YOU KNOW WHAT YOU ARE DOING
         */
        @Override
        default void accept(ContainerRenderable renderable, Object[] eventArgs, Object[] customArgs){
            if(eventArgs.length < 5){
                EasyGui.LOGGER.error("error expected at least 5 args got {}",eventArgs.length);
                return;
            }
            Double mouseX = cast(eventArgs[0],Double.class);
            Double mouseY = cast(eventArgs[1],Double.class);
            Integer button = cast(eventArgs[2],Integer.class);
            Double dragX = cast(eventArgs[3],Double.class);
            Double dragY = cast(eventArgs[4],Double.class);


            if(mouseX == null || mouseY == null || button == null || dragX == null || dragY == null ) return;

            run(renderable,mouseX,mouseY,button,dragX,dragY,customArgs);
        };

        /**
         * Override this
         */
        default void run(ContainerRenderable renderable, double mouseX, double mouseY, int button, double dragX,double dragY, Object[] args){

        };

    }
    //does not need to actually drag just reacts to it happening
    void onDrag(double mouseX, double mouseY, int button, double dragX, double dragY);

}
