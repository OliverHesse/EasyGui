package net.lucent.easygui.handlers.event_handlers;


import net.lucent.easygui.interfaces.events.KeyReleasedListener;

//TODO
public class KeyReleasedEventHandler extends AbstractGuiEventHandler<KeyReleasedListener> {

    public void call(int keyCode, int scanCode,int modifier){
        for(KeyReleasedListener listener:HANDLER_LIST){
            if(listener.isActive()) listener.onKeyReleased(keyCode,scanCode,modifier);
        }
    }

}
