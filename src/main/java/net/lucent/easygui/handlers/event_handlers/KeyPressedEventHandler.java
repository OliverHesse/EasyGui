package net.lucent.easygui.handlers.event_handlers;


import net.lucent.easygui.interfaces.events.KeyPressedListener;

/**
 * does not care about call order
 */
public class KeyPressedEventHandler extends AbstractGuiEventHandler<KeyPressedListener>{
    public void call(int keyCode, int scanCode,int modifier){
        for(KeyPressedListener listener: getHandlerList()){
            if(listener.isActive()) listener.onKeyPressed(keyCode,scanCode,modifier);
        }
    }
}
