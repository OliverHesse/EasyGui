package net.Lucent.EasyGui.elements.controls.buttons;


import net.Lucent.EasyGui.elements.other.SquareRenderable;
import net.Lucent.EasyGui.holders.EasyGuiEventHolder;
import net.Lucent.EasyGui.interfaces.events.Clickable;

public abstract class AbstractButton extends SquareRenderable implements Clickable {

    /**
     * how many ticks the button remains pressed for(render property)
     */
    public int PRESSED_TIME = 20;
    public int time_pressed = 0;

    public int width;
    public int height;

    public boolean pressed;

    public boolean hovered;

    public AbstractButton(EasyGuiEventHolder eventHandler, int x, int y, int width, int height) {
        super(eventHandler);
        this.setX(x);
        this.setY(y);
        this.width = width;
        this.height = height;
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
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }


    @Override
    public void onClick(double mouseX, double mouseY, int button) {
        setPressed(true);
    }

    @Override
    public void onMouseOver(boolean state) {
        setHovered(state);
    }
}
