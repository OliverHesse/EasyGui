package net.Lucent.EasyGui.interfaces.events;

import net.Lucent.EasyGui.interfaces.ContainerRenderable;

/**
 * just calls when mouse is released regardless of location
 */
public interface MouseReleaseListener extends ContainerRenderable {
    void onMouseReleased(double mouseX, double mouseY, int button);
}
