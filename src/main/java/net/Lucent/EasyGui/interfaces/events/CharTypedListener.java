package net.Lucent.EasyGui.interfaces.events;

import net.Lucent.EasyGui.interfaces.ContainerRenderable;

//TODO
public interface CharTypedListener extends ContainerRenderable {

    void onCharTyped(char codePoint, int modifiers);
}
