package net.lucent.easygui.handlers.event_handlers;


import net.lucent.easygui.interfaces.events.Clickable;

import java.util.ListIterator;

/**
 * cares about call order
 */
public class ClickEventHandler extends AbstractGuiEventHandler<Clickable> {
    public void call(double mouseX,double mouseY,int button) {

        ListIterator<Clickable> itr = getLastIterator();
        boolean firstFound = false;
        while(itr.hasPrevious()){

            Clickable listener = itr.previous();
            
            if(listener.isMouseOver(mouseX,mouseY) && listener.isActive() && !firstFound){
                listener.onClick(mouseX,mouseY,button,true);
                firstFound = true;
            }
            else listener.onClick(mouseX,mouseY,button,false);
        }


    }
}
