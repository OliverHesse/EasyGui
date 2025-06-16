package net.lucent.easygui.interfaces.events;

import net.lucent.easygui.interfaces.ContainerRenderable;

public interface GuiScaleListener extends ContainerRenderable {
    void onGuiScaleChanged(double oldScale);
}
