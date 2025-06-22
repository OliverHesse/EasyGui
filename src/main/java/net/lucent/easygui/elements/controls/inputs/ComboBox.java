package net.lucent.easygui.elements.controls.inputs;

import net.lucent.easygui.elements.other.SquareRenderable;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.lucent.easygui.interfaces.events.Clickable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import oshi.util.tuples.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

public class ComboBox extends SquareRenderable implements Clickable {
    public int borderColor = -16777216;
    public int textColor = -16777216;
    public int backgroundColor = -7631989;
    public int hoverColor =-6250336 ;
    public int borderWidth = 2;
    private final List<String> entries = new ArrayList<>();

    public Font font = Minecraft.getInstance().font;


    int selectedIndex = 0;
    int maxDropDownHeight = 100; //arbitrary
    Consumer<String> consumer;
    boolean hovered = false;
    public ComboBox(IEasyGuiScreen easyGuiScreen, int x, int y, int width, int height, Consumer<String> valueChanged) {
        super(easyGuiScreen);
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
        consumer = valueChanged;
    }



    @Override
    public int getHeight() {
        if(!isFocused()){
            return super.getHeight();
        }
        return height+ height*entries.size();
    }

    public void setValue(int index){
        this.selectedIndex = index;
    }

    public void addValue(String value){
        entries.add(value);
    }
    public void addValues(List<String> values){
        entries.addAll(values);
    }
    public List<String> getValues(){
        return List.copyOf(entries);
    }
    public String getValue(int index){
        return entries.get(index);
    }
    /*
        uses different scissor than normal since individual options don't want to overlap.
        this could be fixed with labels however since they have cull
     */
    @Override
    public void renderSelf(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        //firs render box

        //draw main box
        //TODO add drop down arrow button
        guiGraphics.fill(0,0,getWidth(),height,backgroundColor);
        guiGraphics.enableScissor((int) getGlobalScaledX(),(int) getGlobalScaledY(),(int) getGlobalScaledX()+getWidth(), (int) (getGlobalScaledY()+height));
        guiGraphics.drawString(font,getValue(selectedIndex),0,height/2 - font.lineHeight/2,textColor,false);
        guiGraphics.disableScissor();

        //draw options
        int hoveredIndex = -1;
        if(hovered){
            //calc which one is hovered;
            double y = screenToLocalY(mouseY);
            if(y > height){
                hoveredIndex = (int) Math.floor( (y-height) /height);
            }
        }
        if(isFocused()){
            for(int i = 0;i<entries.size();i++){
                int renderColor = backgroundColor;
                if(i == hoveredIndex) renderColor = hoverColor;
                guiGraphics.enableScissor((int) getGlobalScaledX(),(int) getGlobalScaledY()+height+i*height,
                        (int) getGlobalScaledX()+getWidth(), (int) (getGlobalScaledY()+2*height+i*height));
                guiGraphics.fill(0,height+i*height,getWidth(),2*height+i*height,renderColor);
                guiGraphics.drawString(font,getValue(i),0,(height+i*height)+height/2-font.lineHeight/2,textColor,false);
                guiGraphics.disableScissor();
            }
        }
        if(borderWidth != 0){
            //draw borders properly
            guiGraphics.fill(-borderWidth,-borderWidth,0,height+borderWidth,borderColor);
            guiGraphics.fill(-borderWidth,-borderWidth,getWidth()+borderWidth,0,borderColor);
            guiGraphics.fill(getWidth(),-borderWidth,getWidth()+borderWidth,height+borderWidth,borderColor);
            guiGraphics.fill(-borderWidth,height,getWidth()+borderWidth,height+borderWidth,borderColor);
        }
    }

    @Override
    public void onMouseOver(boolean state) {
        super.onMouseOver(state);
        hovered = state;
    }

    @Override
    public void onClick(double mouseX, double mouseY, int button, boolean clicked) {
        if(!focused && clicked) setFocused(true);
        else if(clicked){
            //menu is open so select a value
            double y = screenToLocalY(mouseY);
            if(y > height){
                int index = (int) Math.floor( (y-height) /height);
                setValue(index);
                consumer.accept(getValue(index));
            }
            setFocused(false);
        }else   setFocused(false);
    }
}
