package net.lucent.easygui.gui.layout.positioning.rules;

import net.lucent.easygui.gui.RenderableElement;

public interface IPositioningRule {
    /**
     *
     * @param rawX x value transformed using PositioningRule.START
     * @param renderableElement the element to apply to
     * @return the x value after being transformed by PositioningRule
     */
    default int getX(int rawX,RenderableElement renderableElement){
        return rawX;
    }

    /**
     *
     * @param x an int that has already been transformed according to the Positioning rule
     * @param renderableElement the element the rule is being applied to
     * @return the raw x value needed for the transform
     */
    default int getRawX(int x, RenderableElement renderableElement){
        return x;
    }

    /**
     *
     * @param rawY x value transformed using PositioningRule.START
     * @param renderableElement the element to apply to
     * @return the y value after being transformed by PositioningRule
     */
    default int getY(int rawY,RenderableElement renderableElement){
        return rawY;
    }

    /**
     *
     * @param y an int that has already been transformed according to the Positioning rule
     * @param renderableElement the element the rule is being applied to
     * @return the raw y value needed for the transform
     */
    default int getRawY(int y, RenderableElement renderableElement){
        return y;
    }

}
