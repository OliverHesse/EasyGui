package net.lucent.easygui.interfaces.events;

import net.lucent.easygui.interfaces.ContainerRenderable;

public interface MouseMovedListener extends ContainerRenderable {

    void onMouseMoved(double mouseX, double mouseY);
}
