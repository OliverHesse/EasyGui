package net.lucent.easygui.gui.overaly;

import net.lucent.easygui.gui.UIFrame;
import net.lucent.easygui.screen.IEasyScreen;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;


//TODO by default cannot detect screen resize
//TODO add all events
public class EasyOverlayLayer implements IEasyScreen, LayeredDraw.Layer {

    private final UIFrame frame;
    private final EasyOverlayType type;

    public EasyOverlayLayer(UIFrame frame, EasyOverlayType type){
        this.frame = frame;
        this.type = type;
    }

    @Override
    public UIFrame getUIFrame() {
        return frame;
    }

    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        MouseHandler handler = Minecraft.getInstance().mouseHandler;
        frame.run(guiGraphics, (int) handler.xpos(), (int) handler.ypos(),deltaTracker.getGameTimeDeltaTicks());
    }
}
