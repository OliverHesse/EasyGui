package net.lucent.easygui.gui.layout.positioning.rules;

import net.lucent.easygui.gui.RenderableElement;

//think about making individual classes instead of dynamic like this
/*
    Holds some default Positioning Rules
 */
public class PositioningRules {

    //since transformations assume x input is using start rule when converting, old rule -> start -> new rule

    //the default
    public static final IPositioningRule START = new IPositioningRule() {};
    //center assumes positive -> and negative <- from center. aka same direction as start
    public static final IPositioningRule CENTER = new IPositioningRule() {


        @Override
        public int getX(int rawX, RenderableElement renderableElement) {
            if(renderableElement.getParent() == null){
                return rawX-renderableElement.getUiFrame().getWidth()/2;
            }
            return rawX-renderableElement.getWidth()/2;
        }

        @Override
        public int getRawX(int x, RenderableElement renderableElement) {
            if(renderableElement.getParent() == null){
                //use frame
                return renderableElement.getUiFrame().getWidth()/2+x;
            }
            return renderableElement.getParent().getWidth()/2+x;
        }

        @Override
        public int getY(int rawY, RenderableElement renderableElement) {
            if(renderableElement.getParent() == null){
                return rawY-renderableElement.getUiFrame().getHeight()/2;
            }
            return rawY-renderableElement.getHeight()/2;
        }

        @Override
        public int getRawY(int y, RenderableElement renderableElement) {
            if(renderableElement.getParent() == null){
                //use frame
                return renderableElement.getUiFrame().getHeight()/2+y;
            }
            return renderableElement.getParent().getHeight()/2+y;
        }
    };
    //assumes positive is <- and negative is ->. aka opposite direction of start
    public static final IPositioningRule END = new IPositioningRule() {
        @Override
        public int getX(int rawX, RenderableElement renderableElement) {
            if(renderableElement.getParent() == null){
                return renderableElement.getUiFrame().getWidth()-rawX;
            }
            return renderableElement.getParent().getWidth()-rawX;
        }

        @Override
        public int getRawX(int x, RenderableElement renderableElement) {
            if(renderableElement.getParent() == null){
                return renderableElement.getUiFrame().getWidth()-x;
            }
            return renderableElement.getParent().getWidth()-x;
        }

        @Override
        public int getY(int rawY, RenderableElement renderableElement) {
            if(renderableElement.getParent() == null){
                return renderableElement.getUiFrame().getHeight()-rawY;
            }
            return renderableElement.getParent().getHeight()-rawY;
        }

        @Override
        public int getRawY(int y, RenderableElement renderableElement) {
            if(renderableElement.getParent() == null){
                return renderableElement.getUiFrame().getHeight()-y;
            }
            return renderableElement.getParent().getHeight()-y;
        }
    };




}
