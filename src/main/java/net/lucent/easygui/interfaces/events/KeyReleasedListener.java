package net.lucent.easygui.interfaces.events;

import net.lucent.easygui.interfaces.ContainerRenderable;

//TODO
public interface KeyReleasedListener extends ContainerRenderable {
    void onKeyReleased(int keyCode, int scanCode,int modifier);
}
