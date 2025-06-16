package net.lucent.easygui.handlers.event_handlers;

import net.lucent.easygui.interfaces.ContainerRenderable;

public class TickEventHandler extends AbstractGuiEventHandler<ContainerRenderable>{
    public void call(){
        for(ContainerRenderable listener: HANDLER_LIST){
            listener.tick();
        }
    }
}
