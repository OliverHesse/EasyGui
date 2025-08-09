package net.lucent.easygui.handlers.event_handlers;


import net.lucent.easygui.interfaces.events.KeyPressedListener;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

/**
 * does not care about call order
 */
@OnlyIn(Dist.CLIENT)
public class KeyPressedEventHandler extends AbstractGuiEventHandler<KeyPressedListener>{
    public void call(int keyCode, int scanCode,int modifier){
        for(KeyPressedListener listener: getHandlerList()){
            if(listener.isActive()) listener.onKeyPressed(keyCode,scanCode,modifier);
        }
    }
}
