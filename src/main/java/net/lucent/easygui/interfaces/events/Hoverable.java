package net.lucent.easygui.interfaces.events;

import net.lucent.easygui.interfaces.ContainerRenderable;

public interface Hoverable extends ContainerRenderable {

    default boolean isMouseOver(double mouseX,double mouseY) {return false;}
    default void onMouseOver(boolean state){};
}
