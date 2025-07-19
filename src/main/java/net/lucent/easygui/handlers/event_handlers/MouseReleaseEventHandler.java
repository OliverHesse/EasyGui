package net.lucent.easygui.handlers.event_handlers;


import net.lucent.easygui.interfaces.events.MouseReleaseListener;

/**
 * does not care about call order
 */
public class MouseReleaseEventHandler extends AbstractGuiEventHandler<MouseReleaseListener> {

    public void call(double mouseX, double mouseY, int button){
        for(MouseReleaseListener listener : getHandlerList()){
            if(listener.isActive()) listener.onMouseReleased(mouseX,mouseY,button);
        }
    }
}
