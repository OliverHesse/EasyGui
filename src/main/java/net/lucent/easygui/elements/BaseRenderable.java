package net.lucent.easygui.elements;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.lucent.easygui.elements.inventory.DisplaySlot;
import net.lucent.easygui.interfaces.ContainerRenderable;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.lucent.easygui.interfaces.complex_events.Sticky;
import net.lucent.easygui.templating.actions.Action;
import net.lucent.easygui.util.math.BoundChecker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

/**
 * handles all the scaling stuff
 */

public abstract class BaseRenderable implements ContainerRenderable, Sticky {

    private int renderedX;
    private int renderedY;


    BoundChecker.Rec2d cullRegion;

    public Matrix4f positionTransform;
    public Matrix4f transform;

    private int x;
    private int y;
    private int z;
    private int visibleX;
    private int visibleY;

    public int width;
    public int height;


    public int visibleWidth;
    public int visibleHeight;

    public boolean active = true;
    public boolean visible = true;
    public boolean focused = false;
    public boolean sticky = false;
    public Action tickAction;
    public Action resizeAction;
    public Action guiChangeAction;
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

    public BaseRenderable(){

    }


    @Override
    public void setTickAction(Action action){
        this.tickAction = action;
    }
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
        return cull;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public void setWidth(int width) {
        this.width = width;
    }



    public void setVisibleWidth(int visibleWidth) {
        this.visibleWidth = visibleWidth;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
    }

    public void setVisibleHeight(int visibleHeight) {
        this.visibleHeight = visibleHeight;
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

    public int getZ() {
        return z;
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
        //return (new Vector4f(0,0,0,1).mul(getTotalPositionTransformation())).x()*getApplicableMinecraftScale()/getTotalCustomScaling();
    }
    //return (new Vector4f(0,0,0,1).mul(getTotalPositionTransformation())).x();
    /*
         if(getParent() != null){
            return getX()*getParent().getTotalScaleFactorX() + getParent().getGlobalScaledX();
        }
        return getX();
     */

    /**
     * already includes the minecraft gui scale
     */
    @Override
    public double getGlobalScaledY() {
        if(getParent() != null){
            return getY()*getParent().getTotalScaleFactorY() + getParent().getGlobalScaledY();
        }
        return getY();
        //return (new Vector4f(0,0,0,1).mul(getTotalPositionTransformation())).y()*getApplicableMinecraftScale()/getTotalCustomScaling();
    }



    @Override
    public void setX(int x){
        this.x = x;
    }
    @Override
    public void setY(int y){
        this.y = y;
    }
    public void setZ(int z){
        this.z = z;
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

    // a bit buggy rn
    public boolean isCulled(){
        if(getActiveCullRegion() == null) return false;
        if(getTransform() == null) return false;
        Vector3f p1 = getTransform().transformPosition(new Vector3f(0,0,0));;
        Vector3f p2 = getTransform().transformPosition(new Vector3f(getWidth(),0,0));
        Vector3f p3 = getTransform().transformPosition(new Vector3f(getWidth(),getHeight(),0));
        Vector3f p4 = getTransform().transformPosition(new Vector3f(0,getHeight(),0));
        if(!(this instanceof DisplaySlot)) return false;

        getActiveCullRegion().print();

        new BoundChecker.Rec2d(
                new BoundChecker.Vec2(p1),
                new BoundChecker.Vec2(p2),
                new BoundChecker.Vec2(p3),
                new BoundChecker.Vec2(p4)).print();
        return !BoundChecker.containsRec(getActiveCullRegion(),new BoundChecker.Rec2d(
                new BoundChecker.Vec2(p1),
                new BoundChecker.Vec2(p2),
                new BoundChecker.Vec2(p3),
                new BoundChecker.Vec2(p4)));
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
    public void setScreen(IEasyGuiScreen screen) {
        this.screen = screen;
        screen.register(this);
    }

    @Override
    public void setRenderScale(GuiGraphics guiGraphics){





        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(getX(),getY(),0);

        this.positionTransform = new Matrix4f(guiGraphics.pose().last().pose());

        Quaternionf rotation = new Quaternionf()
                .rotateZ((float) Math.toRadians(getRotationZ()))
                .rotateY((float) Math.toRadians(getRotationY()))
                .rotateX((float) Math.toRadians(getRotationX()));
        guiGraphics.pose().rotateAround(rotation, (float) getWidth() /2, (float) getHeight() /2,1);
        if(useCustomScaling){
            guiGraphics.pose().scale((float) getScaleX(), (float) getScaleY(),1);
        }

        this.transform = new Matrix4f(guiGraphics.pose().last().pose());

        if(shouldCull()){

            guiGraphics.enableScissor(
                    (getGlobalPoint().x - cullBorder),
                    (getGlobalPoint().y - cullBorder),
                    (getGlobalHeightWidthPoint().x + cullBorder),
                    (getGlobalHeightWidthPoint().y + cullBorder));

            this.cullRegion = new BoundChecker.Rec2d(
                    getGlobalPoint(),
                    getGlobalWidthPoint(),
                    getGlobalHeightWidthPoint(),
                    getGlobalHeightPoint());

        }
    }

    public void setCullRegion(BoundChecker.Rec2d cullRegion) {
        this.cullRegion = cullRegion;
    }

    @Override
    public int getCullBorderWidth() {
        return cullBorder;
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
        if(!isVisible()) return;
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

    @Override
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

    @Override
    public void tick() {
        if(tickAction != null) tickAction.accept(this);
    }

    @Override
    public boolean isSticky() {
        return sticky;
    }

    @Override
    public void setSticky(boolean sticky) {
        this.sticky = sticky;
    }


    public BoundChecker.Vec2 getGlobalPoint(){
        Vector3f position =   getTransform().transformPosition(new Vector3f(0,0,0));
        return new BoundChecker.Vec2((int) position.x, (int) position.y);
    }
    public BoundChecker.Vec2 getGlobalWidthPoint(){
        Vector3f position =   getTransform().transformPosition(new Vector3f(getWidth(),0,0));
        return new BoundChecker.Vec2((int) position.x, (int) position.y);
    }
    public BoundChecker.Vec2  getGlobalHeightPoint(){
        Vector3f position =   getTransform().transformPosition(new Vector3f(0,getHeight(),0));
        return new BoundChecker.Vec2((int) position.x, (int) position.y);
    }
    public BoundChecker.Vec2 getGlobalHeightWidthPoint(){
        Vector3f position = getTransform().transformPosition(new Vector3f(getWidth(),getHeight(),0));
        return new BoundChecker.Vec2((int) position.x, (int) position.y);
    }

    public Matrix4f getPositionTransform() {
        return positionTransform;
    }

    public Matrix4f getTransform() {
        return transform;
    }

    public BoundChecker.Rec2d getCullRegion() {
        return cullRegion;
    }
    public BoundChecker.Rec2d getActiveCullRegion(){
        if(cullRegion != null || getParent() == null) return cullRegion;
        return getParent().getActiveCullRegion();
    }

    @Override
    public void onResize(int oldWidth, int oldHeight, double oldScale) {
        Sticky.super.onResize(oldWidth, oldHeight, oldScale);
        if(resizeAction != null) resizeAction.accept(this,oldWidth,oldHeight,oldScale);
    }

    @Override
    public void onGuiScaleChanged(double oldScale) {
        Sticky.super.onGuiScaleChanged(oldScale);
        if(guiChangeAction != null) guiChangeAction.accept(this,oldScale);
    }
}
