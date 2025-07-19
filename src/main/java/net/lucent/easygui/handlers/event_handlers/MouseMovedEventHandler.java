package net.lucent.easygui.handlers.event_handlers;

import net.lucent.easygui.interfaces.events.MouseMovedListener;

public class MouseMovedEventHandler extends AbstractGuiEventHandler<MouseMovedListener>{

    public void call(double mouseX,double mouseY){
        for(MouseMovedListener listener : getHandlerList()){
            if(listener.isActive()) listener.onMouseMoved(mouseX,mouseY);
        }
    }
}
