package net.lucent.easygui.handlers.event_handlers;


import net.lucent.easygui.interfaces.events.CharTypedListener;

public class CharTypedEventHandler extends AbstractGuiEventHandler<CharTypedListener>{

    public void call(char codePoint, int modifiers){
        for(CharTypedListener listener: getHandlerList()){
            if(listener.isActive()) listener.onCharTyped(codePoint,modifiers);

        }
    }
}
