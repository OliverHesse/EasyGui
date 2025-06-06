package net.Lucent.EasyGui.handlers;

import net.Lucent.EasyGui.interfaces.events.MouseMovedListener;

public class MouseMovedEventHandler extends AbstractGuiEventHandler<MouseMovedListener>{

    public void call(double mouseX,double mouseY){
        for(MouseMovedListener listener : HANDLER_LIST){
            if(listener.isActive()) listener.onMouseMoved(mouseX,mouseY);
        }
    }
}
