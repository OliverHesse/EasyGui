package net.Lucent.EasyGui.interfaces.events;

import net.Lucent.EasyGui.interfaces.ContainerRenderable;

public interface Hoverable extends ContainerRenderable {

    default boolean isMouseOver(double mouseX,double mouseY) {return false;}
    default void onMouseOver(boolean state){};
}
