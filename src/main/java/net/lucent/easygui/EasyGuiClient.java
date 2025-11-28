package net.lucent.easygui;

import net.lucent.easygui.elements.containers.View;
import net.lucent.easygui.overlays.EasyGuiOverlay;
import net.lucent.easygui.overlays.EasyGuiOverlayHandler;
import net.lucent.easygui.overlays.EasyGuiOverlayManager;
import net.lucent.easygui.templating.registry.EasyGuiRegistries;
import net.lucent.easygui.testing.KeyHandler;
import net.lucent.easygui.testing.test_elements.HealthProgressBar;
import net.lucent.easygui.util.textures.TextureData;
import net.lucent.easygui.util.textures.TextureDataSubSection;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.neoforge.common.NeoForge;

import static net.lucent.easygui.EasyGui.MOD_ID;

@Mod(value = MOD_ID,dist = Dist.CLIENT)
public class EasyGuiClient{
    public EasyGuiClient(IEventBus modEventBus, ModContainer modContainer)
    {

        EasyGuiRegistries.register(modEventBus);
        //ModMenuTypes.register(modEventBus);

        modEventBus.addListener(EasyGuiOverlayManager::onRegisterOverlays);
        //ResourceLocation.fromNamespaceAndPath(MOD_ID,"test")
        //KeyHandler.register();
        /*
        EasyGuiOverlayManager.addLayer(ResourceLocation.fromNamespaceAndPath(MOD_ID,"test"), new EasyGuiOverlay((eventHolder, overlay) ->{
            View view = new View(overlay,0,0);
            view.setUseMinecraftScale(true);


            HealthProgressBar progressBar = new HealthProgressBar(
                    overlay,
                    new TextureDataSubSection(ResourceLocation.fromNamespaceAndPath(EasyGui.MOD_ID,"test_textures/health_bar.png")
                            ,81,18,0,9,81,18),
                    new TextureDataSubSection(ResourceLocation.fromNamespaceAndPath(EasyGui.MOD_ID,"test_textures/health_bar.png")
                            ,81,18,0,0,81,9),
                    view.getScaledWidth()/2 - 91,
                    view.getScaledHeight() - 39); //view.getWidth()/2 - 91 view.getHeight() - 39
            view.addChild(progressBar);
            progressBar.setSticky(true);
            overlay.addView(view);
        }));

         */
        //EasyGuiOverlayManager.addLayer(ResourceLocation.fromNamespaceAndPath(MOD_ID,"cultivation_progress"),new RandomOverlay());
    }

}
