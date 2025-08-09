package net.lucent.easygui.handlers.event_handlers;


import net.lucent.easygui.interfaces.events.MouseScrollListener;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

//TODO add a first called
@OnlyIn(Dist.CLIENT)
public class MouseScrollEventHandler extends AbstractGuiEventHandler<MouseScrollListener>{

    public void call(double mouseX, double mouseY, double scrollX, double scrollY){
        for(MouseScrollListener listener: getHandlerList()){
            if(listener.isActive()) listener.onMouseScroll(mouseX,mouseY,scrollX,scrollY);
        }
    }
}
