package net.lucent.easygui.elements.inventory;

import net.lucent.easygui.elements.BaseRenderable;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.lucent.easygui.menus.EasyGuiContainerMenu;
import net.lucent.easygui.screens.EasyGuiContainerScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;

/*
only works with AbstractContainerScreens
currently just a wrapper around default behaviour so still uses default slots
will change eventually
 */

public class DisplaySlot  extends BaseRenderable {

    public int slotIndex;

    public DisplaySlot(){}
    public DisplaySlot(IEasyGuiScreen screen, int x, int y, int width, int height, int slotIndex,Container container){
        super(screen);
        setX(x);
        setY(y);
        setHeight(height);
        setWidth(width);
        this.slotIndex = slotIndex;

        EasyGuiContainerScreen<? extends AbstractContainerMenu> screen2 = ((EasyGuiContainerScreen<? extends AbstractContainerMenu>) getScreen());
        ((EasyGuiContainerMenu)screen2.getMenu()).addSlot(new Slot(container,slotIndex,x,y));
    }



    @Override
    public void renderSelf(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        EasyGuiContainerScreen<? extends AbstractContainerMenu> screen = ((EasyGuiContainerScreen<? extends AbstractContainerMenu>) getScreen());
        Slot slot = screen.getMenu().getSlot(slotIndex);
        slot.x = getGlobalPoint().x;
        slot.y = getGlobalPoint().y;
        screen.renderSlot(guiGraphics,slot);
    }
}
