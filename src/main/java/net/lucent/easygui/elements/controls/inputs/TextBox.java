package net.lucent.easygui.elements.controls.inputs;

import com.google.gson.JsonObject;
import com.mojang.blaze3d.platform.InputConstants;
import net.lucent.easygui.elements.BaseRenderable;
import net.lucent.easygui.elements.other.SquareRenderable;
import net.lucent.easygui.holders.EasyGuiEventHolder;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.lucent.easygui.interfaces.complex_events.Draggable;
import net.lucent.easygui.interfaces.events.CharTypedListener;
import net.lucent.easygui.interfaces.events.Clickable;
import net.lucent.easygui.interfaces.events.KeyPressedListener;
import net.lucent.easygui.interfaces.events.MouseDragListener;
import net.lucent.easygui.templating.IRenderableDeserializer;
import net.lucent.easygui.templating.actions.Action;
import net.lucent.easygui.templating.deserializers.SquareRenderableDeserializer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import org.checkerframework.checker.units.qual.C;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;


import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * this is more or less a wrapper around the edit box
 * uses default rendering by default. for more options either extend or use
 * TexturedTextBox
 */

public class TextBox extends SquareRenderable implements Draggable, CharTypedListener, KeyPressedListener {

    private boolean editable = false;
    private final EnhancedEditBox editBox;
    private final Font font = Minecraft.getInstance().font;
    private boolean dragging;
    private int draggingX;

    /**
     * used whilst unfocused
     */
    private Component hint = null;

    public Action charTypedAction;

    public Action keyPressedAction;
    public Action mouseDraggedAction;
    public Action clickedAction;
    public Action mouseReleasedAction;

    public TextBox(){

        this.editBox = new EnhancedEditBox(this.font,0,font.lineHeight+2,Component.empty());
        this.editBox.parent = this;

    }

    public TextBox(IEasyGuiScreen easyGuiScreen, int x, int y, int width) {
        super(easyGuiScreen);
        setX(x);
        setY(y);
        this.editBox = new EnhancedEditBox(this.font,width,font.lineHeight+2,Component.empty());
        this.editBox.parent = this;

    }

    @Override
    public void setWidth(int width) {
        super.setWidth(width);
        editBox.setWidth(width);
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


    public void setPivot(int pivotX) {
        draggingX = pivotX;

    }

    @Override
    public boolean isDragged() {
        return dragging;
    }

    @Override
    public void setDragged(boolean state) {
        dragging = state;
    }

    @Override
    public void setClickPosition(double mouseX, double mouseY) {

    }

    @Override
    public void onClick(double mouseX, double mouseY, int button,boolean clicked) {


        if(!clicked){
            setEditable(false);
            setFocused(false);

        }else{
            if(isFocused()){

                editBox.enhancedOnClick(mouseX,mouseY,button,(int) getGlobalScaledX(),getTotalScaleFactorX());
            }else {
                setEditable(true);
                setFocused(true);

            }
            if(clicked && button == InputConstants.MOUSE_BUTTON_LEFT) {
                setDragged(true);
                setPivot((int) ((Mth.floor(mouseX) - getGlobalScaledX())/getTotalScaleFactorX()));
            }
        }
        if(clickedAction != null) clickedAction.accept(this,mouseX,mouseY,button,clicked);
    }

    @Override
    public void onMouseReleased(double mouseX, double mouseY, int button) {
        if(button == InputConstants.MOUSE_BUTTON_LEFT) {
            setDragged(false);
            setPivot(0);
        }
        if(mouseReleasedAction != null) mouseReleasedAction.accept(this,mouseX,mouseY,button);
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
        if(charTypedAction != null) charTypedAction.accept(this,codePoint,modifiers);
    }

    @Override
    public void onKeyPressed(int keyCode, int scanCode, int modifier) {

        if (isFocused()) {
            editBox.keyPressed(keyCode, scanCode, modifier);
            if (keyCode == GLFW.GLFW_KEY_ENTER) onEnter();
        }
        if(keyPressedAction != null) keyPressedAction.accept(this,keyCode,scanCode,modifier);
    }

    /**
     * not used here but useful for things like search box
     */
    public void onEnter(){}



    @Override
    public void renderSelf(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {

        editBox.renderWidget(guiGraphics,mouseX,mouseY,partialTick);
        if(editBox.getValue().isEmpty() && hint != null && !editBox.isFocused()){
            guiGraphics.drawCenteredString(this.font,hint,getWidth()/2,
                    getHeight()/2,editBox.textColor);
        }
    }

    @Override
    public void onDrag(double mouseX, double mouseY, int button, double dragX, double dragY) {

        int distance = (int) ((Mth.floor(mouseX) - getGlobalScaledX())/getTotalScaleFactorX());
        //if(distance > font.width(editBox.getValue())) return;

        editBox.highlightSubstring(distance);
        if(mouseDraggedAction != null) mouseDraggedAction.accept(this,mouseX,mouseY,button,dragX,dragY);
    }



    public static class Deserializer extends SquareRenderableDeserializer{

        @Override
        public void parseHeight(String expr) {
            return;
        }

        public Deserializer(Supplier<? extends TextBox> supplier) {
            super(supplier);
        }

        @Override
        public void buildRenderable(IEasyGuiScreen screen, IRenderableDeserializer parent, JsonObject obj) {
            super.buildRenderable(screen, parent, obj);
            ((TextBox)getRenderable()).setHint(parseComponent("hint",obj));

            ((TextBox) getRenderable()).clickedAction =  parseAction("on_click",obj);
            ((TextBox) getRenderable()).mouseReleasedAction =  parseAction("on_click_release",obj);
            ((TextBox) getRenderable()).mouseDraggedAction =  parseAction("on_mouse_drag",obj);
            ((TextBox) getRenderable()).charTypedAction =  parseAction("on_char_typed",obj);
            ((TextBox) getRenderable()).keyPressedAction =  parseAction("on_key_pressed",obj);
            ((TextBox) getRenderable()).editBox.valueChangedAction =  parseAction("on_value_changed",obj);



        }

        @Override
        public @NotNull IRenderableDeserializer createInstance() {
            return new Deserializer((Supplier<? extends TextBox>) supplier);
        }
    }
}
