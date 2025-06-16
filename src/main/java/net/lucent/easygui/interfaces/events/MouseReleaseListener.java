package net.lucent.easygui.interfaces.events;

import net.lucent.easygui.interfaces.ContainerRenderable;

/**
 * just calls when mouse is released regardless of location
 */
public interface MouseReleaseListener extends ContainerRenderable {
    void onMouseReleased(double mouseX, double mouseY, int button);
}
