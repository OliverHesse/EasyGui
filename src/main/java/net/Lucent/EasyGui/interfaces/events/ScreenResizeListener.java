package net.Lucent.EasyGui.interfaces.events;

import net.Lucent.EasyGui.interfaces.ContainerRenderable;

//doesn't need variables since they can all be gotten through getInstance
public interface ScreenResizeListener extends ContainerRenderable {
    void onResize(int oldWidth, int oldHeight);
}
