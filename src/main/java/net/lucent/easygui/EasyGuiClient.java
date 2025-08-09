package net.lucent.easygui;

import net.lucent.easygui.overlays.EasyGuiOverlayManager;
import net.lucent.easygui.templating.registry.EasyGuiRegistries;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;

@Mod(value = EasyGui.MOD_ID,dist = Dist.CLIENT)
public class EasyGuiClient{
    public EasyGuiClient(IEventBus modEventBus, ModContainer modContainer)
    {

        EasyGuiRegistries.register(modEventBus);
        //ModMenuTypes.register(modEventBus);

        modEventBus.addListener(EasyGuiOverlayManager::onRegisterOverlays);
        //EasyGuiOverlayManager.addLayer(ResourceLocation.fromNamespaceAndPath(MOD_ID,"cultivation_progress"),new RandomOverlay());
    }

}
