package net.lucent.easygui.gui.overaly;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;

public class EasyOverlayPosition {

    private final ResourceLocation location;
    private final Position position;

    private EasyOverlayPosition(ResourceLocation location, Position position) {
        this.location = location;
        this.position = position;
    }
    public void register(RegisterGuiLayersEvent event,ResourceLocation key,EasyOverlayHandler.EasyOverlay overlay){
        position.register(event,location,key,overlay);

    }
    public static EasyOverlayPosition ABOVE(ResourceLocation location){
        return new EasyOverlayPosition(location,Position.ABOVE);
    }
    public static EasyOverlayPosition BELOW(ResourceLocation location){
        return new EasyOverlayPosition(location,Position.BELOW);
    }
    public static EasyOverlayPosition ABOVE_ALL(){
        return new EasyOverlayPosition(null,Position.ABOVE_ALL);
    }
    public static EasyOverlayPosition BELOW_ALL(){
        return new EasyOverlayPosition(null,Position.BELOW_ALL);
    }
    private enum Position{
        ABOVE_ALL {
            @Override
            void register(RegisterGuiLayersEvent event, ResourceLocation location,ResourceLocation key, EasyOverlayHandler.EasyOverlay overlay) {
                event.registerAboveAll(key,new EasyOverlayLayer(overlay.frame(),overlay.type()));
            }
        },
        BELOW_ALL {
            @Override
            void register(RegisterGuiLayersEvent event, ResourceLocation location,ResourceLocation key, EasyOverlayHandler.EasyOverlay overlay) {
                event.registerBelowAll(key,new EasyOverlayLayer(overlay.frame(),overlay.type()));
            }
        },
        BELOW {
            @Override
            void register(RegisterGuiLayersEvent event, ResourceLocation location,ResourceLocation key, EasyOverlayHandler.EasyOverlay overlay) {
                event.registerBelow(location,key,new EasyOverlayLayer(overlay.frame(),overlay.type()));
            }
        },
        ABOVE {
            @Override
            void register(RegisterGuiLayersEvent event, ResourceLocation location,ResourceLocation key, EasyOverlayHandler.EasyOverlay overlay) {
                event.registerAbove(location,key,new EasyOverlayLayer(overlay.frame(),overlay.type()));
            }
        };

        abstract void register(RegisterGuiLayersEvent event,ResourceLocation location,ResourceLocation key,EasyOverlayHandler.EasyOverlay overlay);
    }
}
