package net.Lucent.EasyGui.handlers;


import net.Lucent.EasyGui.interfaces.events.Clickable;

import java.util.ListIterator;

/**
 * cares about call order
 */
public class ClickEventHandler extends AbstractGuiEventHandler<Clickable> {
    public void call(double mouseX,double mouseY,int button) {
        ListIterator<Clickable> itr = getLastIterator();
        while(itr.hasPrevious()){
            Clickable listener = itr.previous();
            if(listener.isMouseOver(mouseX,mouseY) && listener.isActive()){
                listener.onClick(mouseX,mouseY,button);
                return;
            }
        }


    }
}
