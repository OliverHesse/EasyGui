package net.lucent.easygui.elements.containers.list_containers;

import net.lucent.easygui.interfaces.ContainerRenderable;
import net.lucent.easygui.interfaces.IEasyGuiScreen;

public class DynamicSizedListContainer extends FixedSizedListContainer{
    public DynamicSizedListContainer(IEasyGuiScreen easyGuiScreen, int x, int y) {
        super(easyGuiScreen, x, y, 0,0);
    }

    public DynamicSizedListContainer(IEasyGuiScreen easyGuiScreen, int x, int y, boolean as_rows) {
        super(easyGuiScreen, x, y, 0, 0, as_rows);
    }

    @Override
    public void updateChildrenAlignment() {
        super.updateChildrenAlignment();
        updateDimensions();

    }
    public void updateDimensions(){
        int length = getUsedLength(getChildren().size());
        int thickness = getThickness();
        if(this.getAlignment()){
            this.setHeight(length);
            this.setWidth(thickness);
        }else {
            this.setWidth(length);
            this.setHeight(thickness);
        }
    }
    public int getThickness(){
        int runningMax = 0;
        for (ContainerRenderable renderable:getChildren()){
            int value =  getAlignment() ? renderable.getHeight() : renderable.getWidth();
            if(value > runningMax) runningMax = value;
        }
        return runningMax;
    }

    @Override
    public boolean clearChildBuffer() {
        if(!super.clearChildBuffer()){
            updateDimensions();
            return false;
        }
        return true;

    }
}
