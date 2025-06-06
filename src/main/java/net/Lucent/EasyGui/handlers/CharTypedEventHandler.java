package net.Lucent.EasyGui.handlers;


import net.Lucent.EasyGui.interfaces.events.CharTypedListener;

public class CharTypedEventHandler extends AbstractGuiEventHandler<CharTypedListener>{

    public void call(char codePoint, int modifiers){
        for(CharTypedListener listener: HANDLER_LIST){
            if(listener.isActive()) listener.onCharTyped(codePoint,modifiers);

        }
    }
}
