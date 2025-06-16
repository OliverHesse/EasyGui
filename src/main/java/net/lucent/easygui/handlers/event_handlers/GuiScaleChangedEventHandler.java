package net.lucent.easygui.handlers.event_handlers;

import net.lucent.easygui.interfaces.events.GuiScaleListener;

public class GuiScaleChangedEventHandler extends AbstractGuiEventHandler<GuiScaleListener>{

    public void call(double oldScale){
        for(GuiScaleListener listener: HANDLER_LIST){
            if(listener.isActive())   listener.onGuiScaleChanged(oldScale);

        }
    }
}
