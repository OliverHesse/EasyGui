package net.lucent.easygui.templating.registry;

import com.google.gson.JsonPrimitive;
import net.lucent.easygui.EasyGui;
import net.lucent.easygui.elements.containers.View;
import net.lucent.easygui.elements.containers.panels.DraggablePanel;
import net.lucent.easygui.elements.containers.panels.Panel;
import net.lucent.easygui.elements.containers.scroll_boxes.AbstractScrollBox;
import net.lucent.easygui.elements.containers.scroll_boxes.DynamicScrollBox;
import net.lucent.easygui.elements.containers.scroll_boxes.FixedSizedScrollBox;
import net.lucent.easygui.elements.controls.buttons.ColorButton;
import net.lucent.easygui.elements.controls.buttons.TextureButton;
import net.lucent.easygui.elements.controls.buttons.ToggleButton;
import net.lucent.easygui.elements.controls.inputs.ComboBox;
import net.lucent.easygui.elements.controls.inputs.MultiLineTextBox;
import net.lucent.easygui.elements.controls.inputs.TextBox;
import net.lucent.easygui.elements.other.Image;
import net.lucent.easygui.elements.other.Label;
import net.lucent.easygui.elements.other.ProgressBar;
import net.lucent.easygui.interfaces.ContainerRenderable;
import net.lucent.easygui.interfaces.events.Clickable;
import net.lucent.easygui.templating.IRenderableDeserializer;
import net.lucent.easygui.templating.actions.IAction;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.model.renderable.IRenderable;
import net.neoforged.neoforge.registries.*;

@EventBusSubscriber(modid = EasyGui.MOD_ID,bus = EventBusSubscriber.Bus.MOD)
public class EasyGuiRegistries {
    public static class Deserializers{
        public static final ResourceKey<Registry<IRenderableDeserializer>> DESERIALIZER_REGISTRY_KEY = ResourceKey.createRegistryKey(ResourceLocation
                .fromNamespaceAndPath(EasyGui.MOD_ID,"deserializers"));
        public static final Registry<IRenderableDeserializer> DESERIALIZER_REGISTRY = new RegistryBuilder<>(DESERIALIZER_REGISTRY_KEY)
                .defaultKey(ResourceLocation.fromNamespaceAndPath(EasyGui.MOD_ID,"empty"))
                .create();
        public static final DeferredRegister<IRenderableDeserializer> DESERIALIZERS =DeferredRegister.create(DESERIALIZER_REGISTRY,EasyGui.MOD_ID);

        public static final DeferredHolder<IRenderableDeserializer,Panel.Deserializer> PANEL = DESERIALIZERS.register("panel",()->new Panel.Deserializer(Panel::new));
        public static final DeferredHolder<IRenderableDeserializer, DraggablePanel.Deserializer> DRAGGABLE_PANEL = DESERIALIZERS.register("draggable_panel",()->new Panel.Deserializer(DraggablePanel::new));
        public static final DeferredHolder<IRenderableDeserializer, View.Deserializer> VIEW = DESERIALIZERS.register("view",()->new View.Deserializer(View::new));
        public static final DeferredHolder<IRenderableDeserializer, ColorButton.Deserializer> COLOR_BUTTON = DESERIALIZERS.register("color_button",()->new ColorButton.Deserializer(ColorButton::new));
        public static final DeferredHolder<IRenderableDeserializer, TextureButton.Deserializer> TEXTURE_BUTTON = DESERIALIZERS.register("texture_button",()->new TextureButton.Deserializer(TextureButton::new));
        public static final DeferredHolder<IRenderableDeserializer, ToggleButton.Deserializer> TOGGLE_BUTTON = DESERIALIZERS.register("toggle_button",()->new ToggleButton.Deserializer(ToggleButton::new));

        public static final DeferredHolder<IRenderableDeserializer, Image.Deserializer> IMAGE = DESERIALIZERS.register("image",()->new Image.Deserializer(Image::new));
        public static final DeferredHolder<IRenderableDeserializer, Label.Deserializer> LABEL = DESERIALIZERS.register("label",()->new Label.Deserializer(Label::new));
        public static final DeferredHolder<IRenderableDeserializer, ProgressBar.Deserializer> PROGRESS_BAR = DESERIALIZERS.register("progress_bar",()->new ProgressBar.Deserializer(ProgressBar::new));

        public static final DeferredHolder<IRenderableDeserializer, FixedSizedScrollBox.Deserializer> FIXED_SIZED_SCROLL_BOX = DESERIALIZERS.register("fixed_sized_scroll_box",()->new FixedSizedScrollBox.Deserializer(FixedSizedScrollBox::new));
        public static final DeferredHolder<IRenderableDeserializer, AbstractScrollBox.Deserializer> DYNAMIC_SCROLL_BOX = DESERIALIZERS.register("dynamic_scroll_box",()->new AbstractScrollBox.Deserializer(DynamicScrollBox::new));
        public static final DeferredHolder<IRenderableDeserializer, ComboBox.Deserializer> COMBO_BOX = DESERIALIZERS.register("combo_box",()->new ComboBox.Deserializer(ComboBox::new));

        public static final DeferredHolder<IRenderableDeserializer, TextBox.Deserializer> TEXT_BOX = DESERIALIZERS.register("text_box",()->new TextBox.Deserializer(TextBox::new));
        public static final DeferredHolder<IRenderableDeserializer, MultiLineTextBox.Deserializer> MULTI_LINE_TEXT_BOX = DESERIALIZERS.register("multi_line_text_box",()->new MultiLineTextBox.Deserializer(MultiLineTextBox::new));
    }


    public static class Actions{
        public static final ResourceKey<Registry<IAction>> ACTION_REGISTRY_KEY = ResourceKey.createRegistryKey(ResourceLocation
                .fromNamespaceAndPath(EasyGui.MOD_ID,"actions"));
        public static final Registry<IAction> ACTION_REGISTRY = new RegistryBuilder<>(ACTION_REGISTRY_KEY)
                .defaultKey(ResourceLocation.fromNamespaceAndPath(EasyGui.MOD_ID,"empty"))
                .create();
        public static final DeferredRegister<IAction> ACTIONS =DeferredRegister.create(ACTION_REGISTRY,EasyGui.MOD_ID);

        public static final DeferredHolder<IAction, Clickable.IClickAction> CHANGE_ACTIVE_VIEW = ACTIONS.register("change_view",
                ()->new Clickable.IClickAction() {
                    @Override
                    public void run(ContainerRenderable renderable, double mouseX, double mouseY, int button, boolean clicked, Object[] args) {
                        Clickable.IClickAction.super.run(renderable, mouseX, mouseY, button, clicked, args);
                        System.out.println("clicked");
                        if(clicked && args.length == 1){
                            System.out.println("clicked2");
                            if(args[0] instanceof JsonPrimitive) {
                                System.out.println(((JsonPrimitive) args[0]).getAsString());
                                ContainerRenderable view = renderable.getScreen().getElementByID(((JsonPrimitive) args[0]).getAsString());
                                if(view == null){
                                    EasyGui.LOGGER.error("no element of id {}",args[0].toString());
                                    return;
                                }
                                if(!(view instanceof View)){
                                    EasyGui.LOGGER.error("error expected View got {}",view.getClass());
                                    return;
                                }
                                renderable.getScreen().setActiveView((View) view);
                            } else{
                                EasyGui.LOGGER.error("error expected String got {}",args[0].getClass());
                            }
                        } else if (args.length != 1) {
                            EasyGui.LOGGER.error("error expected 1 arg got {}",args.length);
                        }
                    }
                });

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
