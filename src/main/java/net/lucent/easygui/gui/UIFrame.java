package net.lucent.easygui.gui;

import org.joml.Matrix4f;

public class UIFrame {

    public Matrix4f baseTransform;
    public int width;
    public int height;

    public Matrix4f getBaseTransform() {
        return baseTransform;
    }
    public int getHeight(){
        return height;
    }
    public int getWidth(){
        return width;
    }
    //TODO
    public int getScaleAppliedWidth(){
        return width;
    }
    //TODO
    public int getScaleAppliedHeight(){
        return height;
    }
}
