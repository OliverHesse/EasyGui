package net.lucent.easygui.elements;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.lucent.easygui.holders.EasyGuiEventHolder;
import net.lucent.easygui.interfaces.ContainerRenderable;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import org.joml.Quaternionf;

import java.util.ArrayList;
import java.util.List;

/**
 * handles all the scaling stuff
 */

public abstract class BaseRenderable implements ContainerRenderable {

    private int x;
    private int y;

    private int visibleX;
    private int visibleY;

    public int width;
    public int height;


    public int visibleWidth;
    public int visibleHeight;

    public boolean active = true;
    public boolean visible = true;
    public boolean focused = false;

    private int cullBorder = 0; //for borders

    private boolean cull = false;

    public boolean useCustomScaling = true;
    /**
     * after resize does it maintain its relative position
     */

    private double customScale = 1;


    public double xRotation = 0;
    public double yRotation = 0;
    public double zRotation = 0;

    public IEasyGuiScreen screen;
    public ContainerRenderable parent;
    public List<ContainerRenderable> children = new ArrayList<>();

    public String id = "";
    public List<String> classList = new ArrayList<>();

    public BaseRenderable(IEasyGuiScreen screen){
        this.screen = screen;
        screen.register(this);
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public List<String> getClassList() {
        return List.copyOf(classList);
    }
    @Override
    public void setID(String id) {
        this.id = id;
        screen.childIdSet(this,id);
    }

    @Override
    public void addClass(String className) {
        classList.add(className);
        screen.childClassAdded(this,className);
    }

    @Override
    public void removeClass(String className) {
        classList.remove(className);
        screen.childClassRemoved(this,className);

    }

    public void setCullBorder(int cullBorder) {
        this.cullBorder = cullBorder;
    }

    public int getCullBorder() {
        return cullBorder;
    }

    @Override
    public void setCull(boolean cull) {
        this.cull = cull;
    }

    @Override
    public boolean shouldCull() {
        return false;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }



    public void setVisibleWidth(int visibleWidth) {
        this.visibleWidth = visibleWidth;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setVisibleHeight(int visibleHeight) {
        this.visibleHeight = visibleHeight;
    }

    @Override
    public int getVisibleHeight() {
        return visibleHeight;
    }

    @Override
    public int getVisibleWidth() {
        return visibleWidth;
    }

    @Override
    public void setRotation(double x, double y, double z) {
        this.xRotation = x;
        this.yRotation = y;
        this.zRotation = z;
    }

    @Override
    public double getRotationX() {
        return xRotation;
    }

    @Override
    public double getRotationY() {
        return yRotation;
    }

    @Override
    public double getRotationZ() {
        return zRotation;
    }

    @Override
    public int getX(){
        return x;
    }
    @Override
    public int getY(){
        return y;
    }

    @Override
    public int getVisibleX() {
        return visibleX;
    }

    @Override
    public int getVisibleY() {
        return visibleY;
    }

    /**
     * already includes the minecraft gui scale
     */
    @Override
    public double getGlobalScaledX() {

        if(getParent() != null){
            return getX()*getParent().getTotalScaleFactorX() + getParent().getGlobalScaledX();
        }
        return getX();
    }



    /**
     * already includes the minecraft gui scale
     */
    @Override
    public double getGlobalScaledY() {
        if(getParent() != null) return getY()*getParent().getTotalScaleFactorY() + getParent().getGlobalScaledY();
        return getY();
    }

    @Override
    public double getGlobalScaledVisibleX() {
        if(getParent() != null){
            return getVisibleX()*getParent().getTotalScaleFactorX() + getParent().getGlobalScaledX();
        }
        return getVisibleX();
    }

    @Override
    public double getGlobalScaledVisibleY() {
        if(getParent() != null) return getVisibleY()*getParent().getTotalScaleFactorY() + getParent().getGlobalScaledY();
        return getVisibleY();
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
        if(parent == null) return true;
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
    @Override
    public double getScale(){
        if(useCustomScaling){
            return customScale;
        }
        return 1;
    }
    @Override
    public double getScaleX(){
        return getScale();
    }
    @Override
    public double getScaleY(){
        return getScale();
    }

    @Override
    public IEasyGuiScreen getScreen() {
        return screen;
    }

    @Override
    public void setRenderScale(GuiGraphics guiGraphics){


        ScreenRectangle scissorRect =  guiGraphics.scissorStack.stack.peek();
        visibleHeight = getHeight();
        visibleWidth = getWidth();
        visibleX = getX();
        visibleY = getY();

        double x = getGlobalScaledX();
        double y = getGlobalScaledY();
        int width = getScaledWidth();
        int height = getScaledHeight();


        //System.out.println("calculating things "+ getClass().getName());
        //TODO add safety for if parent is null
        if(scissorRect != null){
            //may not be the most efficient
            //if x or y are outside of box+length then it is not visible

            if(x > scissorRect.position().x()+scissorRect.width()
                || y>scissorRect.position().y()+scissorRect.height()){
                visibleWidth = 0;
                visibleHeight = 0;
            }else if(x+width < scissorRect.position().x()
                    || y+height < scissorRect.position().y() ){
                //y+height or x+width outside of area so none visible
                visibleHeight = 0;
                visibleWidth = 0;
            } else{
                if(x+width>scissorRect.position().x()+scissorRect.width()) width = (int) (scissorRect.position().x()+scissorRect.width()-x);
                if(y+height>scissorRect.position().y()+scissorRect.height()) height = (int) (scissorRect.position().y()+scissorRect.height()-y);

                if(y<scissorRect.position().y()){
                    height = (int) (height-(scissorRect.position().y()-y));
                    y = scissorRect.position().y();
                }
                if(x<scissorRect.position().x()){
                    width = (int) (width-(scissorRect.position().x()-x));
                    x = scissorRect.position().x();
                }

                visibleX = getParent().screenToLocalX(x);
                visibleY = getParent().screenToLocalY(y);

                visibleWidth = (int) (width/getTotalScaleFactorX());
                //System.out.println(getHeight());
                visibleHeight = (int) (height/getTotalScaleFactorY());
                //System.out.println(visibleHeight);
            }
        }

        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(getX(),getY(),0);
        Quaternionf rotation = new Quaternionf()
                .rotateZ((float) Math.toRadians(getRotationZ()))
                .rotateY((float) Math.toRadians(getRotationY()))
                .rotateX((float) Math.toRadians(getRotationX()));
        guiGraphics.pose().rotateAround(rotation, (float) getWidth() /2, (float) getHeight() /2,1);
        if(useCustomScaling){
            guiGraphics.pose().scale((float) getScaleX(), (float) getScaleY(),1);
        }

        if(cull) guiGraphics.enableScissor(
                (int) (getGlobalScaledX()-cullBorder),
                (int) (getGlobalScaledY()-cullBorder),
                (int) (getGlobalScaledX()+getScaledWidth()+cullBorder),
                (int) (getGlobalScaledY()+getScaledHeight()+cullBorder));
    }
    @Override
    public void resetRenderScale(GuiGraphics guiGraphics){
        guiGraphics.pose().popPose();
        if(cull) guiGraphics.disableScissor();
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




    @Override
    public void remove() {
        screen.unregister(this);
        if(getParent() != null){
            parent.getChildren().remove(this);
        }
    }

    public void setCustomScale(double customScale) {
        this.customScale = customScale;
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

    public double getCustomScale() {
        return customScale;
    }

    @Override
    public List<ContainerRenderable> getChildren() {
        return children;
    }

    @Override
    public ContainerRenderable getParent() {
        return parent;
    }

    //TODO not in development right now
    public abstract static class Serializer{

        public int parseX(JsonElement element){
            //first try to parse as int
            try{
                return element.getAsInt();
            }catch (Exception e){
                return 0;
            }
        }
        public int parseY(JsonElement element){return 0;}
        public abstract ContainerRenderable readJson(JsonObject obj);

    }

}
