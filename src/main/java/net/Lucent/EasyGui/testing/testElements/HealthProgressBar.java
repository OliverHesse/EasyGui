package net.Lucent.EasyGui.testing.testElements;

import net.Lucent.EasyGui.elements.other.ProgressBar;
import net.Lucent.EasyGui.elements.other.View;
import net.Lucent.EasyGui.holders.EasyGuiEventHolder;
import net.Lucent.EasyGui.interfaces.complexEvents.Sticky;
import net.Lucent.EasyGui.util.TextureData;
import net.minecraft.client.Minecraft;

public class HealthProgressBar extends ProgressBar implements Sticky {


    double ROTATION_RATE = (double) 360 /60;

    public HealthProgressBar(EasyGuiEventHolder eventHandler,  TextureData progressBarTexture, TextureData backgroundTexture, int x, int y) {
        super(eventHandler, progressBarTexture,backgroundTexture, x, y);

    }

    @Override
    public void tick() {

        setRotation(getRotationX()+ROTATION_RATE,getRotationY()+ROTATION_RATE,getRotationZ()+ROTATION_RATE);
    }

    @Override
    public double getProgress() {

        if(Minecraft.getInstance().player == null) return 0;
        double currentHealth = Minecraft.getInstance().player.getHealth();
        double maxHealth = Minecraft.getInstance().player.getMaxHealth();
        return  currentHealth/maxHealth;
    }

    @Override
    public void recalculatePos(int oldWidth, int oldHeight) {
        setX(((View)getParent()).getScaledWidth()/2-91);
        setY(((View) getParent()).getScaledHeight() - 39);
    }
}
