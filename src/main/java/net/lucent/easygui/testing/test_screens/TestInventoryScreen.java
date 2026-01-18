package net.lucent.easygui.testing.test_screens;

import net.lucent.easygui.elements.containers.View;
import net.lucent.easygui.screens.EasyGuiContainerScreen;
import net.lucent.easygui.testing.test_menu.InventoryMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TestInventoryScreen extends EasyGuiContainerScreen<InventoryMenu> {
    public TestInventoryScreen(InventoryMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);

        View view = new View(this);
        view.setUseMinecraftScale(true);
        addView(view);

        //view.addChild(new DynamicContainerPanel(this, view.getScaledWidth()/2-100,view.getScaledHeight()/2-100,200,200,36,playerInventory));
        //view.addChild(new ContainerScrollBox(this,view.getScaledWidth()/2-100,view.getScaledHeight()/2-100,36,9,2,playerInventory));
           }
}
