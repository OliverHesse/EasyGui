package net.lucent.easygui.interfaces.events;

import net.lucent.easygui.interfaces.ContainerRenderable;

public interface KeyPressedListener extends ContainerRenderable {
    void onKeyPressed(int keyCode, int scanCode,int modifier);
}
