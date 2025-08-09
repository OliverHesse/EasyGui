package net.lucent.easygui.handlers.event_handlers;

import net.lucent.easygui.interfaces.ContainerRenderable;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TickEventHandler extends AbstractGuiEventHandler<ContainerRenderable>{
    public void call(){
        for(ContainerRenderable listener: getHandlerList()){
            listener.tick();
        }
    }
}
