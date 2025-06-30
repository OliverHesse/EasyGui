package net.lucent.easygui.testing.test_menu;

import net.lucent.easygui.menus.EasyGuiContainerMenu;
import net.lucent.easygui.testing.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class InventoryMenu extends EasyGuiContainerMenu {

    public InventoryMenu(int containerId, Inventory inventory, FriendlyByteBuf extraData) {
        this(inventory);
    }

    public InventoryMenu(Inventory playerInventory) {
        super(ModMenuTypes.GUI_TESTING_MENU.get(), 0);
    }
    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
