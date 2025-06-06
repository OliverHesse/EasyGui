package net.Lucent.EasyGui.handlers;


import net.Lucent.EasyGui.interfaces.events.KeyPressedListener;

/**
 * does not care about call order
 */
public class KeyPressedEventHandler extends AbstractGuiEventHandler<KeyPressedListener>{
    public void call(int keyCode, int scanCode,int modifier){
        for(KeyPressedListener listener: HANDLER_LIST){
            if(listener.isActive()) listener.onKeyPressed(keyCode,scanCode,modifier);
        }
    }
}
