package net.lucent.easygui.elements.containers;

import net.lucent.easygui.elements.BaseRenderable;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.minecraft.client.gui.GuiGraphics;

public class EmptyContainer extends BaseRenderable {
    public EmptyContainer(){
        super();
    }
    public EmptyContainer(IEasyGuiScreen screen,int x,int y,int width,int height){
        super(screen);
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
    }
    @Override
    public void renderSelf(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {

    }
}
