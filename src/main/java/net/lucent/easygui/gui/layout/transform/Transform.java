package net.lucent.easygui.gui.layout.transform;

import net.lucent.easygui.gui.RenderableElement;
import net.lucent.easygui.gui.events.EasyEvents;
import net.lucent.easygui.gui.events.EventHandler;
import net.lucent.easygui.gui.events.type.EasyEvent;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

//Handles the scaling of elements and the rotation of elements
public class Transform {

    private float scale = 1f;
    private boolean useScale = false;

    private double xRotation;
    private double yRotation;
    private double zRotation;

    private Matrix4f transformMatrix = new Matrix4f();

    private final RenderableElement element;

    public Transform(RenderableElement element){
        this.element = element;
    }
    public void initiateEvent(){
        EasyEvent targetedEvent = new EasyEvent(element, EasyEvents.CHILD_TRANSFORM_CHANGED_EVENT);
        EasyEvent global = new EasyEvent(element, EasyEvents.ELEMENT_TRANSFORM_CHANGED_EVENT);
        EventHandler.runEvent(targetedEvent);
        EventHandler.runForAllChildren(global,element.getUiFrame());
    }
    //========================== TRANSFORM ====================================
    //TODO let user manually chose rotation pivot
    public void updateTransformMatrix(){
        //getPosition matrix
        Matrix4f transformMatrix = new Matrix4f();

        //apply rotation
        Quaternionf rotation = new Quaternionf()
                .rotateZ((float) Math.toRadians(getXRotation()))
                .rotateY((float) Math.toRadians(getYRotation()))
                .rotateX((float) Math.toRadians(getZRotation()));
        transformMatrix.rotateAround(rotation, (float) element.getWidth() /2, (float) element.getHeight() /2,1);

        //apply scale
        if(useScale) transformMatrix.scale(scale);


        this.transformMatrix = transformMatrix;
        initiateEvent();
    }

    //========================== GETTERS AND SETTERS ==========================

    public float getScale(){return scale;}
    public void setScale(float scale){
        this.scale = scale;
        updateTransformMatrix();
    }

    public boolean IsUsingScale(){return useScale;}
    public void setUseScale(boolean useScale){
        this.useScale = useScale;
        updateTransformMatrix();
    }

    public double getXRotation(){return xRotation;}
    public double getYRotation(){return yRotation;}
    public double getZRotation(){return zRotation;}

    public void setXRotation(double rotation){
        this.xRotation = rotation;
        updateTransformMatrix();
    }
    public void setYRotation(double rotation){
        this.yRotation = rotation;
        updateTransformMatrix();
    }
    public void setZRotation(double rotation){
        this.zRotation = rotation;
        updateTransformMatrix();
    }

    public Matrix4f getTransformMatrix(){return transformMatrix;}
}
