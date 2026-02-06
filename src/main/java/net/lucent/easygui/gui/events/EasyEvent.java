package net.lucent.easygui.gui.events;

import net.lucent.easygui.gui.RenderableElement;

public class EasyEvent {

    private final RenderableElement target;

    private RenderableElement currentElement;

    private final String event;

    private EventPhase eventPhase;

    private boolean isCanceled = false;

    //mouse position and scroll behaviour
    private final double mouseX,mouseY,deltaY,deltaX;
    public int button;

    private final double dragY,dragX;
    private final int keyCode, scanCode, modifiers;


    //for mouse move event
    public EasyEvent(RenderableElement target, String event, int mouseX, int mouseY) {
        this.target = target;
        this.event = event;
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.deltaX = 0;
        this.deltaY = 0;
        this.keyCode = 0;
        this.scanCode = 0;
        this.modifiers = 0;
        this.dragY = 0;
        this.dragX = 0;
        this.eventPhase = EventPhase.CAPTURE;
    }

    public void setEventPhase(EventPhase phase){this.eventPhase = phase;}

    public void setCurrentElement(RenderableElement element){
        this.currentElement = element;
    }
    public void setCanceled(boolean canceled){isCanceled = canceled;}

    public RenderableElement getCurrentElement(){return this.currentElement;}
    public RenderableElement getTarget(){return this.target;}

    public String getEvent(){return this.event;}
    public EventPhase getEventPhase(){return this.eventPhase;}
    public boolean isCanceled(){return isCanceled;}

    public double getMouseX(){return this.mouseX;}
    public double getMouseY(){return this.mouseY;}
    public double getDeltaX(){return this.deltaX;}
    public double getDeltaY(){return this.deltaY;}
    public double getDragX(){return this.dragX;}
    public double getDragY(){return this.dragY;}
    public double getKeyCode(){return this.keyCode;}
    public double getScanCode(){return this.scanCode;}
    public double getModifiers(){return this.modifiers;}
}
