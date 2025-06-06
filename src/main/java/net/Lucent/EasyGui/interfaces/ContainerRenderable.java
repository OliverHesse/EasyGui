package net.Lucent.EasyGui.interfaces;

import net.minecraft.client.gui.GuiGraphics;

import java.util.List;

public interface ContainerRenderable {
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


    default double getTotalCustomScaling(){return 1.0;}
    default double getTotalScaleFactorX(){return 1.0;}
    default double getTotalScaleFactorY(){return 1.0;}

    default double getGlobalScaledX(){return 0.0;}
    default double getGlobalScaledY(){return 0.0;}
    ContainerRenderable getParent();
    ContainerRenderable getRoot();
    List<ContainerRenderable> getChildren();

    void setParent(ContainerRenderable parent);
    void addChild(ContainerRenderable child);
    void remove();
}
