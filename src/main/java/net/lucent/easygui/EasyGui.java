package net.lucent.easygui;

import net.lucent.easygui.overlays.EasyGuiOverlayManager;
import net.lucent.easygui.parsers.ShuntingYardExprParser;

import net.lucent.easygui.testing.KeyHandler;
import net.neoforged.fml.loading.FMLEnvironment;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(EasyGui.MOD_ID)
public class EasyGui
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "easy_gui";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();


    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public EasyGui(IEventBus modEventBus, ModContainer modContainer)
    {
        System.out.println(new ShuntingYardExprParser().parseInput(" 37+43*2/(parent.width-5)^22^3"));
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        //KeyHandler.register();

        if (FMLEnvironment.dist == Dist.CLIENT) {
            modEventBus.addListener(EasyGuiOverlayManager::onRegisterOverlays);
            //EasyGuiOverlayManager.addLayer(ResourceLocation.fromNamespaceAndPath(MOD_ID,"cultivation_progress"),new RandomOverlay());
            /*
            EasyGuiOverlayManager.registerVanillaOverlayOverride(VanillaGuiLayers.PLAYER_HEALTH, new EasyGuiOverlay((eventHolder, overlay) ->{
                View view = new View(eventHolder,overlay,0,0, Minecraft.getInstance().getWindow().getScreenWidth(),
                    Minecraft.getInstance().getWindow().getScreenHeight());
                view.useMinecraftScale = true;


                HealthProgressBar progressBar = new HealthProgressBar(
                        eventHolder,
                        new TextureData(ResourceLocation.fromNamespaceAndPath(EasyGui.MOD_ID,"test_textures/health_bar_overlay.png")
                                ,81,9),
                        new TextureData(ResourceLocation.fromNamespaceAndPath(EasyGui.MOD_ID,"test_textures/health_bar_background.png")
                                ,81,9),
                        view.getScaledWidth()/2 - 91,
                        view.getScaledHeight() - 39); //view.getWidth()/2 - 91 view.getHeight() - 39
                view.addChild(progressBar);
                overlay.view = view;
                }));


             */

        }

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}
