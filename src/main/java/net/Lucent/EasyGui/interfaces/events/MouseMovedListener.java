package net.Lucent.EasyGui.interfaces.events;

import net.Lucent.EasyGui.interfaces.ContainerRenderable;

public interface MouseMovedListener extends ContainerRenderable {

    void onMouseMoved(double mouseX, double mouseY);
}
