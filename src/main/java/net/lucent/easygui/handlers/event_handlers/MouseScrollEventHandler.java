package net.lucent.easygui.handlers.event_handlers;


import net.lucent.easygui.interfaces.events.MouseScrollListener;
//TODO add a first called
public class MouseScrollEventHandler extends AbstractGuiEventHandler<MouseScrollListener>{

    public void call(double mouseX, double mouseY, double scrollX, double scrollY){
        for(MouseScrollListener listener: getHandlerList()){
            if(listener.isActive()) listener.onMouseScroll(mouseX,mouseY,scrollX,scrollY);
        }
    }
}
