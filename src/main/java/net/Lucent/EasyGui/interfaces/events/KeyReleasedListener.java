package net.Lucent.EasyGui.interfaces.events;

import net.Lucent.EasyGui.interfaces.ContainerRenderable;

//TODO
public interface KeyReleasedListener extends ContainerRenderable {
    void onKeyReleased(int keyCode, int scanCode,int modifier);
}
