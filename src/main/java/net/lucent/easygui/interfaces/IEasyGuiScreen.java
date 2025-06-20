package net.lucent.easygui.interfaces;

import net.lucent.easygui.elements.other.View;

public interface IEasyGuiScreen {

    void register(ContainerRenderable renderable);
    void unregister(ContainerRenderable renderable);

    void removeView(View view);
}
