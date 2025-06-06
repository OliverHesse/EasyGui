package net.Lucent.EasyGui.interfaces.events;

import net.Lucent.EasyGui.interfaces.ContainerRenderable;

public interface KeyPressedListener extends ContainerRenderable {
    void onKeyPressed(int keyCode, int scanCode,int modifier);
}
