package net.lucent.easygui.elements.inventory;

import net.lucent.easygui.screens.EasyGuiScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.player.RemotePlayer;
import net.minecraft.world.entity.LivingEntity;

public class DisplayPlayer extends DisplayEntity{
    public DisplayPlayer(EasyGuiScreen easyGuiScreen, LocalPlayer entity, int x, int y, double entityScale) {
        super(easyGuiScreen, entity, x, y, entityScale);
    }
}
