package net.lucent.easygui.gui.layout.positioning;

import net.lucent.easygui.gui.RenderableElement;
import net.lucent.easygui.gui.layout.positioning.context.IPositioningContext;
import net.lucent.easygui.gui.layout.positioning.rules.IPositioningRule;
import org.joml.Matrix4f;

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

    public Positioning(IPositioningContext context, IPositioningRule xPositioning,IPositioningRule yPositioning){
        this.context = context;
        this.xPositioning = xPositioning;
        this.yPositioning = yPositioning;
    }

    public void setElement(RenderableElement element) {
        this.element = element;
    }
    //========================= Set Positioning Context ==================

    //TODO

    //========================= Set Positioning Rule ======================

    //TODO

    //========================= Position Matrix ===========================
    public void updatePositionMatrix(){
        //make sure to update rawCoordinates
        updateRawCoordinates();

        //get the context matrix
        Matrix4f contextMatrix = context.getPositioningContextMatrix(element);
        positionMatrix = contextMatrix.translate(rawX,rawY,0);
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
}
