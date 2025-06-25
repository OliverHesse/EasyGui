package net.lucent.easygui.elements.other;


import net.lucent.easygui.elements.BaseRenderable;
import net.lucent.easygui.holders.EasyGuiEventHolder;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.lucent.easygui.interfaces.events.Hoverable;
import net.lucent.easygui.util.math.BoundChecker;
import net.minecraft.client.Minecraft;
import org.joml.Matrix4f;
import org.joml.Vector4f;

public abstract class SquareRenderable extends BaseRenderable implements Hoverable {
    public SquareRenderable(IEasyGuiScreen easyGuiScreen) {
        super(easyGuiScreen);
    }
    public SquareRenderable(){}

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        System.out.println("mouseX: "+mouseX);
        /* used for debugging
        System.out.println("x: " + getGlobalScaledX() + " y: "+ getGlobalScaledY());
        System.out.println("sw: "+ getScaledWidth() + " sh: "+getScaledHeight());
        System.out.println("vx "+ getGlobalScaledVisibleX()+ " vy: "+getGlobalScaledVisibleY());
        System.out.println("vw: "+ getScaledVisibleWidth() + " vh: "+getScaledVisibleHeight());


         */
        Matrix4f transformation = getTotalTransformation();
        Vector4f p1 = (new Vector4f(0,0,0,1)).mul(transformation);
        Vector4f p2 = (new Vector4f(getWidth(),0,0,1)).mul(transformation);
        Vector4f p3 = (new Vector4f(getWidth(),getHeight(),0,1)).mul(transformation);
        Vector4f p4 = (new Vector4f(0,getHeight(),0,1)).mul(transformation);

        return BoundChecker.containsPoint(new BoundChecker.Vec2(p1),new BoundChecker.Vec2(p2),new BoundChecker.Vec2(p3),new BoundChecker.Vec2(p4),new BoundChecker.Vec2((int)mouseX,(int)mouseY));

        //return mouseX > getGlobalScaledVisibleX() && mouseX < getGlobalScaledVisibleX() + getScaledVisibleWidth() &&
        //        mouseY > getGlobalScaledVisibleY() && mouseY < getGlobalScaledVisibleY()+ getScaledVisibleHeight();
    }


}
