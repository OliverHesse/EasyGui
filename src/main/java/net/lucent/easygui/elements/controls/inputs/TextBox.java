package net.lucent.easygui.elements.controls.inputs;

import com.mojang.blaze3d.platform.InputConstants;
import net.lucent.easygui.elements.BaseRenderable;
import net.lucent.easygui.elements.other.SquareRenderable;
import net.lucent.easygui.holders.EasyGuiEventHolder;
import net.lucent.easygui.interfaces.events.CharTypedListener;
import net.lucent.easygui.interfaces.events.Clickable;
import net.lucent.easygui.interfaces.events.KeyPressedListener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.checkerframework.checker.units.qual.C;
import org.lwjgl.glfw.GLFW;


import java.util.function.Consumer;

/**
 * this is more or less a wrapper around the edit box
 * uses default rendering by default. for more options either extend or use
 * TexturedTextBox
 */

public class TextBox extends SquareRenderable implements Clickable, CharTypedListener, KeyPressedListener {

    private boolean editable = false;
    private final EnhancedEditBox editBox;
    private final Font font;

    /**
     * used whilst unfocused
     */
    private Component hint = null;

    public TextBox(EasyGuiEventHolder eventHandler,int x,int y, int width, int height) {
        super(eventHandler);
        setX(x);
        setY(y);
        this.font = Minecraft.getInstance().font;
        this.editBox = new EnhancedEditBox(this.font,width,height,Component.empty());
    }

    @Override
    public int getWidth() {
        return editBox.getWidth();
    }

    @Override
    public int getHeight() {
        return editBox.getHeight();
    }

    public void setHint(Component hint) {
        this.hint = hint;
    }

    public void setEditable(boolean editable){
        editBox.setEditable(editable);
        this.editable = editable;
    }
    public boolean getEditable(){return editable;}

    @Override
    public void onClick(double mouseX, double mouseY, int button,boolean clicked) {
        System.out.println("clicked");
        if(!clicked){
            setEditable(false);
            setFocused(false);
            return;
        }
        if(isFocused()){
            System.out.println("enhanced click");
            editBox.enhancedOnClick(mouseX,mouseY,button,(int) getGlobalScaledX(),getTotalScaleFactorX());
        }else {
            setEditable(true);
            setFocused(true);

        }
        System.out.println(getEditable());
        System.out.println(isFocused());
    }


    @Override
    public void setFocused(boolean focused) {
        super.setFocused(focused);
        if(focused != editBox.isFocused()) editBox.setFocused(focused);
    }

    public String getValue() {
        return editBox.getValue();
    }
    public void setValue(String value) {
        editBox.setValue(value);
    }

    /**
     * for if you do not want responder to be called
     * @param value
     */
    public void setValueWithoutNotification(String value) {
        Consumer<String> temp = editBox.responder;
        editBox.setResponder(null);
        editBox.setValue(value);
        editBox.setResponder(temp);
    }
    public void setTextColor(int color) {
        editBox.setTextColor(color);
    }

    public void setTextColorUneditable(int color) {
        editBox.setTextColorUneditable(color);
    }

    public void setBordered(boolean bordered) {
        editBox.setBordered(bordered);

        if (!bordered) {
            editBox.setX(getX() + 1);
            editBox.setY(getY() + 1);
            editBox.setWidth(getWidth() - 6);
        } else {
            editBox.setX(getX());
            editBox.setY(getY());
            editBox.setWidth(getWidth());
        }
    }

    /**
     * potentially modify to include a list of responders
     * called on change
     * @param responder
     */

    public void setResponder(Consumer<String> responder){
        editBox.setResponder(responder);
    }

    public void setMaxLength(int length){
        editBox.setMaxLength(length);
    }

    @Override
    public void onCharTyped(char codePoint, int modifiers) {
        editBox.charTyped(codePoint,modifiers);
    }

    @Override
    public void onKeyPressed(int keyCode, int scanCode, int modifier) {

        if(!isFocused())return;
        editBox.keyPressed(keyCode,scanCode,modifier);
        if(keyCode == GLFW.GLFW_KEY_ENTER) onEnter();

    }

    /**
     * not used here but useful for things like search box
     */
    public void onEnter(){}

    /**
     * nod needed but useful for rendering
     * @param state is mouse over this(even if mouse is over if it is not on top(not z index but child depth))
     */
    @Override
    public void onMouseOver(boolean state) {
        Clickable.super.onMouseOver(state);
    }

    @Override
    public void renderSelf(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        System.out.println("=========");
        System.out.println(getWidth());
        System.out.println(getScaledWidth());
        editBox.renderWidget(guiGraphics,mouseX,mouseY,partialTick);
        if(editBox.getValue().isEmpty() && hint != null && !editBox.isFocused()){
            guiGraphics.drawCenteredString(this.font,hint,getWidth()/2,
                    getHeight()/2,editBox.textColor);
        }
    }
}
