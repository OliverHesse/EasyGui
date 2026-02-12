package net.lucent.easygui.gui;

import net.lucent.easygui.gui.events.EasyEvents;
import net.lucent.easygui.gui.events.EventPhase;
import net.lucent.easygui.gui.layout.positioning.Positioning;
import net.lucent.easygui.gui.layout.positioning.context.PositioningContexts;
import net.lucent.easygui.gui.layout.positioning.rules.PositioningRules;
import net.lucent.easygui.gui.layout.transform.Transform;
import net.lucent.easygui.gui.listeners.IEasyEventListener;
import net.lucent.easygui.util.BoundChecker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.phys.Vec2;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RenderableElement {

    private Positioning positioning;
    private Transform transform;
    private RenderableElement parent;
    private UIFrame uiFrame;

    private String id;
    private final List<String> classes = new ArrayList<>();

    private final HashMap<String, List<IEasyEventListener>> bubbleListeners = new HashMap<>();
    private final HashMap<String, List<IEasyEventListener>> captureListeners = new HashMap<>();

    private int width;
    private int height;

    private boolean active = true;
    private boolean visible  =true;

    private boolean cull = false;

    private int zIndex;
    private final List<RenderableElement> children = new ArrayList<>();
    private final List<RenderableElement> childAdditionBuffer = new ArrayList<>();
    private final List<RenderableElement> childRemovalBuffer = new ArrayList<>();

    private boolean canBeFocused;

    public RenderableElement(UIFrame frame){
        this.uiFrame = frame;
        positioning =  new Positioning(PositioningContexts.ABSOLUTE, PositioningRules.START,this);
        transform  = new Transform(this);
        addEventListener(EasyEvents.FRAME_DIMENSIONS_CHANGE_EVENT,(easyEvent)->{
            System.out.println("event resize revcieved");
            positioning.updatePositionMatrix();
        });
    }
    public RenderableElement(UIFrame frame,int x, int y){
        this(frame);
        positioning.setX(x);
        positioning.setY(y);
    }

    //================ SHAPE =================
    public int getWidth(){
        return width;
    }
    public void setWidth(int width){
        this.width = width;
    }
    public int getHeight(){
        return height;
    }
    public void setHeight(int height){
        this.height = height;
    }

    public Vec2 getGlobalPoint(){
        Vector3f position = getCompletePositioningMatrix().transformPosition(new Vector3f(0,0,0));
        return new Vec2(position.x,position.y);
    }
    //the point x+width,y+height;
    public Vec2 getGlobalCornerPoint(){
        Matrix4f point = new Matrix4f(getCompletePositioningMatrix()).translate(getWidth(),getHeight(),0);
        Vector3f position = point.mul(getTransformMatrix()).transformPosition(new Vector3f(0,0,0));
        return new Vec2(position.x,position.y);
    }
    public boolean isPointBounded(GuiGraphics guiGraphics,double x,double y){
        if(!guiGraphics.containsPointInScissor((int) x, (int) y)) return false;
        return isPointBounded(x,y);
    }
    public boolean isPointBounded(double x,double y){

        Vec2 p1 = getGlobalPoint();

        Vec2 p2 = getGlobalCornerPoint();

        Vec2 mousePos = new Vec2((float) x,(float)y);
        return mousePos.x > p1.x && mousePos.y > p1.y && mousePos.x < p2.x && mousePos.y < p2.y;
    }
    public boolean isFullyCulled(GuiGraphics guiGraphics){
        Vec2 point1 = getGlobalPoint();
        Vec2 point2 = getGlobalCornerPoint();

        boolean corner1 = guiGraphics.containsPointInScissor((int) point1.x, (int) point1.y);
        boolean corner2 = guiGraphics.containsPointInScissor((int) point1.x, (int) point2.y);
        boolean corner3 = guiGraphics.containsPointInScissor((int) point2.x, (int) point2.y);
        boolean corner4 = guiGraphics.containsPointInScissor((int) point2.x, (int) point1.y);
        return corner1 || corner2 || corner3 || corner4;
    }
    //================= TRANSFORMATION MATRICES ===================
    public  Positioning getPositioning(){return positioning;}
    public Transform getTransform(){return this.transform;}

    //returns total transform including scale and rotation
    public Matrix4f getTransformMatrix(){
        return transform.getTransformMatrix();
    }
    //returns only positioning transform
    public Matrix4f getPositioningMatrix(){
        return positioning.getPositionMatrix();
    }
    public Matrix4f getCompletePositioningMatrix(){return positioning.getCompletePositionMatrix();}

    /**
     *
     * @param positioning new Positioning
     * @param transferCoordinates transfer old coordinates into new one
     */
    public void setPositioning(Positioning positioning,boolean transferCoordinates){
        positioning.setElement(this);
        if(transferCoordinates){
            positioning.setFromRawX(this.positioning.getRawX());
            positioning.setFromRawY(this.positioning.getRawY());
        }
        this.positioning = positioning;

    }
    public void setTransform(Transform transform){
        transform.setElement(this);
        this.transform = transform;
    }

    public Matrix4f globalToLocalMatrix(){
        return new Matrix4f(getCompletePositioningMatrix()).mul(getTransformMatrix()).invert();
    }
    public Vec2 globalToLocalPositionPoint(float mouseX, float mouseY){
        Vector3f point = (new Matrix4f(getCompletePositioningMatrix())).invert().transformPosition(new Vector3f(mouseX,mouseY,0));
        return new Vec2(point.x,point.y);
    }
    public Vec2 globalToLocalPoint(float mouseX, float mouseY){
        Vector3f point =  (new Matrix4f(getCompletePositioningMatrix().mul(getTransformMatrix()))).invert().transformPosition(
                new Vector3f(mouseX,mouseY,0)
        );
        return new Vec2(point.x,point.y);
    }

    //================= GETTERS =======================
    public RenderableElement getParent() {
        return parent;
    }
    public UIFrame getUiFrame(){
        return uiFrame;
    }
    public boolean isActive(){return this.active;}
    public boolean isVisible(){return this.visible;}


    public int getTotalZIndex(){
        RenderableElement element = getParent();
        int finalV = getZIndex();
        while (element != null){
            finalV += element.getZIndex();
            element = element.getParent();
        }
        return finalV;
    }

    public int getZIndex(){return this.zIndex;}

    public String getId() {
        return id;
    }
    public List<String> getClasses(){
        return classes;
    }

    public boolean isFocusable(){
        return canBeFocused;
    }
    public boolean isFocused(){
        return isFocusable() && getUiFrame().getFocusedElement() == this;
    }
    public boolean shouldCull(){
        return cull;
    }
    //================= SETTERS =======================
    public void setUiFrame(UIFrame frame){this.uiFrame=frame;}
    public void setActive(boolean active){this.active = active;}
    public void setVisible(boolean visible){this.visible =visible;}

    public void setZIndex(int zIndex){this.zIndex = zIndex;}

    public void clearClasses(){
        for(String classId : getClasses()){
            getUiFrame().removeClass(classId,this);
        }
        getClasses().clear();
    }
    public void setId(String id){
        getUiFrame().setId(id,this);
        this.id = id;
    }

    public void removeClass(String classId){
        getUiFrame().removeClass(classId,this);
        getClasses().remove(classId);
    }

    public void addClasses(List<String> classIds){
        for(String classId : classIds){
            getUiFrame().addClass(classId,this);
        }
        getClasses().addAll(classIds);

    }

    public void addClass(String classId){
        getUiFrame().addClass(classId,this);
        getClasses().add(classId);
    }

    public void setFocused(boolean focus){
        getUiFrame().trySetFocus(this,focus);
    }
    public void setShouldCull(boolean shouldCull){
        cull = shouldCull;
    }
    //================= OTHER =========================




    //================== RUNTIME ======================
    protected void run(GuiGraphics guiGraphics,int mouseX,int mouseY, float partialTick){
        clearChildAdditionBuffer();
        clearChildRemovalBuffer();
        if(!isActive()) return;
        guiGraphics.pose().pushPose();
        guiGraphics.pose().mulPose(getPositioningMatrix());

        guiGraphics.pose().mulPose(getTransformMatrix());
        renderTick(guiGraphics,mouseX,mouseY,partialTick);
        setVisible(isFullyCulled(guiGraphics));
        if(!isVisible()) return;

        guiGraphics.pose().translate(0,0,getZIndex());
        render(guiGraphics,mouseX,mouseY,partialTick);

        if(shouldCull()) createCullRegion(guiGraphics);
        runChildren(guiGraphics,mouseX,mouseY,partialTick);
        if(shouldCull()) disableCullRegion(guiGraphics);

        guiGraphics.pose().popPose();
    }

    //run event when not active
    public void renderTick(GuiGraphics guiGraphics,int mouseX,int mouseY, float partialTick){}
    //run only when active
    public void render(GuiGraphics guiGraphics,int mouseX,int mouseY, float partialTick){}
    public void runChildren(GuiGraphics guiGraphics,int mouseX,int mouseY, float partialTick){
        for(RenderableElement child:getChildren()){
            child.run(guiGraphics,mouseX,mouseY,partialTick);
        }
    }

    public void onRemove(){}

    public void createCullRegion(GuiGraphics guiGraphics){
        Vec2 p1 = getGlobalPoint();
        Vec2 p2 = getGlobalCornerPoint();
        guiGraphics.enableScissor((int) p1.x, (int) p1.y, (int) p2.x, (int) p2.y);
    }
    public void disableCullRegion(GuiGraphics guiGraphics){
        guiGraphics.disableScissor();
    }
    //====================== EVENTS ============================
    public void addEventListener(String event, IEasyEventListener eventListener, EventPhase phase){
        if(phase == EventPhase.CAPTURE){
            captureListeners.computeIfAbsent(event,key -> new ArrayList<>()).addFirst(eventListener);
        }else{
            bubbleListeners.computeIfAbsent(event,key -> new ArrayList<>()).addFirst(eventListener);
        }
    }
    public void addEventListener(String event,IEasyEventListener eventListener){
        addEventListener(event,eventListener,EventPhase.BUBBLE);
    }

    public List<IEasyEventListener> getCaptureListeners(String event){
        return captureListeners.containsKey(event) ? captureListeners.get(event) : List.of();
    }
    public List<IEasyEventListener> getBubbleListeners(String event){
        return bubbleListeners.containsKey(event) ? bubbleListeners.get(event) : List.of();
    }
    //====================== CHILDREN ==========================

    public boolean hasChildren(){
        return !children.isEmpty() || !childAdditionBuffer.isEmpty();
    }

    public void addChild(RenderableElement element){
        element.parent = this;
        childAdditionBuffer.add(element);
    }
    public void clearChildAdditionBuffer(){
        children.addAll(childAdditionBuffer);
        childAdditionBuffer.clear();
    }

    public void clearChildRemovalBuffer(){
        for(RenderableElement element : childRemovalBuffer){
            element.onRemove();
            element.removeChildren();
        }
        childRemovalBuffer.clear();
    }

    public void removeChildren(){
        childRemovalBuffer.addAll(children);
    }

    public List<RenderableElement> getChildren(){
        ArrayList<RenderableElement> arrayList = new ArrayList<>();
        arrayList.addAll(children);
        arrayList.addAll(childAdditionBuffer);
        return arrayList;
    }

    //TODO see if i can make more efficient
    //order by z index. if z index match order by position in original list
    public List<RenderableElement> getPriorityOrderedChildren(){
        List<RenderableElement> orderedList = new ArrayList<>();
        for (RenderableElement element : getChildren()){
            //try insert into list
            if(orderedList.isEmpty()) {
                orderedList.add(element);
                continue;
            }
            for(int i =0; i<orderedList.size();i++){
                if(element.getTotalZIndex() > orderedList.get(i).getTotalZIndex()){
                    orderedList.add(i,element);
                    break;
                }
            }
            //it has reached end of list, so it has the lowest priority
            orderedList.add(element);
        }
        return orderedList;
    }


    /*
        used to get the top most rendered element at a point.
        mainly used for mouse click, move, release and pressed events
     */
    public RenderableElement getHighestPriorityChildWithBoundedPoint(GuiGraphics guiGraphics,int x,int y){

        RenderableElement element = null;
        for(RenderableElement child : getPriorityOrderedChildren()){
            RenderableElement result = child.getHighestPriorityChildWithBoundedPoint(guiGraphics,x,y);
            if(result != null && (element == null || element.zIndex < result.zIndex)){
                element = result;
            }
        }
        //does not matter if element has higher z index because children have an auto +1
        if( element == null && isPointBounded(guiGraphics,x,y)){
            return this;
        }
        return element;
    }
}


