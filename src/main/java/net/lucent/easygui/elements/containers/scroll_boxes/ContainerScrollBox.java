package net.lucent.easygui.elements.containers.scroll_boxes;

import net.lucent.easygui.elements.inventory.DisplaySlot;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.minecraft.world.Container;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ContainerScrollBox extends AbstractScrollBox{

    public int slots;
    public int rows;
    public int displayRows;
    public ContainerScrollBox(IEasyGuiScreen screen, int x, int y, int slots,int slotsPerRow,int displayRows, Container container){
        super(screen,x,y,0,0);
        setWidth(slotsPerRow*18);
        generateSlots(slots,container);
        setScrollBarVisible(false);
        setHeight(displayRows*18);
        rows = (slots/slotsPerRow)+1;
        this.displayRows = displayRows;
    }
    public void generateSlots(int slots,Container container){
        int slotsPerRow = width/18;
        setHeight(rows*18);
        for(int i=0;i<slots;i++){
            int row = i/slotsPerRow;
            int column = i%slotsPerRow;

            DisplaySlot slot = new DisplaySlot(getScreen(),column*18+1,row*18+1,16,16,i, container);
            addChild(slot);
        }
    }

    @Override
    public void onMouseScroll(double mouseX, double mouseY, double scrollX, double scrollY) {
        if(isMouseOver(mouseX,mouseY)){
            setYOffset(getScrollAmount(yOffset,-scrollY*18,getScrollHeight()));

        }

        if(mouseScrollAction != null) mouseScrollAction.accept(this,mouseX,mouseY,scrollX,scrollY);
    }

    @Override
    public double getScrollHeight() {
        return Math.max(0,(rows-displayRows-1)*18);
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
