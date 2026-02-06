package net.lucent.easygui.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UIFrame {

    private RenderableElement root;

    private Matrix4f baseTransform;
    private int width;
    private int height;
    private boolean useMinecraftScale;

    private HashMap<String,RenderableElement> idMap = new HashMap<>();
    private HashMap<String, List<RenderableElement>> classMap = new HashMap<>();

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

    public void setId(String id,RenderableElement element){
        if(idMap.containsKey(id)) idMap.get(id).setId(null);
        idMap.remove(element.getId());
        idMap.put(id,element);
    }
    public void addClass(String classId, RenderableElement element){
        if(!classMap.containsKey(classId)) classMap.put(classId,new ArrayList<>());
        classMap.get(classId).add(element);
    }
    public void removeClass(String classId,RenderableElement element){
        if(!classMap.containsKey(classId)) return;
        classMap.get(classId).remove(element);
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

    public void removeElementFromIdAndClasses(RenderableElement element){
        idMap.remove(element.getId());
        element.clearClasses();
    }
}
