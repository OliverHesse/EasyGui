package net.lucent.easygui.gui;

import net.lucent.easygui.gui.layout.positioning.Positioning;
import net.lucent.easygui.gui.layout.transform.Transform;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;

public class RenderableElement {

    public Positioning positioning;
    public Transform transform;
    public RenderableElement parent;
    public UIFrame uiFrame;

    private int width;
    private int height;

    private boolean active;
    private boolean focused;
    private boolean visible;

    private int zIndex;
    private final List<RenderableElement> children = new ArrayList<>();
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

    //TODO
    public boolean isPointBounded(int x,int y){
        return false;
    }
    //================= TRANSFORMATION MATRICES ===================
    //returns total transform including scale and rotation
    public Matrix4f getTransform(){
        return transform.getTransformMatrix();
    }
    //returns only positioning transform
    public Matrix4f getPositioningTransform(){
        return positioning.getPositionMatrix();
    }

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

    //================= OTHER =========================
    public RenderableElement getParent() {
        return parent;
    }
    public UIFrame getUiFrame(){
        return uiFrame;
    }
    public boolean isActive(){return this.active;}
    public boolean isVisible(){return this.visible;}
    public boolean isFocused(){return this.focused;}

    public void setActive(boolean active){this.active = active;}
    public void setVisible(boolean visible){this.visible =visible;}
    public void setFocused(boolean focused){this.focused = focused;}

    public void setZIndex(int zIndex){this.zIndex = zIndex;}

    public int getZIndex(){return this.zIndex;}
    //================== RUNTIME ======================
    protected void run(GuiGraphics guiGraphics,int mouseX,int mouseY, float partialTick){
        if(!isActive()) return;
        renderTick(guiGraphics,mouseX,mouseY,partialTick);
        if(!isVisible()) return;
        render(guiGraphics,mouseX,mouseY,partialTick);
        runChildren(guiGraphics,mouseX,mouseY,partialTick);
    }

    public void renderTick(GuiGraphics guiGraphics,int mouseX,int mouseY, float partialTick){}
    public void render(GuiGraphics guiGraphics,int mouseX,int mouseY, float partialTick){}
    public void runChildren(GuiGraphics guiGraphics,int mouseX,int mouseY, float partialTick){}

    //====================== CHILDREN ==========================

    public boolean hasChildren(){
        return !children.isEmpty();
    }

    //TODO see if i can make more efficient
    //order by z index. if z index match order by position in original list
    public List<RenderableElement> getPriorityOrderedChildren(){
        List<RenderableElement> orderedList = new ArrayList<>();
        for (RenderableElement element : children){
            //try insert into list
            if(orderedList.isEmpty()) {
                orderedList.add(element);
                continue;
            }
            for(int i =0; i<orderedList.size();i++){
                if(element.getZIndex() > orderedList.get(i).getZIndex()){
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
    public RenderableElement getHighestPriorityChildWithBoundedPoint(int x,int y){

        RenderableElement element = null;
        for(RenderableElement child : getPriorityOrderedChildren()){
            RenderableElement result = child.getHighestPriorityChildWithBoundedPoint(x,y);
            if(result != null && (element == null || element.zIndex < result.zIndex)){
                element = result;
            }
        }
        //does not matter if element has higher z index because children have an auto +1
        if(isPointBounded(x,y) && element == null){
            return this;
        }
        return element;
    }
}


