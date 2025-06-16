package net.lucent.easygui.interfaces.events;

import net.lucent.easygui.interfaces.ContainerRenderable;

//TODO
public interface CharTypedListener extends ContainerRenderable {

    void onCharTyped(char codePoint, int modifiers);
}
