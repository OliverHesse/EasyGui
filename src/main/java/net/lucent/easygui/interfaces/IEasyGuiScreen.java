package net.lucent.easygui.interfaces;

import net.lucent.easygui.elements.containers.View;
import net.lucent.easygui.elements.tooltips.EasyTooltip;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.List;
@OnlyIn(Dist.CLIENT)
public interface IEasyGuiScreen {

    void register(ContainerRenderable renderable);
    void unregister(ContainerRenderable renderable);

    default void setActiveView(View view){}

    void setTooltip(EasyTooltip tooltip);

    void addView(View view);

    void removeView(View view);

    void childIdSet(ContainerRenderable obj,String id);
    void childClassAdded(ContainerRenderable obj,String className);
    void childClassRemoved(ContainerRenderable obj,String className);

    ContainerRenderable getElementByID(String id);
    List<ContainerRenderable> getElementsByClassName(String className);
}
