package net.Lucent.EasyGui.handlers;


import net.Lucent.EasyGui.interfaces.events.Hoverable;

import java.util.ListIterator;

/**
 * called every tick and calls onMouse over on the first active item
 * if mouse is over and first state = true. otherwise false
 * so you can still have mouse be over but state be false
 *
 * TODO use z index instead of creating order? or make it a setting?
 */
public class MouseOverEventHandler extends AbstractGuiEventHandler<Hoverable> {
    public void call(double mouseX,double mouseY){

        ListIterator<Hoverable> itr = getLastIterator();
        boolean firstFound = false;
        while(itr.hasPrevious()){
            Hoverable listener = itr.previous();
            if(listener.isMouseOver(mouseX,mouseY) && listener.isActive() && !firstFound){
                firstFound = true;
            }
            listener.onMouseOver(firstFound);

        }

    }
}
