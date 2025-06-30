package net.lucent.easygui.testing.test_screens;

import net.lucent.easygui.screens.EasyGuiContainerScreen;
import net.lucent.easygui.testing.test_menu.InventoryMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class TestInventoryScreen extends EasyGuiContainerScreen<InventoryMenu> {
    public TestInventoryScreen(InventoryMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }
}
