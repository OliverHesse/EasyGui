package net.Lucent.EasyGui.testing;

import net.Lucent.EasyGui.EasyGui;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.screens.Overlay;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.CustomizeGuiOverlayEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

@EventBusSubscriber(modid = EasyGui.MOD_ID)
public class OverlayRenderer{

    @SubscribeEvent
    public static void onRender(RenderGuiLayerEvent.Pre event){
        if(event.getName() == VanillaGuiLayers.PLAYER_HEALTH){
            event.setCanceled(true);
        };
        System.out.println("render gui layer");

    }


    @SubscribeEvent
    public static void onRender(RenderGuiEvent.Post event){
        System.out.println("render gui");
    }




}
