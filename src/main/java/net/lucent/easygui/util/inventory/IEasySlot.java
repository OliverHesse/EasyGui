package net.lucent.easygui.util.inventory;

import net.lucent.easygui.interfaces.ContainerRenderable;
import org.joml.Matrix4f;

interface IEasySlot {

    default Matrix4f getPosPose(){
        return getContainerRenderable().getPositionTransform();
    }
    default Matrix4f getPose(){
        return getContainerRenderable().getTransform();
    }
    ContainerRenderable getContainerRenderable();


     boolean isMouseOver( double mouseX, double mouseY);
}
