package net.Lucent.EasyGui.handlers;

import net.Lucent.EasyGui.interfaces.events.GuiScaleListener;
import net.Lucent.EasyGui.interfaces.events.ScreenResizeListener;

public class GuiScaleChangedEventHandler extends AbstractGuiEventHandler<GuiScaleListener>{

    public void call(double oldScale){
        for(GuiScaleListener listener: HANDLER_LIST){
            if(listener.isActive())   listener.onGuiScaleChanged(oldScale);

        }
    }
}
