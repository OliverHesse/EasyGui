package net.lucent.easygui.elements.other;


import net.lucent.easygui.elements.BaseRenderable;
import net.lucent.easygui.elements.controls.buttons.ColorButton;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.lucent.easygui.interfaces.events.Hoverable;
import net.lucent.easygui.templating.actions.Action;
import net.lucent.easygui.util.math.BoundChecker;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public abstract class SquareRenderable extends BaseRenderable implements Hoverable {

    public Action hoverAction;

    public SquareRenderable(IEasyGuiScreen easyGuiScreen) {
        super(easyGuiScreen);
    }
    public SquareRenderable(){}

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {

        /* used for debugging
        System.out.println("x: " + getGlobalScaledX() + " y: "+ getGlobalScaledY());
        System.out.println("sw: "+ getScaledWidth() + " sh: "+getScaledHeight());
        System.out.println("vx "+ getGlobalScaledVisibleX()+ " vy: "+getGlobalScaledVisibleY());
        System.out.println("vw: "+ getScaledVisibleWidth() + " vh: "+getScaledVisibleHeight());


         */


        //.mul((float) (getApplicableMinecraftScale()/getTotalCustomScale()))

        if(getTransform() == null) return false;
        Vector3f p1 = getTransform().transformPosition(new Vector3f(0,0,0));;
        Vector3f p2 = getTransform().transformPosition(new Vector3f(getWidth(),0,0));
        Vector3f p3 = getTransform().transformPosition(new Vector3f(getWidth(),getHeight(),0));
        Vector3f p4 = getTransform().transformPosition(new Vector3f(0,getHeight(),0));
        BoundChecker.Vec2 mousePos = new BoundChecker.Vec2((int)mouseX,(int)mouseY);
        if(getParent() != null && getParent().getActiveCullRegion() != null){

            BoundChecker.Rec2d rec = getParent().getActiveCullRegion();
            if(!BoundChecker.containsPoint(getParent().getActiveCullRegion(),mousePos)) return false;
        }
        return BoundChecker.containsPoint(new BoundChecker.Vec2(p1),new BoundChecker.Vec2(p2),new BoundChecker.Vec2(p3),new BoundChecker.Vec2(p4),mousePos);

    }

    @Override
    public void onMouseOver(boolean state) {
        Hoverable.super.onMouseOver(state);
        if(hoverAction != null) hoverAction.accept(this,state);
    }
}
