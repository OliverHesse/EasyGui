package net.lucent.easygui.elements.containers.panels;

import com.mojang.blaze3d.platform.InputConstants;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.lucent.easygui.interfaces.complex_events.Draggable;
import net.lucent.easygui.interfaces.complex_events.Sticky;
import net.lucent.easygui.interfaces.events.MouseDragListener;
import net.lucent.easygui.util.math.BoundChecker;
import net.minecraft.client.gui.components.events.ContainerEventHandler;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DraggablePanel extends Panel implements Draggable{

    boolean dragged = false;
    int clickedX = 0;
    int clickedY = 0;
    public DraggablePanel(){}
    public DraggablePanel(IEasyGuiScreen easyGuiScreen,int x,int y,int width, int height) {
        super(easyGuiScreen,x,y,width,height);
    }

    @Override
    public int getClickPosX() {
        return clickedX;
    }

    @Override
    public int getClickPosY() {
        return clickedY;
    }

    @Override
    public boolean isDragged() {
        return dragged;
    }

    @Override
    public void setDragged(boolean state) {
        dragged = state;
    }

    @Override
    public void setClickPosition(double mouseX, double mouseY) {
        BoundChecker.Vec2 point = screenToLocalPoint(mouseX,mouseY);

        this.clickedY = point.y;
        this.clickedX = point.x;
    }




    @Override
    public boolean isSticky() {
        return true;
    }
}
