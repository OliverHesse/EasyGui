package net.lucent.easygui.overlays;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;
@OnlyIn(Dist.CLIENT)
public class EasyGuiOverlayHandler implements LayeredDraw.Layer {

    public List<EasyGuiOverlay> overlays = new ArrayList<>();

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
