package net.lucent.easygui.testing.test_elements;

import net.lucent.easygui.elements.other.ProgressBar;
import net.lucent.easygui.elements.other.View;
import net.lucent.easygui.holders.EasyGuiEventHolder;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.lucent.easygui.interfaces.ITextureData;
import net.lucent.easygui.interfaces.complex_events.Sticky;
import net.minecraft.client.Minecraft;

public class HealthProgressBar extends ProgressBar implements Sticky {


    double ROTATION_RATE = (double) 360 /60;

    public HealthProgressBar(IEasyGuiScreen easyGuiScreen, ITextureData progressBarTexture, ITextureData backgroundTexture, int x, int y) {
        super(easyGuiScreen, progressBarTexture,backgroundTexture, x, y);


    }

    @Override
    public void tick() {

        //setRotation(getRotationX()+ROTATION_RATE,getRotationY()+ROTATION_RATE,getRotationZ()+ROTATION_RATE);
    }

    @Override
    public double getProgress() {

        if(Minecraft.getInstance().player == null) return 0;
        double currentHealth = Minecraft.getInstance().player.getHealth();
        double maxHealth = Minecraft.getInstance().player.getMaxHealth();
        return  currentHealth/maxHealth;
    }

    @Override
    public void setSticky(boolean sticky) {

    }

    @Override
    public boolean isSticky() {
        return true;
    }

    @Override
    public void recalculatePos(int oldWidth, int oldHeight) {

        setX((getRoot()).getScaledWidth()/2-91);
        setY((getRoot()).getScaledHeight() - 39);
    }

}
