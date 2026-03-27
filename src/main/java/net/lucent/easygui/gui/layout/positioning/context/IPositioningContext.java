package net.lucent.easygui.gui.layout.positioning.context;

import net.lucent.easygui.gui.RenderableElement;
import org.joml.Matrix4f;

public interface IPositioningContext {
    /**
     * @return the transformation Matrix to apply to the poseStack
     */
    default Matrix4f getPositioningContextMatrix(RenderableElement element){
        return new Matrix4f();
    };
}
