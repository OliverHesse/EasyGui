package net.lucent.easygui.interfaces.complex_events;

import com.mojang.blaze3d.platform.InputConstants;
import net.lucent.easygui.interfaces.events.Clickable;
import net.lucent.easygui.interfaces.events.MouseDragListener;
import net.lucent.easygui.interfaces.events.MouseReleaseListener;
import net.minecraft.util.Mth;

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
            setX((int) (mouseX-getClickPosX()) );
            setY((int) (mouseY-getClickPosY()));
        }
    }
    @Override
    default void onMouseReleased(double mouseX, double mouseY, int button){
        if(button == InputConstants.MOUSE_BUTTON_LEFT) {
            setDragged(false);
        }
    }


}
