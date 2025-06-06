package net.Lucent.EasyGui.screens;

import net.Lucent.EasyGui.elements.other.View;
import net.Lucent.EasyGui.holders.EasyGuiEventHolder;
import net.Lucent.EasyGui.interfaces.EasyGuiScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ScreenEvent;

import java.util.ArrayList;
import java.util.List;


public class EasyGuiBaseScreen extends Screen implements EasyGuiScreen {

    public List<View> views = new ArrayList<>();
    public EasyGuiEventHolder eventHolder = new EasyGuiEventHolder();

    public EasyGuiBaseScreen(Component title) {
        super(title);
    }


    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        for(View view : views){
            if(view.isActive()) view.render(guiGraphics,mouseX,mouseY,partialTick);
        }
    }

    @Override
    public void removeView(View view) {
        views.remove(view);
    }



    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        eventHolder.MOUSE_MOVED_EVENT.call(mouseX, mouseY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        eventHolder.MOUSE_SCROLL_EVENT.call(mouseX, mouseY, scrollX, scrollY);
        return true;
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        eventHolder.KEY_RELEASED_EVENT.call(keyCode, scanCode, modifiers);
        return true;
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        eventHolder.CHAR_TYPED_EVENT.call(codePoint,modifiers);
        return true;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        eventHolder.CLICK_EVENT.call(mouseX, mouseY, button);
        return true;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        eventHolder.MOUSE_RELEASE_EVENT.call(mouseX, mouseY, button);
        return true;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        eventHolder.MOUSE_DRAG_EVENT.call(mouseX, mouseY, button,dragX,dragY);
        return true;
    }

    @Override
    public void tick() {
        super.tick();

        double mouseX = Minecraft.getInstance().mouseHandler.xpos();
        double mouseY = Minecraft.getInstance().mouseHandler.ypos();
        System.out.println(mouseX + " , "+ mouseY);
        eventHolder.MOUSE_OVER_EVENT.call(mouseX,mouseY);
    }


}
