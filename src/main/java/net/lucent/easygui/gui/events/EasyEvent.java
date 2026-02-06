package net.lucent.easygui.gui.events;

import net.lucent.easygui.gui.RenderableElement;

public class EasyEvent {

    private final RenderableElement target;

    private RenderableElement currentElement;

    private final String event;

    private EventPhase eventPhase;

    private boolean isCanceled = false;

    private boolean ranListener = false;

    //mouse position and scroll behaviour
    private final double mouseX,mouseY,deltaY,deltaX;
    public int button;

    private final double dragY,dragX;
    private final int keyCode, scanCode, modifiers;


    //for mouse move event
    public EasyEvent(RenderableElement target, String event, double mouseX, double mouseY) {
        this(target,event,mouseX,mouseY,0,0.0,0.0,0.0,0.0,0,0,0);
    }
    //for mouse click event
    public EasyEvent(RenderableElement target,String event, double mouseX, double mouseY, int button){
        this(target,event,mouseX,mouseY,button,0.0,0.0,0.0,0.0,0,0,0);
    }
    //for scrollEvents
    public EasyEvent(RenderableElement target,String event, double mouseX,double mouseY, double deltaX,double deltaY){
        this(target,event,mouseX,mouseY,0,deltaX,deltaY,0.0,0.0,0,0,0);
    }
    //for drag events
    public EasyEvent(RenderableElement target,String event, double mouseX, double mouseY,int button, double dragX,double dragY){
        this(target,event,mouseX,mouseY,button,0.0,0.0,dragX,dragY,0,0,0);
    }
    //for key events
    public EasyEvent(RenderableElement target,String event,int keyCode,int scanCode,int modifiers){
        this(target,event,0.0,0.0,0,0.0,0.0,0.0,0.0,keyCode,scanCode,modifiers);
    }

    //general
    public EasyEvent(RenderableElement target,String event, double mouseX, double mouseY, int button,double deltaX,double deltaY,double dragX,double dragY,int keyCode,int scanCode,int modifiers){
        this.target = target;
        this.event = event;
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.button = button;
        this.deltaY = deltaY;
        this.deltaX = deltaX;
        this.dragX = dragX;
        this.dragY =  dragY;
        this.keyCode = keyCode;
        this.scanCode = scanCode;
        this.modifiers = modifiers;
        this.eventPhase = EventPhase.CAPTURE;
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
