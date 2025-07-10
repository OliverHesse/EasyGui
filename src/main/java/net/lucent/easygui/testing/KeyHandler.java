package net.lucent.easygui.testing;

import net.lucent.easygui.testing.network.ClientOpenMenuPayload;
import net.lucent.easygui.testing.test_menu.InventoryMenu;
import net.lucent.easygui.testing.test_screens.TestScreen;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.SimpleMenuProvider;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.PacketDistributor;

public class KeyHandler {


    public static final KeyMapping OPEN_MENU_KEY = new KeyMapping("key.easy_gui.test_menu_key", 67, "category.easy_gui.testing");
    public static void register() {
        IEventBus eventBus = NeoForge.EVENT_BUS;
        eventBus.addListener(EventPriority.HIGH, KeyHandler::handleKeyInputEvent);
    }


    public static void handleKeyInputEvent(ClientTickEvent.Post event) {
        if(Minecraft.getInstance().level == null && Minecraft.getInstance().getConnection() == null) return;
        if(OPEN_MENU_KEY.consumeClick()){
            System.out.println("button clicked");
            //PacketDistributor.sendToServer(new ClientOpenMenuPayload());
            Minecraft.getInstance().setScreen(new TestScreen(Component.literal("TestScreen")));
        }



    }










}
