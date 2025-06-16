package net.lucent.easygui.interfaces.events;

import net.lucent.easygui.interfaces.ContainerRenderable;

public interface MouseDragListener extends ContainerRenderable {

    //does not need to actually drag just reacts to it happening
    void onDrag(double mouseX, double mouseY, int button, double dragX, double dragY);

}
