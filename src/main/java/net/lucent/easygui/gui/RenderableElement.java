package net.lucent.easygui.gui;

import net.lucent.easygui.gui.layout.positioning.Positioning;
import net.lucent.easygui.gui.layout.transform.Transform;
import org.joml.Matrix4f;

public class RenderableElement {

    public Positioning positioning;
    public Transform transform;
    public RenderableElement parent;
    public UIFrame uiFrame;

    private int width;
    private int height;



    //================ SHAPE =================
    public int getWidth(){
        return width;
    }
    public void setWidth(int width){
        this.width = width;
    }
    public int getHeight(){
        return height;
    }
    public void setHeight(int height){
        this.height = height;
    }

    //================= TRANSFORMATION MATRICES ===================
    //returns total transform including scale and rotation
    public Matrix4f getTransform(){
        return transform.getTransformMatrix();
    }
    //returns only positioning transform
    public Matrix4f getPositioningTransform(){
        return positioning.getPositionMatrix();
    }

    /**
     *
     * @param positioning new Positioning
     * @param transferCoordinates transfer old coordinates into new one
     */
    public void setPositioning(Positioning positioning,boolean transferCoordinates){
        positioning.setElement(this);
        if(transferCoordinates){
            positioning.setFromRawX(this.positioning.getRawX());
            positioning.setFromRawY(this.positioning.getRawY());
        }
        this.positioning = positioning;

    }

    //================= OTHER =========================
    public RenderableElement getParent() {
        return parent;
    }
    public UIFrame getUiFrame(){
        return uiFrame;
    }


}
