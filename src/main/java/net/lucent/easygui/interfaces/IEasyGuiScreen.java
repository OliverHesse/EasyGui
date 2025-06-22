package net.lucent.easygui.interfaces;

import net.lucent.easygui.elements.containers.View;

import java.util.List;

public interface IEasyGuiScreen {

    void register(ContainerRenderable renderable);
    void unregister(ContainerRenderable renderable);

    default void setActiveView(View view){}


    void removeView(View view);

    void childIdSet(ContainerRenderable obj,String id);
    void childClassAdded(ContainerRenderable obj,String className);
    void childClassRemoved(ContainerRenderable obj,String className);

    ContainerRenderable getElementByID(String id);
    List<ContainerRenderable> getElementsByClassName(String className);
}
