package net.lucent.easygui.elements.controls.buttons;


import net.lucent.easygui.elements.other.SquareRenderable;
import net.lucent.easygui.holders.EasyGuiEventHolder;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.lucent.easygui.interfaces.complex_events.Sticky;
import net.lucent.easygui.interfaces.events.Clickable;

public abstract class AbstractButton extends SquareRenderable implements Clickable, Sticky {

    /**
     * how many ticks the button remains pressed for(render property)
     */
    public int PRESSED_TIME = 60;
    public double time_pressed = 0; //measured in ticks
    public boolean unPressNaturally = true;

    public boolean sticky = false;

    public boolean pressed;

    public boolean hovered;

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
        if(!clicked) return;

        setPressed(true);
    }

    @Override
    public void onMouseOver(boolean state) {
        setHovered(state);
    }

    @Override
    public void recalculatePos(int oldWidth, int oldHeight) {

    }

    @Override
    public void tick() {
        super.tick();
        time_pressed += 1;
        if (time_pressed >= PRESSED_TIME) {
            pressed = false;
            time_pressed = 0;
        }
    }
}
