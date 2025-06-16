package net.lucent.easygui.interfaces.events;

import net.lucent.easygui.interfaces.ContainerRenderable;

public interface MouseScrollListener extends ContainerRenderable {
    void onMouseScroll(double mouseX, double mouseY, double scrollX, double scrollY);

}
