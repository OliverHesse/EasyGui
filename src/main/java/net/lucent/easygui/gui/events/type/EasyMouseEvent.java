package net.lucent.easygui.gui.events.type;

import net.lucent.easygui.gui.RenderableElement;
import net.lucent.easygui.gui.events.EasyEvents;
import net.lucent.easygui.gui.events.EventPhase;

public class EasyMouseEvent extends EasyEvent {


    //mouse position and scroll behaviour
    private final double mouseX,mouseY,deltaY,deltaX;
    public final int button;

    private final double dragY,dragX;

    public static EasyMouseEvent MouseDownEvent(RenderableElement target,double mouseX,double mouseY,int button){
        return MouseDownEvent(target,mouseX,mouseY,button,false);
    }
    public static EasyMouseEvent MouseDownEvent(RenderableElement target,double mouseX,double mouseY,int button,boolean global){
        String event = global ? EasyEvents.GLOBAL_MOUSE_DOWN_EVENT : EasyEvents.MOUSE_DOWN_EVENT;
        return new EasyMouseEvent(target,event,mouseX,mouseY,button,0,0,0,0);
    }

    public static EasyMouseEvent MouseUpEvent(RenderableElement target,double mouseX,double mouseY,int button){
        return MouseUpEvent(target,mouseX,mouseY,button,false);
    }
    public static EasyMouseEvent MouseUpEvent(RenderableElement target,double mouseX,double mouseY,int button,boolean global){
        String event = global ? EasyEvents.GLOBAL_MOUSE_UP_EVENT : EasyEvents.MOUSE_UP_EVENT;
        return new EasyMouseEvent(target,event,mouseX,mouseY,button,0,0,0,0);
    }

    public static EasyMouseEvent MouseMoveEvent(RenderableElement target,double mouseX,double mouseY){
        return MouseMoveEvent(target,mouseX,mouseY,false);
    }
    public static EasyMouseEvent MouseMoveEvent(RenderableElement target,double mouseX,double mouseY,boolean global){
        String event = global ? EasyEvents.GLOBAL_MOUSE_MOVE_EVENT : EasyEvents.MOUSE_MOVE_EVENT;
        return new EasyMouseEvent(target,event,mouseX,mouseY,0,0,0,0,0);
    }

    public static EasyMouseEvent MouseDragEvent(RenderableElement target,double mouseX,double mouseY,int button,double dragX,double dragY){
        return MouseDragEvent(target,mouseX,mouseY,button,dragX,dragY,false);
    }
    public static EasyMouseEvent MouseDragEvent(RenderableElement target,double mouseX,double mouseY,int button,double dragX,double dragY,boolean global){
        String event = global ? EasyEvents.GLOBAL_MOUSE_DRAG_EVENT : EasyEvents.MOUSE_DRAG_EVENT;
        return new EasyMouseEvent(target,event,mouseX,mouseY,button,0,0,dragX,dragY);
    }

    public static EasyMouseEvent MouseScrollEvent(RenderableElement target,double mouseX,double mouseY,double deltaX,double deltaY){
        return MouseScrollEvent(target,mouseX,mouseY,deltaX,deltaY,false);
    }
    public static EasyMouseEvent MouseScrollEvent(RenderableElement target,double mouseX,double mouseY,double deltaX,double deltaY,boolean global){
        String event = global ? EasyEvents.GLOBAL_MOUSE_UP_EVENT : EasyEvents.MOUSE_UP_EVENT;
        return new EasyMouseEvent(target,event,mouseX,mouseY,0,deltaX,deltaY,0,0);
    }
    public EasyMouseEvent(RenderableElement target, String event, double mouseX, double mouseY,  int button,double deltaX, double deltaY, double dragX, double dragY) {
        super(target,event);
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.deltaY = deltaY;
        this.deltaX = deltaX;
        this.button = button;
        this.dragY = dragY;
        this.dragX = dragX;
    }


    public double getMouseX(){return this.mouseX;}
    public double getMouseY(){return this.mouseY;}
    public double getDeltaX(){return this.deltaX;}
    public double getDeltaY(){return this.deltaY;}
    public double getDragX(){return this.dragX;}
    public double getDragY(){return this.dragY;}
}
