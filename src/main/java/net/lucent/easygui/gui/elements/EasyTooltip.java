package net.lucent.easygui.gui.elements;

import net.lucent.easygui.gui.RenderableElement;
import net.lucent.easygui.gui.UIFrame;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class EasyTooltip extends RenderableElement {

    public MutableComponent component = Component.empty();

    public EasyTooltip(UIFrame frame) {
        super(frame);
        setActive(false);
        getTransform().setUseScale(true);
    }

    @Override
    public void setActive(boolean active) {
        super.setActive(active);
        if(!active){

            component = Component.empty();
        }
    }

    @Override
    public void renderTick(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderTick(guiGraphics, mouseX, mouseY, partialTick);
    }


    public void appendText(Component component){
        this.component.append(component);
    }
    public void setScale(float scale){
        getTransform().setScale(scale);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.renderTooltip(
                Minecraft.getInstance().font,
                component,
                0,
                0
        );
    }
}
