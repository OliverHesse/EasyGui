package net.Lucent.EasyGui.handlers;

import net.Lucent.EasyGui.interfaces.ContainerRenderable;

public class TickEventHandler extends AbstractGuiEventHandler<ContainerRenderable>{
    public void call(){
        for(ContainerRenderable listener: HANDLER_LIST){
            listener.tick();
        }
    }
}
