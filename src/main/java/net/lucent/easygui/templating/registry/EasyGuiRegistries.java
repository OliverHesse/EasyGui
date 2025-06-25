package net.lucent.easygui.templating.registry;

import net.lucent.easygui.EasyGui;
import net.lucent.easygui.elements.containers.View;
import net.lucent.easygui.elements.containers.panels.Panel;
import net.lucent.easygui.interfaces.ContainerRenderable;
import net.lucent.easygui.templating.IRenderableDeserializer;
import net.lucent.easygui.templating.actions.Action;
import net.lucent.easygui.templating.actions.IAction;
import net.lucent.easygui.templating.deserializers.BaseDeserializer;
import net.lucent.easygui.templating.deserializers.ViewDeserializer;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.*;

import java.util.function.Supplier;
@EventBusSubscriber(modid = EasyGui.MOD_ID,bus = EventBusSubscriber.Bus.MOD)
public class EasyGuiRegistries {
    public static class Deserializers{
        public static final ResourceKey<Registry<IRenderableDeserializer>> DESERIALIZER_REGISTRY_KEY = ResourceKey.createRegistryKey(ResourceLocation
                .fromNamespaceAndPath(EasyGui.MOD_ID,"deserializers"));
        public static final Registry<IRenderableDeserializer> DESERIALIZER_REGISTRY = new RegistryBuilder<>(DESERIALIZER_REGISTRY_KEY)
                .defaultKey(ResourceLocation.fromNamespaceAndPath(EasyGui.MOD_ID,"empty"))
                .create();
        public static final DeferredRegister<IRenderableDeserializer> DESERIALIZERS =DeferredRegister.create(DESERIALIZER_REGISTRY,EasyGui.MOD_ID);

        public static final DeferredHolder<IRenderableDeserializer,BaseDeserializer> PANEL = DESERIALIZERS.register("panel",()->new BaseDeserializer(Panel::new));
        public static final DeferredHolder<IRenderableDeserializer, ViewDeserializer> VIEW = DESERIALIZERS.register("view",()->new ViewDeserializer(View::new));
    }

    public static class Actions{
        public static final ResourceKey<Registry<IAction>> ACTION_REGISTRY_KEY = ResourceKey.createRegistryKey(ResourceLocation
                .fromNamespaceAndPath(EasyGui.MOD_ID,"actions"));
        public static final Registry<IAction> ACTION_REGISTRY = new RegistryBuilder<>(ACTION_REGISTRY_KEY)
                .defaultKey(ResourceLocation.fromNamespaceAndPath(EasyGui.MOD_ID,"empty"))
                .create();
        public static final DeferredRegister<IAction> ACTIONS =DeferredRegister.create(ACTION_REGISTRY,EasyGui.MOD_ID);

        public static final DeferredHolder<IAction, ContainerRenderable.TickAction> GENERIC_TICK_ACTION = ACTIONS.register("generic_tick",
                ()-> (renderable, customArgs) -> {
                            //System.out.println("this tick action worked lol");
                        }
        );


    }
    @SubscribeEvent // on the mod event bus
    public static void registerRegistries(NewRegistryEvent event) {
        event.register(Deserializers.DESERIALIZER_REGISTRY);
        event.register(Actions.ACTION_REGISTRY);
    }
    public static void register(IEventBus modBus){
        Deserializers.DESERIALIZERS.register(modBus);
        Actions.ACTIONS.register(modBus);
    }

}
