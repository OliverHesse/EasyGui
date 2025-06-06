package net.Lucent.EasyGui.interfaces.events;

import net.Lucent.EasyGui.interfaces.ContainerRenderable;

public interface MouseScrollListener extends ContainerRenderable {
    void onMouseScroll(double mouseX, double mouseY, double scrollX, double scrollY);

}
