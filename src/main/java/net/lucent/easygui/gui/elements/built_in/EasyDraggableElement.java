package net.lucent.easygui.gui.elements.built_in;

import com.mojang.blaze3d.platform.InputConstants;
import net.lucent.easygui.gui.RenderableElement;
import net.lucent.easygui.gui.UIFrame;
import net.lucent.easygui.gui.events.EasyEvents;
import net.lucent.easygui.gui.events.type.EasyEvent;
import net.lucent.easygui.gui.events.type.EasyMouseEvent;
import net.minecraft.world.phys.Vec2;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class EasyDraggableElement extends RenderableElement {
    private boolean pressed = false;
    private boolean hovered = false;
    private int clickedMouseX;
    private int clickedMouseY;
    public EasyDraggableElement(UIFrame frame, int x, int y) {
        super(frame, x, y);
        addEventListener(EasyEvents.MOUSE_DOWN_EVENT,this::onMouseDown);
        addEventListener(EasyEvents.MOUSE_UP_EVENT,this::onMouseUp);
        addEventListener(EasyEvents.MOUSE_MOVE_EVENT,this::mouseMoveEvent);
        addEventListener(EasyEvents.MOUSE_DRAG_EVENT,this::mouseDragEvent);
        addEventListener(EasyEvents.GLOBAL_MOUSE_DOWN_EVENT,(easyEvent -> {
            if(easyEvent.getTarget() != this) setZIndex(0);
        }));
        addEventListener(EasyEvents.GLOBAL_MOUSE_MOVE_EVENT,(easyEvent -> {
            if(easyEvent.getTarget() != this) setZIndex(0);
        }));

    }

    public void mouseDragEvent(EasyEvent event){
        if(!(event instanceof EasyMouseEvent easyMouseEvent)) return; //make sure the right event was called (should always be this but double check)
        if(!isHovered()) return;
        if(isHovered() && isPressed()){

            Vec2 point = globalToLocalPoint((float) (easyMouseEvent.getMouseX()+ easyMouseEvent.getDragX()),(float)(easyMouseEvent.getMouseY()+easyMouseEvent.getDragY()));

            getPositioning().setX((int) ((point.x-clickedMouseX)+getPositioning().getX()));
            getPositioning().setY((int) ((point.y-clickedMouseY)+getPositioning().getY()));
        }
    }



    public void mouseMoveEvent(EasyEvent event){
        if(!(event instanceof EasyMouseEvent easyMouseEvent)) return; //make sure the right event was called (should always be this but double check)
        setHovered(easyMouseEvent.getTarget() == this); //the target of these events is the highest priority ordered element
        if(isPressed() && !isHovered()){
            setZIndex(10);
            setPressed(false); //if mouse moves away and was being pressed unPress
        }


    }

    public void onMouseDown(EasyEvent event){
        if(!(event instanceof EasyMouseEvent easyMouseEvent)) return;//make sure the right event was called (should always be this but double check)
        if(easyMouseEvent.getTarget() != this) return; //make sure this element was pressed
        if(easyMouseEvent.button != InputConstants.MOUSE_BUTTON_LEFT) return; // check the correct button was used
        setPressed(true); //start pressing
        Vec2 point = globalToLocalPoint((float) easyMouseEvent.getMouseX(), (float) easyMouseEvent.getMouseY());
        clickedMouseX = (int) point.x;
        clickedMouseY = (int) point.y;
        if(isHovered()) setZIndex(100);
    }
    public void onMouseUp(EasyEvent event){
        if(!(event instanceof EasyMouseEvent easyMouseEvent)) return;//make sure the right event was called (should always be this but double check)
        if(easyMouseEvent.getTarget() != this) return;//make sure mouse was released over this element
        if(easyMouseEvent.button != InputConstants.MOUSE_BUTTON_LEFT) return;// check the correct button was used
        if(isPressed()) setZIndex(10);
        setPressed(false); //reset

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
