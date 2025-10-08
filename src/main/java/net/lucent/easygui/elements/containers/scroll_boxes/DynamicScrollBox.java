package net.lucent.easygui.elements.containers.scroll_boxes;

import com.google.gson.JsonObject;
import net.lucent.easygui.elements.other.SquareRenderable;
import net.lucent.easygui.interfaces.ContainerRenderable;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.lucent.easygui.templating.IRenderableDeserializer;
import net.lucent.easygui.templating.deserializers.SquareRenderableDeserializer;
import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;
@OnlyIn(Dist.CLIENT)
//TODO update after moving child elements
//TODO update when children are changed
public class DynamicScrollBox extends AbstractScrollBox {
    private double  maxScrollY;
    private double maxScrollX;

    public DynamicScrollBox(){}
    public DynamicScrollBox(IEasyGuiScreen easyGuiScreen, int x, int y, int width, int height) {
        super(easyGuiScreen, x, y, width, height);
    }
    //finds the child that xPos + width is the greatest. does not include child children
    public int getMaxXOffset(){
        int maxXOffset = 0;
        for(ContainerRenderable containerRenderable : getChildren()){
            int xOffset = (int) (containerRenderable.getX()+containerRenderable.getWidth()*containerRenderable.getCustomScale());
            if(xOffset > maxXOffset) maxXOffset = xOffset;

        }
        return maxXOffset;
    }

    //finds the child that yPos + height is the greatest. does not include child children
    public int getMaxYOffset(){
        int maxYOffset = 0;
        for(ContainerRenderable containerRenderable : getChildren()){
            int yOffset = (int) (containerRenderable.getX()+containerRenderable.getWidth()*containerRenderable.getCustomScale());
            if(yOffset > maxYOffset) maxYOffset = yOffset;

        }
        return maxYOffset;
    }

    public void updateScrollData(){
        int maxYOffset = getMaxYOffset();
        int maxXOffset = getMaxXOffset();
        if(maxYOffset-getInnerHeight() > getScrollHeight()) maxScrollY = Math.max(0,maxYOffset-getInnerHeight());
        if(maxXOffset-getInnerWidth() > getScrollWidth()) maxScrollX = Math.max(0,maxXOffset-getInnerWidth());
    }

    @Override
    public void renderSelf(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderSelf(guiGraphics, mouseX, mouseY, partialTick);
        updateScrollData();
    }

    @Override
    public double getScrollHeight() {
        return maxScrollY;
    }

    @Override
    public double getScrollWidth() {

        return maxScrollX;
    }

    @Override
    public double getScrollRate() {
        return 9;
    }


}
