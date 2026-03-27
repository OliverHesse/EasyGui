package net.lucent.easygui.gui.overaly;

import com.mojang.blaze3d.platform.InputConstants;
import net.lucent.easygui.gui.UIFrame;
import net.lucent.easygui.screen.IEasyScreen;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.TextureAtlasHolder;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.neoforge.common.NeoForge;

import java.awt.event.MouseEvent;


//TODO by default cannot detect screen resize
//TODO add all events
public class EasyOverlayLayer implements IEasyScreen, LayeredDraw.Layer {

    private  UIFrame frame;
    private final EasyOverlayType type;
    double oldMouseX;
    double oldMouseY;
    int oldWidth;
    int oldHeight;
    public EasyOverlayLayer(UIFrame frame, EasyOverlayType type){
        this.frame = frame;
        this.type = type;
        NeoForge.EVENT_BUS.register(this);

    }

    @Override
    public UIFrame getUIFrame() {
        return frame;
    }



    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        MouseHandler handler = Minecraft.getInstance().mouseHandler;
        int width =  Minecraft.getInstance().getWindow().getWidth();
        int height =  Minecraft.getInstance().getWindow().getHeight();
        if(width != oldWidth || height != oldHeight){
            frame.onWindowResize(Minecraft.getInstance());
            oldWidth = width;
            oldHeight = height;
        }
        frame.run(guiGraphics, (int) handler.xpos(), (int) handler.ypos(),deltaTracker.getGameTimeDeltaTicks());
    }

    @SubscribeEvent
    private void onClientTick(ClientTickEvent.Pre event){
        double mouseX = Minecraft.getInstance().mouseHandler.xpos();
        double mouseY = Minecraft.getInstance().mouseHandler.ypos();
        if(oldMouseX != mouseX || oldMouseY != mouseY){
            getUIFrame().mouseMoved(mouseX,mouseY);
            oldMouseY = mouseY;
            oldMouseX = mouseX;
        }


    }


    @SubscribeEvent
    public void onScroll(InputEvent.MouseScrollingEvent event){
        getUIFrame().mouseScrolled(event.getMouseX(),event.getMouseY(),event.getScrollDeltaX(),event.getScrollDeltaY());
    }

    @SubscribeEvent
    public void onMouseClick(InputEvent.MouseButton.Pre event){
        double mouseX = Minecraft.getInstance().mouseHandler.xpos();
        double mouseY = Minecraft.getInstance().mouseHandler.ypos();
        if(event.getAction() == InputConstants.PRESS || event.getAction() == InputConstants.REPEAT){
            getUIFrame().mouseClicked(mouseX,mouseY,event.getButton());
            return;
        }
        getUIFrame().mouseReleased(mouseX,mouseY,event.getButton());
    }
    public void onInputEvent(InputEvent.Key keyEvent){
        if(keyEvent.getAction() == InputConstants.PRESS){
            getUIFrame().keyPressed(keyEvent.getKey(),keyEvent.getScanCode(),keyEvent.getModifiers());
        }else{
            getUIFrame().keyReleased(keyEvent.getKey(),keyEvent.getScanCode(),keyEvent.getModifiers());
        }
    }

    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick){

        if((type == EasyOverlayType.GAME && Minecraft.getInstance().player != null) || type == EasyOverlayType.TITLE_MENU){
            getUIFrame().run(guiGraphics,mouseX,mouseY,partialTick);
        }
    }

    @Override
    public void setUIFrame(UIFrame frame) {
        this.frame = frame;
    }


}
