package net.Lucent.EasyGui.overlays;

import net.Lucent.EasyGui.elements.other.BaseRenderable;
import net.Lucent.EasyGui.elements.other.View;
import net.Lucent.EasyGui.holders.EasyGuiEventHolder;
import net.Lucent.EasyGui.interfaces.ContainerRenderable;
import net.Lucent.EasyGui.interfaces.EasyGuiScreen;
import net.minecraft.client.gui.GuiGraphics;

public class EasyGuiOverlay  {

    public View view;
    public EasyGuiEventHolder eventHolder = new EasyGuiEventHolder();

    void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick){
        view.render(guiGraphics,mouseX,mouseY,partialTick);
    }
}
