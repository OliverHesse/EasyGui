package net.lucent.easygui.gui.events;

import net.lucent.easygui.gui.RenderableElement;
import net.lucent.easygui.gui.UIFrame;
import net.lucent.easygui.gui.listeners.IEasyEventListener;
import net.minecraft.client.Minecraft;

import java.util.List;

public class EventHandler {


    public static void runEvent(EasyEvent event){

        List<RenderableElement> capturePath = EventHelper.createEventPath(event.getTarget());
        event.setEventPhase(EventPhase.CAPTURE);
        for(RenderableElement element : capturePath){
            event.setCurrentElement(element);
            //run event code
            for(IEasyEventListener listener : event.getCurrentElement().getCaptureListeners(event.getEvent())){
                event.listenerRun();
                listener.run(event);

            }
            //check if canceled
            if(event.isCanceled()) break; //potentially change to return unless i add important code after this
        }
        if(event.isCanceled()) return;

        RenderableElement currentElement = event.getTarget();
        event.setEventPhase(EventPhase.AT_TARGET);
        for(IEasyEventListener listener : currentElement.getCaptureListeners(event.getEvent())){
            listener.run(event);
            event.listenerRun();
        }
        for(IEasyEventListener listener : currentElement.getBubbleListeners(event.getEvent())){
            listener.run(event);
            event.listenerRun();
        }

        currentElement = currentElement.getParent();

        //run bubble up code

        event.setEventPhase(EventPhase.BUBBLE);
        while(currentElement != null){
            event.setCurrentElement(currentElement);

            //run event code
            for(IEasyEventListener listener : event.getCurrentElement().getBubbleListeners(event.getEvent())){
                listener.run(event);
                event.listenerRun();
            }
            //check if canceled
            if(event.isCanceled()) return;
            currentElement = currentElement.getParent();

        }

    }

    public static void runForAllChildren(EasyEvent event, UIFrame frame){
        RenderableElement root =  frame.getRoot();
        event.setEventPhase(EventPhase.AT_TARGET);
        for(RenderableElement element : EventHelper.getAllElements(root)){
            event.setCurrentElement(element);
            for(IEasyEventListener listener : event.getCurrentElement().getCaptureListeners(event.getEvent())){
                listener.run(event);
                event.listenerRun();
            }
            for(IEasyEventListener listener : event.getCurrentElement().getBubbleListeners(event.getEvent())){
                listener.run(event);
                event.listenerRun();
            }
        }
    }
}
