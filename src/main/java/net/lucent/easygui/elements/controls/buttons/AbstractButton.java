package net.lucent.easygui.elements.controls.buttons;


import com.google.gson.JsonObject;
import net.lucent.easygui.elements.other.SquareRenderable;
import net.lucent.easygui.holders.EasyGuiEventHolder;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.lucent.easygui.interfaces.complex_events.Sticky;
import net.lucent.easygui.interfaces.events.Clickable;
import net.lucent.easygui.templating.IRenderableDeserializer;
import net.lucent.easygui.templating.actions.Action;
import net.lucent.easygui.templating.deserializers.SquareRenderableDeserializer;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class AbstractButton extends SquareRenderable implements Clickable, Sticky {

    /**
     * how many ticks the button remains pressed for(render property)
     */
    public int PRESSED_TIME = 10;
    public double time_pressed = 0; //measured in ticks
    public boolean unPressNaturally = true;

    public boolean sticky = false;

    public boolean pressed;

    public boolean hovered;

    public Action clickAction;


    public AbstractButton(){}

    public AbstractButton(IEasyGuiScreen easyGuiScreen, int x, int y, int width, int height) {
        super(easyGuiScreen);
        this.setX(x);
        this.setY(y);
        setWidth(width);
        setHeight(height);

    }

    @Override
    public void setSticky(boolean sticky) {
        this.sticky = sticky;
    }
    @Override
    public boolean isSticky(){
        return sticky;
    }

    public boolean isPressed(){
        return pressed;
    }
    public boolean isHovered(){return hovered;}
    public void setPressed(boolean state){
        pressed = state;
    }
    public void setHovered(boolean state){hovered = state;}



    @Override
    public void onClick(double mouseX, double mouseY, int button,boolean clicked) {
        if(clicked){
            setPressed(true);
            time_pressed = 0;
        }
        if(clickAction != null) clickAction.accept(this,mouseX,mouseY,button,clicked);
    }

    @Override
    public void onMouseOver(boolean state) {
        setHovered(state);
    }


    @Override
    public void tick() {
        super.tick();
        if(unPressNaturally){
            time_pressed += 1;
            if (time_pressed >= PRESSED_TIME) {
                setPressed(false);
                time_pressed = 0;
            }
        }
    }
    public void setPressTime(int pressTime){
        this.PRESSED_TIME = pressTime;
    }
    public static class Deserializer extends SquareRenderableDeserializer {
        public Deserializer(Supplier<? extends AbstractButton> supplier) {
            super(supplier);
        }

        @Override
        public void buildRenderable(IEasyGuiScreen screen, IRenderableDeserializer parent, JsonObject obj) {
            super.buildRenderable(screen, parent, obj);
            ((AbstractButton) getRenderable()).setPressTime(getOrDefault(obj,"press_time",10));
            if(obj.getAsJsonObject("on_click") != null){
                System.out.println(obj.getAsJsonObject("on_click"));
                ((AbstractButton) getRenderable()).clickAction =  parseAction("on_click",obj);
            }
        }

        @Override
        public @NotNull IRenderableDeserializer createInstance() {
            return new Deserializer((Supplier<? extends AbstractButton>) supplier);
        }
    }
}
