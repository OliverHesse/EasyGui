package net.lucent.easygui.elements.containers.list_containers;

import net.lucent.easygui.elements.containers.panels.Panel;
import net.lucent.easygui.interfaces.ContainerRenderable;
import net.lucent.easygui.interfaces.IEasyGuiScreen;

import java.util.List;
//TODO update to take into account child scaled size
public class FixedSizedListContainer extends Panel {

    private boolean AS_ROWS = true;
    private int GAP = 2;
    public FixedSizedListContainer(IEasyGuiScreen easyGuiScreen, int x, int y, int width, int height) {
        super(easyGuiScreen, x, y, width, height);
    }
    public FixedSizedListContainer(IEasyGuiScreen easyGuiScreen, int x, int y, int width, int height,boolean as_rows) {
        super(easyGuiScreen, x, y, width, height);
        this.AS_ROWS = as_rows;
    }

    public void setGap(int gap){
        this.GAP = gap;
    }
    public int getGap(){return GAP;}
    /** TODO redo child position when this is called
     * true = rows
     * false = columns
     *
     *
     *
     **/
    public void setAlignment(boolean alignment){
        this.AS_ROWS = alignment;
        updateChildrenAlignment();
    }
    public boolean getAlignment(){
        return AS_ROWS;
    }
    public int getUsedLength(int index){
        if(index < 0) return 0;
        int length = 0;
        for(int i = 0; i<index;i++){
            ContainerRenderable child = getChildren().get(i);
            length += AS_ROWS ? (int) (child.getHeight() * child.getCustomScale() + getGap()) : (int) (child.getWidth() * child.getCustomScale() + getGap());

        }
        return length;
    }

    public void updateChildrenAlignment(){
        for(int i = 0;i < getChildren().size(); i++){
            if(AS_ROWS) getChildren().get(i).setY(getUsedLength(i));
            else getChildren().get(i).setX(getUsedLength(i));
        }
    }
    @Override
    public void addChild(ContainerRenderable child) {
        child.setX(0);
        child.setY(0);
        if(AS_ROWS) child.setY(getUsedLength(getChildren().size()));
        else child.setX(getUsedLength(getChildren().size()));
        super.addChild(child);
    }

    @Override
    public void clearChildRemoveBuffer() {
        super.clearChildRemoveBuffer();
        updateChildrenAlignment();
    }
}
