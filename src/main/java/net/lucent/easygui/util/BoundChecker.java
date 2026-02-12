package net.lucent.easygui.util;

import net.lucent.easygui.gui.RenderableElement;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.world.phys.Vec2;

public class BoundChecker {
    public boolean isPointInCullRegion(GuiGraphics guiGraphics, int x, int y){

        return guiGraphics.containsPointInScissor(x,y);
    }
    public static boolean isElementVisible(GuiGraphics guiGraphics, RenderableElement element){
        Vec2 point1 = element.getGlobalPoint();
        Vec2 point2 = element.getGlobalCornerPoint();

        boolean corner1 = guiGraphics.containsPointInScissor((int) point1.x, (int) point1.y);
        boolean corner2 = guiGraphics.containsPointInScissor((int) point1.x, (int) point2.y);
        boolean corner3 = guiGraphics.containsPointInScissor((int) point2.x, (int) point2.y);
        boolean corner4 = guiGraphics.containsPointInScissor((int) point2.x, (int) point1.y);
        return corner1 || corner2 || corner3 || corner4;
    }
}
