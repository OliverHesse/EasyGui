package net.lucent.easygui.gui.events.type;

import net.lucent.easygui.gui.RenderableElement;
import net.lucent.easygui.gui.events.EasyEvents;
import net.lucent.easygui.gui.events.EventPhase;

public class EasyEvent {


    private final RenderableElement target;

    private RenderableElement currentElement;

    private final String event;

    private EventPhase eventPhase;

    private boolean isCanceled = false;

    private boolean ranListener = false;


    public EasyEvent(RenderableElement target, String event){
        this.target = target;
        this.event = event;
    }

    public void setEventPhase(EventPhase phase){this.eventPhase = phase;}

    public void setCurrentElement(RenderableElement element){
        this.currentElement = element;
    }
    public void setCanceled(boolean canceled){isCanceled = canceled;}
    public void listenerRun(){ranListener = true;}
    public RenderableElement getCurrentElement(){return this.currentElement;}
    public RenderableElement getTarget(){return this.target;}

    public String getEvent(){return this.event;}
    public EventPhase getEventPhase(){return this.eventPhase;}
    public boolean isCanceled(){return isCanceled;}
    public boolean didRunListener(){return ranListener;}

}
