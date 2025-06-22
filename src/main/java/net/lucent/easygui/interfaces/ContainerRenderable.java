package net.lucent.easygui.interfaces;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;

import java.util.List;
//TODO annotate for better documentation
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

    default double getScale(){return 0;}
    default double getScaleX(){return getScale();}
    default double getScaleY(){return getScale();}

    default int getScaledWidth(){
        return getWidth();
    }
    default int getScaledHeight(){
        return getHeight();
    }

    IEasyGuiScreen getScreen();

    void setParent(ContainerRenderable parent);
    void addChild(ContainerRenderable child);
    void remove();

    default int screenToLocalX(double x){
        return (int) ((x-getGlobalScaledX())/getTotalCustomScaling());
    }
    default int screenToLocalY(double y){
        return (int) ((y-getGlobalScaledY())/getTotalCustomScaling());
    }
    default void tick(){}

    void setCull(boolean state);
    boolean shouldCull();
}
