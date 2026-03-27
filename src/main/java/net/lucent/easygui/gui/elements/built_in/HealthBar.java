package net.lucent.easygui.gui.elements.built_in;

import net.lucent.easygui.gui.RenderableElement;
import net.lucent.easygui.gui.UIFrame;
import net.lucent.easygui.gui.layout.positioning.rules.PositioningRules;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;

public class HealthBar extends RenderableElement {
    private final int backgroundColor = 0xff000000;
    private final int healthColor =0xffeb4034;

    public HealthBar(UIFrame frame) {
        super(frame);
        setWidth(80);
        setHeight(10);
        getPositioning().setXPositioningRule(PositioningRules.CENTER);
        getPositioning().setYPositioningRule(PositioningRules.END);
        getPositioning().setX(-91);
        getPositioning().setY(39);
    }

    public double getHealthProgress(){
        Player player = Minecraft.getInstance().player;
        if(player == null)return 0.0;
        return player.getHealth()/player.getMaxHealth();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        //ensures it is only visible in survival when hideGUI is not enabled
        if(Minecraft.getInstance().options.hideGui ||
                (Minecraft.getInstance().gameMode != null &&
                        !Minecraft.getInstance().gameMode.canHurtPlayer())) return;
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        //makes sure the rest of the GUI renders properly,if you want exact values check mc source code
        Minecraft.getInstance().gui.leftHeight += 10;

        guiGraphics.fill(0,0,getWidth(),getHeight(),backgroundColor);
        guiGraphics.fill(0,0, (int) (getWidth()*getHealthProgress()),getHeight(),healthColor);
    }
}
