package net.Lucent.EasyGui.overlays;

import net.Lucent.EasyGui.elements.other.BaseRenderable;
import net.Lucent.EasyGui.elements.other.View;
import net.Lucent.EasyGui.holders.EasyGuiEventHolder;
import net.Lucent.EasyGui.interfaces.ContainerRenderable;
import net.Lucent.EasyGui.interfaces.EasyGuiScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.ContainerEventHandler;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.fluids.capability.templates.FluidHandlerItemStack;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

@OnlyIn(Dist.CLIENT)
public class EasyGuiOverlay implements EasyGuiScreen {

    public int windowWidth = Minecraft.getInstance().getWindow().getWidth();
    public int windowHeight = Minecraft.getInstance().getWindow().getHeight();
    public double guiScale = Minecraft.getInstance().getWindow().getGuiScale();
    public View view;
    public EasyGuiEventHolder eventHolder = new EasyGuiEventHolder();
    public BiConsumer<EasyGuiEventHolder,EasyGuiOverlay> runnable;
    public EasyGuiOverlay(BiConsumer<EasyGuiEventHolder,EasyGuiOverlay>  initialize){
        this.runnable = initialize;

        NeoForge.EVENT_BUS.register(this);
    }




    //should only run client side
    @SubscribeEvent
    private void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event){
        runnable.accept(eventHolder,this);
    }

    void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick){
        //initialize();
        Minecraft mc = Minecraft.getInstance();
        //Annoyingly overlays don't get a resize event...
        if(mc.getWindow().getHeight() != windowHeight || windowWidth != mc.getWindow().getWidth()){
            eventHolder.SCREEN_RESIZE_EVENT.call(windowWidth,windowHeight);
            windowWidth = mc.getWindow().getWidth();
            windowHeight = mc.getWindow().getHeight();

        }
        if(mc.getWindow().getGuiScale() != guiScale){
            eventHolder.GUI_SCALE_CHANGED_EVENT.call(guiScale);
            guiScale = mc.getWindow().getGuiScale();

        }
        view.render(guiGraphics,mouseX,mouseY,partialTick);
    }


    @Override
    public void removeView(View view) {

    }
}
