package net.lucent.easygui.handlers.event_handlers;


import net.lucent.easygui.interfaces.events.ScreenResizeListener;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenResizeEventHandler extends AbstractGuiEventHandler<ScreenResizeListener> {
    public void call(int oldWidth,int oldHeight,double oldScale){
        for(ScreenResizeListener listener: getHandlerList()){
            if(listener.isActive())   listener.onResize(oldWidth,oldHeight,oldScale);

        }
    }
}
