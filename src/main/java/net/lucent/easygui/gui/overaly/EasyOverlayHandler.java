package net.lucent.easygui.gui.overaly;

import net.lucent.easygui.gui.UIFrame;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;

import java.util.HashMap;
import java.util.Map;

public class EasyOverlayHandler {
    public record EasyOverlay(EasyOverlayType type, EasyOverlayPosition position, UIFrame frame){};

    private static final HashMap<ResourceLocation,EasyOverlay> customOverlays = new HashMap<>();

    public static void onRegisterOverlays(RegisterGuiLayersEvent event) {
        for(Map.Entry<ResourceLocation, EasyOverlay> overlay : customOverlays.entrySet()){
            overlay.getValue().position.register(event,overlay.getKey(),overlay.getValue());
        }
    }

}
