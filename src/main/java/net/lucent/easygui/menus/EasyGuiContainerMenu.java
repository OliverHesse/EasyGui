package net.lucent.easygui.menus;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class EasyGuiContainerMenu extends AbstractContainerMenu {
    protected EasyGuiContainerMenu(@Nullable MenuType<?> menuType, int containerId) {
        super(menuType, containerId);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return null;
    }

    @Override
    public Slot addSlot(Slot slot) {
        return super.addSlot(slot);
    }

    @Override
    public DataSlot addDataSlot(DataSlot intValue) {
        return super.addDataSlot(intValue);
    }

    @Override
    public void addDataSlots(ContainerData array) {
        super.addDataSlots(array);
    }

    @Override
    public boolean stillValid(Player player) {
        return false;
    }
}
