package net.lucent.easygui.handlers.event_handlers;


import net.lucent.easygui.interfaces.events.MouseReleaseListener;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

/**
 * does not care about call order
 */
@OnlyIn(Dist.CLIENT)
public class MouseReleaseEventHandler extends AbstractGuiEventHandler<MouseReleaseListener> {

    public void call(double mouseX, double mouseY, int button){
        for(MouseReleaseListener listener : getHandlerList()){
            if(listener.isActive()) listener.onMouseReleased(mouseX,mouseY,button);
        }
    }
}
