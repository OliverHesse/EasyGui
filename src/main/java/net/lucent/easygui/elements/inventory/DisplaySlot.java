package net.lucent.easygui.elements.inventory;

import net.lucent.easygui.elements.BaseRenderable;
import net.lucent.easygui.elements.other.SquareRenderable;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.lucent.easygui.menus.EasyGuiContainerMenu;
import net.lucent.easygui.screens.EasyGuiContainerScreen;
import net.lucent.easygui.util.inventory.EasySlot;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

/*
only works with AbstractContainerScreens
currently just a wrapper around default behaviour so still uses default slots
will change eventually
 */
@OnlyIn(Dist.CLIENT)
public class DisplaySlot  extends SquareRenderable {

    public int slotIndex;
    public boolean renderDefaultSlot = true;
    public DisplaySlot(){}
    public DisplaySlot(IEasyGuiScreen screen, int x, int y, int width, int height,String renderId){
        super(screen);
        setX(x);
        setY(y);
        setHeight(height);
        setWidth(width);
        setID(renderId);
    }
    public DisplaySlot(IEasyGuiScreen screen, int x, int y, int width, int height,String renderId,boolean renderDefaultSlot){
        this(screen,x,y,width,height,renderId);
        this.renderDefaultSlot = renderDefaultSlot;
    }



    @Override
    public void renderSelf(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
            if(!renderDefaultSlot) return;
            guiGraphics.fill(0,0,getWidth(),getHeight(),-7631989);

            guiGraphics.fill(-1,-1,0,getHeight(),-13158601);
            guiGraphics.fill(-1,-1,getWidth(),0,-13158601);

            guiGraphics.fill(getWidth(),0,getWidth()+1,getHeight()+1,-1);
            guiGraphics.fill(0,getHeight(),getWidth()+1,getHeight()+1,-1);

            guiGraphics.fill(-1,getHeight(),0,getHeight()+1,-7631989);
            guiGraphics.fill(getWidth(),-1,getWidth()+1,0,-7631989);
    }
}
