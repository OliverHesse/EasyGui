package net.Lucent.EasyGui.elements.other;


import net.Lucent.EasyGui.holders.EasyGuiEventHolder;
import net.Lucent.EasyGui.interfaces.ContainerRenderable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

import java.util.ArrayList;
import java.util.List;

/**
 * handles all the scaling stuff
 */

public abstract class BaseRenderable implements ContainerRenderable {

    private int x;
    private int y;

    public boolean active = true;
    public boolean visible = true;
    public boolean focused = false;

    public boolean useCustomScaling = true;
    /**
     * after resize does it maintain its relative position
     */
    public boolean sticky = false;

    public double customScale = 1;



    public EasyGuiEventHolder eventHandler;
    public ContainerRenderable parent;
    public List<ContainerRenderable> children = new ArrayList<>();

    public BaseRenderable(EasyGuiEventHolder eventHandler){
        this.eventHandler = eventHandler;
        eventHandler.register(this);
    }

    @Override
    public int getX(){
        return x;
    }
    @Override
    public int getY(){
        return y;
    }

    /**
     * already includes the minecraft gui scale
     */
    @Override
    public double getGlobalScaledX() {

        if(parent != null){
            System.out.println("---");
            System.out.println(parent.getTotalScaleFactorX());
            System.out.println(parent.getGlobalScaledX());
            return getX()*parent.getTotalScaleFactorX() + parent.getGlobalScaledX();
        }
        return getX();
    }

    /**
     * already includes the minecraft gui scale
     */
    @Override
    public double getGlobalScaledY() {
        if(parent != null) return getY()*parent.getTotalScaleFactorY() + parent.getGlobalScaledY();
        return getY();
    }
    @Override
    public void setX(int x){
        this.x = x;
    }
    @Override
    public void setY(int y){
        this.y = y;
    }

    @Override
    public boolean isActive() {
        if(!active) return false;
        if(parent == null) return active;
        return parent.isActive();
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }


    @Override
    public boolean isFocused() {
        return focused;
    }

    @Override
    public void setFocused(boolean focused) {
        this.focused = focused;
    }


    @Override
    public boolean isVisible() {
        return visible;
    }

    @Override
    public void setVisible(boolean visible) {
        this.visible = visible;
    }


    /**
     * by default does not remove minecraft internal scaling. that is done by a view
     * @return
     */
    public double getScale(){
        if(useCustomScaling){
            return customScale;
        }
        return 1;
    }

    public double getScaleX(){
        return getScale();
    }
    public double getScaleY(){
        return getScale();
    }



    @Override
    public void setRenderScale(GuiGraphics guiGraphics){

        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(x,y,0);
        if(useCustomScaling){
            guiGraphics.pose().scale((float) getScaleX(), (float) getScaleY(),1);
        }
    }
    @Override
    public void resetRenderScale(GuiGraphics guiGraphics){
        guiGraphics.pose().popPose();
    }


    @Override
    public void renderChildren(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick){
        for(ContainerRenderable child:getChildren()){
            if(child.isActive() && child.isVisible()) child.render(guiGraphics,mouseX,mouseY,partialTick);
        }
    }

    @Override
    public final void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {

        setRenderScale(guiGraphics);
        renderSelf(guiGraphics,mouseX,mouseY,partialTick);
        renderChildren(guiGraphics,mouseX,mouseY,partialTick);
        resetRenderScale(guiGraphics);

    }




    public double getMinecraftScaling(){
        return Minecraft.getInstance().getWindow().getGuiScale();
    }

    @Override
    public double getTotalCustomScaling(){
        if(parent != null) return customScale*parent.getTotalCustomScaling();
        return customScale;
    }

    @Override
    public double getTotalScaleFactorX(){

        if(parent != null && this.useCustomScaling) return parent.getTotalScaleFactorX()*customScale;
        if(parent != null) return parent.getTotalScaleFactorX();
        if(useCustomScaling) return customScale;
        return 1;
    }
    @Override
    public double getTotalScaleFactorY(){

        if(parent != null && this.useCustomScaling) return parent.getTotalScaleFactorY()*customScale;
        if(parent != null) return parent.getTotalScaleFactorY();
        if(useCustomScaling) return customScale;
        return 1;
    }



    @Override
    public ContainerRenderable getRoot(){
        if(parent == null) return this;
        return parent.getRoot();
    }

    //needed for render calculations for things like scroll containers
    public int getWidth(){return 0;}
    public int getHeight(){return 0;}


    public int getScaledWidth(){
        System.out.println("scale factor: "+getTotalScaleFactorX());
        return (int) (getWidth()*getTotalScaleFactorX());
    }

    public int getScaledHeight(){
        return (int) (getHeight()*getTotalScaleFactorY());
    }


    @Override
    public void remove() {
        eventHandler.unregister(this);
        if(getParent() != null){
            parent.getChildren().remove(this);
        }
    }

    @Override
    public void setParent(ContainerRenderable parent) {
        this.parent = parent;
    }

    @Override
    public void addChild(ContainerRenderable child) {
        this.children.add(child);
        child.setParent(this);
    }

    @Override
    public List<ContainerRenderable> getChildren() {
        return children;
    }

    @Override
    public ContainerRenderable getParent() {
        return parent;
    }
}
