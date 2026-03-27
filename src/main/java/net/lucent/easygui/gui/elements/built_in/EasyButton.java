package net.lucent.easygui.gui.elements.built_in;

import com.mojang.blaze3d.platform.InputConstants;
import net.lucent.easygui.gui.RenderableElement;
import net.lucent.easygui.gui.UIFrame;
import net.lucent.easygui.gui.events.EasyEvents;
import net.lucent.easygui.gui.events.type.EasyEvent;
import net.lucent.easygui.gui.events.type.EasyMouseEvent;

public class EasyButton extends RenderableElement {

    private boolean pressed = false;
    private boolean hovered = false;
    public EasyButton(UIFrame frame,int x,int y) {
        super(frame,x,y);
        addEventListener(EasyEvents.MOUSE_DOWN_EVENT,this::onMouseDown);
        addEventListener(EasyEvents.MOUSE_UP_EVENT,this::onMouseUp);
        addEventListener(EasyEvents.MOUSE_MOVE_EVENT,this::mouseMoveEvent);
    }

    public void mouseMoveEvent(EasyEvent event){
        if(!(event instanceof EasyMouseEvent easyMouseEvent)) return; //make sure the right event was called (should always be this but double check)
        setHovered(easyMouseEvent.getTarget() == this); //the target of these events is the highest priority ordered element
        if(isPressed() && !isHovered()) setPressed(false); //if mouse moves away and was being pressed unPress
    }

    public void onMouseDown(EasyEvent event){
        if(!(event instanceof EasyMouseEvent easyMouseEvent)) return;//make sure the right event was called (should always be this but double check)
        if(easyMouseEvent.getTarget() != this) return; //make sure this element was pressed
        if(easyMouseEvent.button != InputConstants.MOUSE_BUTTON_LEFT) return; // check the correct button was used
        setPressed(true); //start pressing
    }
    public void onMouseUp(EasyEvent event){
        if(!(event instanceof EasyMouseEvent easyMouseEvent)) return;//make sure the right event was called (should always be this but double check)
        if(easyMouseEvent.getTarget() != this) return;//make sure mouse was released over this element
        if(easyMouseEvent.button != InputConstants.MOUSE_BUTTON_LEFT) return;// check the correct button was used
        if(isPressed()){//make sure mouse was pressed down over this element first
            //RUN CLICK LOGIC HERE
            onClick();
        }
        setPressed(false); //reset
    }
    public void onClick(){

    }
    //==================== GETTERS AND SETTERS====================
    public boolean isPressed(){
        return pressed;
    }
    public void setPressed(boolean state){
        this.pressed = state;
    }
    public boolean isHovered(){return hovered;}
    public void setHovered(boolean state){
        this.hovered = state;
    }
}
