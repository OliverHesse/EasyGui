package net.lucent.easygui;

import net.lucent.easygui.elements.containers.View;
import net.lucent.easygui.overlays.EasyGuiOverlay;
import net.lucent.easygui.overlays.EasyGuiOverlayHandler;
import net.lucent.easygui.overlays.EasyGuiOverlayManager;
import net.lucent.easygui.templating.registry.EasyGuiRegistries;
import net.lucent.easygui.testing.test_elements.HealthProgressBar;
import net.lucent.easygui.util.textures.TextureData;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.neoforge.common.NeoForge;

@Mod(value = EasyGui.MOD_ID,dist = Dist.CLIENT)
public class EasyGuiClient{
    public EasyGuiClient(IEventBus modEventBus, ModContainer modContainer)
    {

        EasyGuiRegistries.register(modEventBus);
        //ModMenuTypes.register(modEventBus);

        modEventBus.addListener(EasyGuiOverlayManager::onRegisterOverlays);
        EasyGuiOverlayManager.registerVanillaOverlayOverride(VanillaGuiLayers.PLAYER_HEALTH,new EasyGuiOverlay((easyGuiEventHolder, easyGuiOverlay) ->{
            View view = new View(easyGuiOverlay);
            easyGuiOverlay.addView(view);
            HealthProgressBar healthProgressBar = new HealthProgressBar(easyGuiOverlay,
                    new TextureData( ResourceLocation.fromNamespaceAndPath(EasyGui.MOD_ID,"test_textures/health_bar_overlay.png"),81,9),
                    new TextureData(ResourceLocation.fromNamespaceAndPath(EasyGui.MOD_ID,"test_textures/health_bar_background.png"),81,8),
                    view.getScaledWidth()/2-91,
                    view.getScaledHeight()/2-39
                    );

            view.addChild(healthProgressBar);
            healthProgressBar.setSticky(true);
        }));
        //EasyGuiOverlayManager.addLayer(ResourceLocation.fromNamespaceAndPath(MOD_ID,"cultivation_progress"),new RandomOverlay());
    }

}
