package net.lucent.easygui.overlays;

import com.mojang.blaze3d.platform.InputConstants;
import net.lucent.easygui.elements.other.View;
import net.lucent.easygui.holders.EasyGuiEventHolder;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import java.util.function.BiConsumer;

/**
 * this cannot be inherited will cause problems with events
 */
@OnlyIn(Dist.CLIENT)
public class EasyGuiOverlay implements IEasyGuiScreen {

    public int windowWidth = Minecraft.getInstance().getWindow().getWidth();
    public int windowHeight = Minecraft.getInstance().getWindow().getHeight();
    public double guiScale = Minecraft.getInstance().options.guiScale().get();
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
        double mouseX = Minecraft.getInstance().mouseHandler.xpos();
        double mouseY = Minecraft.getInstance().mouseHandler.ypos();
        eventHolder.TICK_EVENT.call();
        eventHolder.MOUSE_OVER_EVENT.call(mouseX,mouseY);
    }


    @SubscribeEvent
    public void onScroll(InputEvent.MouseScrollingEvent event){
        eventHolder.MOUSE_SCROLL_EVENT.call(event.getMouseX(),event.getMouseY(),event.getScrollDeltaX(),event.getScrollDeltaY());
    }

    @SubscribeEvent
    public void onMouseClick(InputEvent.MouseButton.Pre event){
        double mouseX = Minecraft.getInstance().mouseHandler.xpos();
        double mouseY = Minecraft.getInstance().mouseHandler.ypos();
        if(event.getAction() == InputConstants.PRESS || event.getAction() == InputConstants.REPEAT){
            eventHolder.CLICK_EVENT.call(mouseX,mouseY,event.getButton());
            return;
        }
        eventHolder.MOUSE_RELEASE_EVENT.call(mouseX,mouseY,event.getButton());
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
