package net.lucent.easygui.util.inventory;

import net.lucent.easygui.interfaces.ContainerRenderable;
import net.lucent.easygui.interfaces.events.Hoverable;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class EasyItemHandlerSlot extends SlotItemHandler implements IEasySlot {

    public ContainerRenderable slot;
    public EasyItemHandlerSlot(ItemStackHandler container, int slot, int x, int y, ContainerRenderable displaySlot) {
        super(container, slot, x, y);
        this.slot = displaySlot;
    }


    @Override
    public ContainerRenderable getContainerRenderable() {
        return slot;
    }

    public boolean isMouseOver(double mouseX, double mouseY){
        if(slot instanceof Hoverable hoverable) return hoverable.isMouseOver(mouseX,mouseY);
        return false;
    }
}
