package net.Lucent.EasyGui.interfaces.complex_events;

import net.Lucent.EasyGui.interfaces.events.GuiScaleListener;
import net.Lucent.EasyGui.interfaces.events.ScreenResizeListener;
import net.minecraft.client.Minecraft;

public interface Sticky extends ScreenResizeListener, GuiScaleListener {



    //TODO make work..
    void recalculatePos(int oldWidth, int oldHeight);
    @Override
    default void onGuiScaleChanged(double oldScale){
        System.out.println("scale changed");
        //old height = current height. because only scale factor changed
        recalculatePos(Minecraft.getInstance().getWindow().getWidth(),Minecraft.getInstance().getWindow().getHeight());
    }

    @Override
    default void onResize(int oldWidth, int oldHeight){
        System.out.println("Resize");
        recalculatePos(oldWidth,oldHeight);
    };
}
