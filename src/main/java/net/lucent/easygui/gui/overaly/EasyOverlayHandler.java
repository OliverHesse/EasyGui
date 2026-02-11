package net.lucent.easygui.gui.overaly;

import net.lucent.easygui.EasyGui;
import net.lucent.easygui.gui.UIFrame;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;

import java.util.HashMap;
import java.util.Map;

@EventBusSubscriber(modid = EasyGui.MOD_ID)
public class EasyOverlayHandler {
    public record EasyOverlay(EasyOverlayType type, EasyOverlayPosition position, UIFrame frame){};

    private static final HashMap<ResourceLocation,EasyOverlay> customOverlays = new HashMap<>();

    private static final HashMap<ResourceLocation,EasyOverlayLayer> vanillaOverrides = new HashMap<>();

    public static void registerOverlay(ResourceLocation key,EasyOverlayPosition position,UIFrame frame){
        customOverlays.put(key,new EasyOverlay(EasyOverlayType.GAME,position,frame));
    }
    public static void overrideVanillaOverlay(ResourceLocation vanillaKey,UIFrame frame){
        vanillaOverrides.put(vanillaKey,new EasyOverlayLayer(frame,EasyOverlayType.GAME));
    }

    @SubscribeEvent
    public static void onRegisterOverlays(RegisterGuiLayersEvent event) {
        for(Map.Entry<ResourceLocation, EasyOverlay> overlay : customOverlays.entrySet()){
            overlay.getValue().position.register(event,overlay.getKey(),overlay.getValue());
        }

    }
    @SubscribeEvent
    public static void onRenderGuiLayer(RenderGuiLayerEvent.Pre event){
        if(vanillaOverrides.containsKey(event.getName())){
            event.setCanceled(true);
            vanillaOverrides.get(event.getName()).render(event.getGuiGraphics(),event.getPartialTick());
        }
    }

}
