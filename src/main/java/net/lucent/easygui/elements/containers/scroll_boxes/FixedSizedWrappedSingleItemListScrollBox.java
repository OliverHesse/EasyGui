package net.lucent.easygui.elements.containers.scroll_boxes;

import net.lucent.easygui.interfaces.ContainerRenderable;
import net.lucent.easygui.interfaces.IEasyGuiScreen;

import java.util.List;

//for now only works as rows not columns
//scrolls like container scroll box
public class FixedSizedWrappedSingleItemListScrollBox extends AbstractScrollBox{

    public double rows;
    public int displayRows;
    public int slotHeight;
    public int slotWidth;
    public int slotGap;
    public FixedSizedWrappedSingleItemListScrollBox(IEasyGuiScreen screen,int x,int y,int width,int visibleRows,int slotWidth,int slotHeight,int slotGap){
        super(screen,x,y,width,visibleRows*(slotHeight+slotGap));
        this.slotHeight = slotHeight;
        this.slotWidth = slotWidth;
        this.slotGap = slotGap;
    }

    public void positionAllChildren(){
        List<ContainerRenderable> children = getChildren();
        int slotsPerRow = (getWidth()/(slotWidth+slotGap));
        rows = Math.ceil(((double) children.size() / slotsPerRow));
        double yOffset = this.yOffset;
        this.yOffset = 0;
        for(int i = 0;i< children.size();i++){
            int row = i/slotsPerRow;
            int column = i%slotsPerRow;
            children.get(i).setX(column*(slotGap+slotWidth));
            children.get(i).setY(row*(slotGap+slotHeight));
        }
        setYOffset(yOffset);
    }

    @Override
    public boolean clearChildBuffer() {
        boolean result = super.clearChildBuffer();
        if(result) positionAllChildren();
        return result;
    }

    @Override
    public void onMouseScroll(double mouseX, double mouseY, double scrollX, double scrollY) {
        if(isMouseOver(mouseX,mouseY)){
            setYOffset(getScrollAmount(yOffset,-scrollY*(slotHeight+slotGap),getScrollHeight()));

        }

        if(mouseScrollAction != null) mouseScrollAction.accept(this,mouseX,mouseY,scrollX,scrollY);
    }

    @Override
    public double getScrollHeight() {
        return Math.max(0,(rows-displayRows-1)*(slotHeight+slotGap));
    }

    @Override
    public double getScrollWidth() {
        return 0;
    }

    @Override
    public double getScrollRate() {
        return 0;
    }
}
