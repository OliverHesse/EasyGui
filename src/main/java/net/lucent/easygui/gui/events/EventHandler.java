package net.lucent.easygui.gui.events;

import net.lucent.easygui.gui.RenderableElement;
import net.minecraft.client.Minecraft;

import java.util.List;

public class EventHandler {


    //TODO allow for optional skipping of bubble or capture phase?
    public static void runEvent(EasyEvent event){

        List<RenderableElement> capturePath = EventHelper.createEventPath(event.getTarget());
        event.setEventPhase(EventPhase.CAPTURE);
        for(RenderableElement element : capturePath){
            event.setCurrentElement(element);
            //run event code
                //TODO
            //check if canceled
            if(event.isCanceled()) break; //potentially change to return unless i add important code after this
        }
        if(event.isCanceled()) return;

        //run bubble up code

        RenderableElement currentElement = event.getTarget();
        while(currentElement != null){
            event.setCurrentElement(currentElement);

            //run event code
                //TODO

            //check if canceled
            if(event.isCanceled()) return;
            currentElement = currentElement.getParent();

        }

    }
}
