package net.lucent.easygui.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import org.joml.Matrix4f;

public class UIFrame {

    private RenderableElement root;

    private Matrix4f baseTransform;
    private int width;
    private int height;
    private boolean useMinecraftScale;



    public double getMinecraftScale(){
        return Minecraft.getInstance().getWindow().getGuiScale();
    }

    public void updateBaseTransform(){
        baseTransform = new Matrix4f();
        if(isUsingMinecraftScale()) baseTransform.scale((float) (1/getMinecraftScale()));
    }

    public void updateDimensions(Minecraft minecraft){
        width = isUsingMinecraftScale() ? minecraft.getWindow().getGuiScaledWidth() : minecraft.getWindow().getWidth();
        height = isUsingMinecraftScale() ? minecraft.getWindow().getGuiScaledHeight() : minecraft.getWindow().getHeight();
    }

    public void onWindowResize(Minecraft minecraft){
        updateBaseTransform();
        updateDimensions(minecraft);
    }

    //======================= SETTERS =======================

    public void setUseMinecraftScale(boolean useMinecraftScale) {
        this.useMinecraftScale = useMinecraftScale;
        updateBaseTransform();
        //TODO trigger layout change
    }


    //======================= GETTERS =======================

    public boolean isUsingMinecraftScale(){return this.useMinecraftScale;}

    public int getHeight(){
        return height;
    }
    public int getWidth(){
        return width;
    }

    public Matrix4f getBaseTransform() {
        return baseTransform;
    }


    //========================= RUNTIME ==========================
    public void run(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick){
        guiGraphics.pose().pushPose();
        guiGraphics.pose().mulPose(getBaseTransform());
        if(root != null) root.run(guiGraphics,mouseX,mouseY,partialTick);
        guiGraphics.pose().popPose();
    }

}
