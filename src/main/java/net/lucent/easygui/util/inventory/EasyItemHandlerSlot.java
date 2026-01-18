package net.lucent.easygui.util.inventory;

import net.lucent.easygui.interfaces.ContainerRenderable;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.lucent.easygui.interfaces.events.Hoverable;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class EasyItemHandlerSlot extends SlotItemHandler implements IEasySlot {

    public String renderSlotId;
    public EasyItemHandlerSlot(ItemStackHandler container, int slot, int x, int y, String renderSlotId) {
        super(container, slot, x, y);
        this.renderSlotId = renderSlotId;
    }


    @Override
    public String getRenderSlotId() {
        return renderSlotId;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public boolean isMouseOver(double mouseX, double mouseY, IEasyGuiScreen screen) {
        if(screen.getElementByID(getRenderSlotId()) instanceof Hoverable hoverable){
            hoverable.isMouseOver(mouseX,mouseY);
        }
        return false;
    }
}
