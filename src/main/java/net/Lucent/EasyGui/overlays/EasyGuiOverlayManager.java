package net.Lucent.EasyGui.overlays;

import net.Lucent.EasyGui.EasyGui;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;

import java.util.ArrayList;
import java.util.List;

public class EasyGuiOverlayManager implements LayeredDraw.Layer {

    public static final EasyGuiOverlayManager INSTANCE = new EasyGuiOverlayManager();

    public static final List<EasyGuiOverlay> overlays = new ArrayList<>();

    public void onRegisterOverlays(RegisterGuiLayersEvent event) {
        event.registerAboveAll(ResourceLocation.fromNamespaceAndPath(EasyGui.MOD_ID, "overlay"), INSTANCE);
    }

    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        Minecraft mc = Minecraft.getInstance();

        if (!mc.getDebugOverlay().showDebugScreen() && !mc.options.hideGui) {
            for(EasyGuiOverlay overlay:overlays){
                overlay.render(guiGraphics, (int) mc.mouseHandler.xpos(),(int) mc.mouseHandler.ypos(),
                        deltaTracker.getGameTimeDeltaPartialTick(true));
            }
        }
    }
}
