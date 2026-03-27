package net.lucent.easygui.gui.layout.positioning.context;

import net.lucent.easygui.gui.RenderableElement;
import net.lucent.easygui.gui.UIFrame;
import org.joml.Matrix4f;

public class PositioningContexts {
    public static final IPositioningContext FIXED = new IPositioningContext(){
        @Override
        public Matrix4f getPositioningContextMatrix(RenderableElement element) {
            UIFrame uiFrame = element.getUiFrame();

            Matrix4f currentPositioningContext = element.getParent() == null ? new Matrix4f(element.getUiFrame().getBaseTransform()) : new Matrix4f(element.getTransformMatrix());
            return new Matrix4f(uiFrame.getBaseTransform()).mul(currentPositioningContext.invert()); // should get transform matrix
        }
    };
    public static final IPositioningContext ABSOLUTE = new IPositioningContext() {};




}
