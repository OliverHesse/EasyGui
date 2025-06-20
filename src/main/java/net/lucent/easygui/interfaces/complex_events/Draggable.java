package net.lucent.easygui.interfaces.complex_events;

import com.mojang.blaze3d.platform.InputConstants;
import net.lucent.easygui.interfaces.events.Clickable;
import net.lucent.easygui.interfaces.events.MouseDragListener;
import net.lucent.easygui.interfaces.events.MouseReleaseListener;
import net.minecraft.util.Mth;

public interface Draggable extends Clickable, MouseDragListener, MouseReleaseListener {

    boolean isDragged();
    void setDragged(boolean state);

    @Override
    default void onClick(double mouseX, double mouseY, int button, boolean clicked){
        if(clicked && button == InputConstants.MOUSE_BUTTON_LEFT) {
            setDragged(true);
        }

    }

    @Override
    default void onMouseReleased(double mouseX, double mouseY, int button){
        if(button == InputConstants.MOUSE_BUTTON_LEFT) {
            setDragged(false);
        }
    }


}
