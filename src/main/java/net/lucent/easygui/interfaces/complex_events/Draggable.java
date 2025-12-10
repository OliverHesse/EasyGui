package net.lucent.easygui.interfaces.complex_events;

import com.mojang.blaze3d.platform.InputConstants;
import net.lucent.easygui.interfaces.events.Clickable;
import net.lucent.easygui.interfaces.events.MouseDragListener;
import net.lucent.easygui.interfaces.events.MouseReleaseListener;
import net.lucent.easygui.properties.Positioning;
import net.lucent.easygui.util.math.BoundChecker;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface Draggable extends Clickable, MouseDragListener, MouseReleaseListener {

    boolean isDragged();
    void setDragged(boolean state);
    void setClickPosition(double mouseX, double mouseY);
    @Override
    default void onClick(double mouseX, double mouseY, int button, boolean clicked){
        if(clicked && button == InputConstants.MOUSE_BUTTON_LEFT) {
            setDragged(true);
            setClickPosition(mouseX,mouseY);
        }

    }
    default int getClickPosX(){return 0;};
    default int getClickPosY(){return 0;};
    @Override
    default void onDrag(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if(isDragged() && button == InputConstants.MOUSE_BUTTON_LEFT){

            BoundChecker.Vec2 localPoint = screenToLocalPoint(mouseX,mouseY);
            BoundChecker.Vec2 finalLocalPoint = new BoundChecker.Vec2(localPoint.x-getClickPosX(),localPoint.y-getClickPosY());
            BoundChecker.Vec2 screenPoint = localToScreenPoint(finalLocalPoint.x, finalLocalPoint.y);
            BoundChecker.Vec2 point = getParent().screenToLocalPoint(screenPoint.x,screenPoint.y);
            setX(point.x, Positioning.START);
            setY(point.y ,Positioning.START);
        }
    }
    @Override
    default void onMouseReleased(double mouseX, double mouseY, int button){
        if(button == InputConstants.MOUSE_BUTTON_LEFT) {
            setDragged(false);
        }
    }


}
