package net.lucent.easygui.elements.containers.scroll_boxes;

import com.google.gson.JsonObject;
import net.lucent.easygui.elements.other.SquareRenderable;
import net.lucent.easygui.interfaces.ContainerRenderable;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.lucent.easygui.templating.IRenderableDeserializer;
import net.lucent.easygui.templating.deserializers.SquareRenderableDeserializer;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

//TODO update after moving child elements
public class DynamicScrollBox extends AbstractScrollBox {
    private double  maxScrollY;
    private double maxScrollX;

    public DynamicScrollBox(){}
    public DynamicScrollBox(IEasyGuiScreen easyGuiScreen, int x, int y, int width, int height) {
        super(easyGuiScreen, x, y, width, height);
    }



    @Override
    public double getScrollHeight() {
        return maxScrollY;
    }

    @Override
    public void addChild(ContainerRenderable child) {
        super.addChild(child);
        //get largest yValue pos child
        if(child.getY()+child.getHeight() > getScrollHeight()){
            maxScrollY =Math.max(0,child.getY()+child.getHeight()-getInnerHeight());
        }
        if(child.getX()+getWidth() > getScrollWidth())   maxScrollX =Math.max(0,child.getX()+child.getWidth()-getInnerWidth());
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
