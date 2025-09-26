package net.lucent.easygui.elements.tooltips;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

import java.util.List;
//TODO make tooltips more complex
public record EasyTooltip(int x, int y, List<Component> content){
    public void render(GuiGraphics guiGraphics){
        guiGraphics.renderComponentTooltip(Minecraft.getInstance().font,content,x,y);
    }
}
