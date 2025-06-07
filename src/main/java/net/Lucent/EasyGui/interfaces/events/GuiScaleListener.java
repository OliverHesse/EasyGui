package net.Lucent.EasyGui.interfaces.events;

import net.Lucent.EasyGui.interfaces.ContainerRenderable;

public interface GuiScaleListener extends ContainerRenderable {
    void onGuiScaleChanged(double oldScale);
}
