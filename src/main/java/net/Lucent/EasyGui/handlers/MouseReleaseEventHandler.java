package net.Lucent.EasyGui.handlers;


import net.Lucent.EasyGui.interfaces.events.MouseReleaseListener;

/**
 * does not care about call order
 */
public class MouseReleaseEventHandler extends AbstractGuiEventHandler<MouseReleaseListener> {

    public void call(double mouseX, double mouseY, int button){
        for(MouseReleaseListener listener : HANDLER_LIST){
            if(listener.isActive()) listener.onMouseReleased(mouseX,mouseY,button);
        }
    }
}
