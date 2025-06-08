package net.Lucent.EasyGui.overlays;

import net.Lucent.EasyGui.elements.other.View;
import net.Lucent.EasyGui.holders.EasyGuiEventHolder;
import net.Lucent.EasyGui.interfaces.EasyGuiScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import java.util.function.BiConsumer;

/**
 * this cannot be inherited will cause problems with events
 */
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
    @SubscribeEvent
    private void onClientTick(ClientTickEvent.Pre event){
        eventHolder.TICK_EVENT.call();
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
