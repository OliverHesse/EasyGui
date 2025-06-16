package net.lucent.easygui.handlers.event_handlers;


import net.lucent.easygui.interfaces.events.MouseScrollListener;

public class MouseScrollEventHandler extends AbstractGuiEventHandler<MouseScrollListener>{

    public void call(double mouseX, double mouseY, double scrollX, double scrollY){
        for(MouseScrollListener listener: HANDLER_LIST){
            if(listener.isActive()) listener.onMouseScroll(mouseX,mouseY,scrollX,scrollY);
        }
    }
}
