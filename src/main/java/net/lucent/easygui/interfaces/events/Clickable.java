package net.lucent.easygui.interfaces.events;

import net.lucent.easygui.EasyGui;
import net.lucent.easygui.interfaces.ContainerRenderable;
import net.lucent.easygui.templating.actions.IAction;

public interface Clickable extends Hoverable {
    public interface IClickAction extends IAction{

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
            Integer button = cast(eventArgs[2],Integer.class);
            Boolean clicked = cast(eventArgs[3],Boolean.class);

            if(mouseX == null || mouseY == null || button == null || clicked == null) return;

            run(renderable,mouseX,mouseY,button,clicked,customArgs);
        };

        /**
         * Override this
         */
        default void run(ContainerRenderable renderable, double mouseX, double mouseY, int button, boolean clicked, Object[] args){

        };

    }
    void onClick(double mouseX,double mouseY,int button,boolean clicked);
}