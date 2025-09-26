package net.lucent.easygui.interfaces;

import net.lucent.easygui.templating.actions.Action;
import net.lucent.easygui.templating.actions.IAction;
import net.lucent.easygui.util.math.BoundChecker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.List;
//TODO annotate for better documentation
@OnlyIn(Dist.CLIENT)
public interface ContainerRenderable extends Renderable {



    String getID();
    List<String> getClassList();
    void setID(String id);
    void addClass(String className);
    void removeClass(String className);
    @Override
    void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick);
    void renderSelf(GuiGraphics guiGraphics,int mouseX, int mouseY,float partialTick);
    void renderChildren(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick);
    void setRenderScale(GuiGraphics guiGraphics);
    void resetRenderScale(GuiGraphics guiGraphics);
    //cannot be rendered nor listen to events
    default boolean isActive(){return false;};
    void setActive(boolean state);
    //cannot be rendered but can listen to events
    default boolean isVisible(){return false;}
    void setVisible(boolean state);
    default boolean isFocused(){return false;}
    void setFocused(boolean state);

    int getX();
    int getY();
    void setX(int x);
    void setY(int y);

    default void setRotation(double x,double y,double z){}

    default double getRotationX(){return 0;}
    default double getRotationY(){return 0;}
    default double getRotationZ(){return 0;}

    default boolean usesMinecraftScaling(){ return  false;}

    default double getTotalCustomScaling(){return 1.0;}
    default double getTotalScaleFactorX(){return 1.0;}
    default double getTotalScaleFactorY(){return 1.0;}

    default double getGlobalScaledX(){return 0.0;}
    default double getGlobalScaledY(){return 0.0;}

    ContainerRenderable getParent();
    ContainerRenderable getRoot();
    List<ContainerRenderable> getChildren();

    default int getWidth(){
        return 0;
    }

    default int getHeight(){
        return 0;
    }

    //TODO
    //relative position

    default int getCullBorderWidth(){return 0;}

    default double getScale(){return 0;}
    default double getScaleX(){return getScale();}
    default double getScaleY(){return getScale();}

    default int getScaledWidth(){
        return (int) (getWidth()*getTotalScaleFactorX());
    }
    default int getScaledHeight(){
        return (int) (getHeight()*getTotalScaleFactorY());
    }



    IEasyGuiScreen getScreen();
    void setScreen(IEasyGuiScreen screen);


    Matrix4f getTransform();
    Matrix4f getPositionTransform();
    void setParent(ContainerRenderable parent);
    void addChild(ContainerRenderable child);
    void removeChild(ContainerRenderable child);
    void removeChildren();

    default BoundChecker.Vec2 screenToLocalPoint(double x, double y){
        Vector3f position = new Matrix4f(getTransform()).invert().transformPosition(new Vector3f((float) x,(float) y,0));
        return new BoundChecker.Vec2((int) position.x, (int) position.y);
    }
    default BoundChecker.Vec2 localToScreenPoint(double x, double y){
        Vector3f position = new Matrix4f(getTransform()).transformPosition(new Vector3f((float) x,(float) y,0));
        return new BoundChecker.Vec2((int) position.x, (int) position.y);
    }


    default void tick(){}

    double getCustomScale();

    default double getTotalCustomScale(){
        if(getParent() == null) return getCustomScale();
        return getParent().getTotalCustomScale()*getCustomScale();
    }

    default double getApplicableMinecraftScale(){
        if(getRoot() != null && getRoot().usesMinecraftScaling()) return Minecraft.getInstance().getWindow().getGuiScale();
        return 1;
    }

    void setCull(boolean state);
    boolean shouldCull();
    void setWidth(int width);
    void setHeight(int height);

    void setTickAction(Action action);
    interface TickAction extends IAction{
        @Override
        default void accept(ContainerRenderable renderable, Object[] eventArgs, Object[] customArgs) {
            run(renderable,customArgs);
        }
        void run(ContainerRenderable renderable, Object[] customArgs);
    }

    BoundChecker.Rec2d getActiveCullRegion();
}
