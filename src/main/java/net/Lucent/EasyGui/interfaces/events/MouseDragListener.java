package net.Lucent.EasyGui.interfaces.events;

import net.Lucent.EasyGui.interfaces.ContainerRenderable;

public interface MouseDragListener extends ContainerRenderable {

    //does not need to actually drag just reacts to it happening
    void onDrag(double mouseX, double mouseY, int button, double dragX, double dragY);

}
