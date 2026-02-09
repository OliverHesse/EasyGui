package net.lucent.easygui.gui.layout.positioning;

import net.lucent.easygui.gui.RenderableElement;
import net.lucent.easygui.gui.events.EasyEvents;
import net.lucent.easygui.gui.events.EventHandler;
import net.lucent.easygui.gui.events.type.EasyEvent;
import net.lucent.easygui.gui.layout.positioning.context.IPositioningContext;
import net.lucent.easygui.gui.layout.positioning.rules.IPositioningRule;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles the positioning of the element
 * uses a context to determine relative to what is it positioned
 * then uses positioning rules to position the element
 *
 * for efficiency, it will only update rawCoordinates on a layout change
 * because on a layout change the x will not change but raw x might
 */
public class Positioning {
    public IPositioningContext context;
    public IPositioningRule xPositioning;
    public IPositioningRule yPositioning;

    private int x;
    private int y;

    private int rawX;
    private int rawY;

    private Matrix4f positionMatrix = new Matrix4f();

    private RenderableElement element;

    public Positioning(IPositioningContext context, IPositioningRule xPositioning,IPositioningRule yPositioning,RenderableElement element){
        this.context = context;
        this.xPositioning = xPositioning;
        this.yPositioning = yPositioning;
        setElement(element);
        updatePositionMatrix();
    }
    public Positioning(IPositioningContext context,IPositioningRule positioningRule,RenderableElement element){
        this(context,positioningRule,positioningRule,element);
    }

    public void setElement(RenderableElement element) {
        this.element = element;
    }

    public void initiateEvent(){
        EasyEvent targetedEvent = new EasyEvent(element, EasyEvents.CHILD_POSITION_TRANSFORM_CHANGED_EVENT);
        EasyEvent global = new EasyEvent(element, EasyEvents.ELEMENT_POSITION_TRANSFORM_CHANGED_EVENT);
        EventHandler.runEvent(targetedEvent);
        EventHandler.runForAllChildren(global,element.getUiFrame());
    }
    //========================= Set Positioning Context ==================

    public void setPositioningContext(IPositioningContext context){
        this.context = context;
        updatePositionMatrix();
    }


    //========================= Set Positioning Rule ======================

    public void setXPositioningRule(IPositioningRule rule){
        setXPositioning(rule,false);
    }
    public void setXPositioning(IPositioningRule rule,boolean keepRawX){
        this.xPositioning = rule;
        if(keepRawX) setX(rule.getX(getRawX(),element));
        else setX(0);


    }
    public void setYPositioningRule(IPositioningRule rule){
        setYPositioning(rule,false);
    }
    public void setYPositioning(IPositioningRule rule, boolean keepRawY){
        this.xPositioning = rule;
        if(keepRawY) setY(rule.getY(getRawY(),element));
        else setY(0);
    }
    public void setPositioningRule(IPositioningRule rule){
        this.xPositioning  =rule;
        this.yPositioning= rule;
        setX(rule.getX(getRawX(),element));
        setY(rule.getY(getRawY(),element));
        updateRawCoordinates();
    }

    //========================= Position Matrix ===========================
    public void updatePositionMatrix(){
        //make sure to update rawCoordinates
        updateRawCoordinates();

        //get the context matrix
        Matrix4f contextMatrix = context.getPositioningContextMatrix(element);
        System.out.println("trying to position element at " + rawX+","+rawY);
        positionMatrix = contextMatrix.translate(rawX,rawY,0);
        initiateEvent();
    }


    //========================= Rule positioned cords =====================
    //assumes x and y inputs are of the same positioning rule
    public void setX(int x){
        this.x = x;
        updatePositionMatrix();
    }
    public void setY(int y){
        this.y = y;
        updatePositionMatrix();
    }

    //assumes rawX and rawY are formated using START

    public void setFromRawX(int rawX){
        x = xPositioning.getX(rawX,element);
        updatePositionMatrix();
    }
    public void setFromRawY(int rawY){
        y = yPositioning.getY(rawY,element);
        updatePositionMatrix();
    }
    //============================ RAW CORDS =======================

    public void updateRawX(){
        rawX = xPositioning.getRawX(x,element);
    }
    public void updateRawY(){
        rawY = yPositioning.getRawY(y,element);
    }

    public void updateRawCoordinates(){
        updateRawX();
        updateRawY();
    }

    //========================== GETTERS =============================
    public int getX(){return this.x;}
    public int getY(){return this.y;}
    public int getRawX(){return this.rawX;}
    public int getRawY(){return this.rawY;}
    public Matrix4f getPositionMatrix(){return this.positionMatrix;}
    public Matrix4f getCompletePositionMatrix(){
        if(element.getParent() == null) return positionMatrix;
        RenderableElement currentElement = element.getParent();
        List<RenderableElement> elements = new ArrayList<>();
        while(currentElement != null){
            elements.addFirst(currentElement);
            currentElement = currentElement.getParent();
        }
        Matrix4f completeMatrix = new Matrix4f();
        for(RenderableElement positionedElement : elements){
            completeMatrix = completeMatrix.mul(positionedElement.getPositioningMatrix().mul(positionedElement.getTransformMatrix()));
        }
        return completeMatrix.mul(positionMatrix);
    }
}
