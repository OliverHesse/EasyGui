package net.lucent.easygui.handlers.event_handlers;

import net.lucent.easygui.interfaces.events.MouseMovedListener;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MouseMovedEventHandler extends AbstractGuiEventHandler<MouseMovedListener>{

    public void call(double mouseX,double mouseY){
        for(MouseMovedListener listener : getHandlerList()){
            if(listener.isActive()) listener.onMouseMoved(mouseX,mouseY);
        }
    }
}
