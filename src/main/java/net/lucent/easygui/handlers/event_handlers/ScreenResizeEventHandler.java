package net.lucent.easygui.handlers.event_handlers;


import net.lucent.easygui.interfaces.events.ScreenResizeListener;

public class ScreenResizeEventHandler extends AbstractGuiEventHandler<ScreenResizeListener> {
    public void call(int oldWidth,int oldHeight){
        for(ScreenResizeListener listener: HANDLER_LIST){
            if(listener.isActive())   listener.onResize(oldWidth,oldHeight);

        }
    }
}
