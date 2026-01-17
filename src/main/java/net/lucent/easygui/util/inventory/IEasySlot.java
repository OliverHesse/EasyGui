package net.lucent.easygui.util.inventory;

import net.lucent.easygui.interfaces.ContainerRenderable;
import net.minecraft.world.inventory.Slot;
import org.joml.Matrix4f;

public interface IEasySlot {

    default Matrix4f getPosPose(){
        return getContainerRenderable().getPositionTransform();
    }
    default Matrix4f getPose(){
        return getContainerRenderable().getTransform();
    }
    ContainerRenderable getContainerRenderable();


     boolean isMouseOver( double mouseX, double mouseY);
}
