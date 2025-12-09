package net.lucent.easygui.overlays;

import net.lucent.easygui.EasyGui;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@EventBusSubscriber(modid = EasyGui.MOD_ID,value = Dist.CLIENT)
public class EasyGuiOverlayManager {


    public static final Map<ResourceLocation,EasyGuiOverlayHandler> customOverlayLayers = new HashMap<>();

    public static final Map<ResourceLocation,EasyGuiOverlay> vanillaOverlayOverrides = new HashMap<>();

    public static void registerVanillaOverlayOverride(ResourceLocation name,EasyGuiOverlay overlay){
        vanillaOverlayOverrides.put(name,overlay);
    }

    public static void addLayer(ResourceLocation name,EasyGuiOverlay overlay){
        if(!customOverlayLayers.containsKey(name)){
            customOverlayLayers.put(name, new EasyGuiOverlayHandler());
        }
        customOverlayLayers.get(name).overlays.add(overlay);
    }


    public static void onRegisterOverlays(RegisterGuiLayersEvent event) {

        for(Map.Entry<ResourceLocation,EasyGuiOverlayHandler> entry :customOverlayLayers.entrySet()){
            event.registerAboveAll(entry.getKey(),entry.getValue());
        }

    }

    @SubscribeEvent
    public static void onRenderGuiLayer(RenderGuiLayerEvent.Pre event){
        if(vanillaOverlayOverrides.containsKey(event.getName())){
            event.setCanceled(true);
            Minecraft mc = Minecraft.getInstance();

            vanillaOverlayOverrides.get(event.getName()).render(event.getGuiGraphics(),(int) mc.mouseHandler.xpos(),(int) mc.mouseHandler.ypos(),
                    event.getPartialTick().getGameTimeDeltaPartialTick(true));
        }
    }



}
