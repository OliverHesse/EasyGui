package net.lucent.easygui.elements.controls.inputs;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.platform.InputConstants;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.lucent.easygui.elements.other.SquareRenderable;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.lucent.easygui.interfaces.ITextureData;
import net.lucent.easygui.interfaces.events.*;
import net.lucent.easygui.templating.IRenderableDeserializer;
import net.lucent.easygui.templating.actions.Action;
import net.lucent.easygui.templating.deserializers.SquareRenderableDeserializer;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.StringSplitter;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.font.TextFieldHelper;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/*
    mostly uses code from BookEditScreen since MultiLineEditBox did not render text for some reason
 */
public class MultiLineTextBox extends SquareRenderable implements
        Clickable, KeyPressedListener, CharTypedListener, MouseDragListener {

    //look at rebuildDisplayCache. seems to do what i want
    public String pageText = "";
    public Font font = Minecraft.getInstance().font;

    private final Minecraft minecraft = Minecraft.getInstance();
    private DisplayData displayData ;

    private int lastIndex = -1;
    private long lastClickTime;

    private final TextFieldHelper boxData = new TextFieldHelper(
                this::getCurrentPageText,
                this::setCurrentPageText,
                this::getClipboard,
                this::setClipboard,
                value -> font.wordWrapHeight(value,getWidth()) <= (getHeight()-font.lineHeight-1));
    private int textColor = 14737632;
    private int textColorUnFocused= 7368816;
    private ITextureData backgroundTexture=  null;
    private int backgroundColor = -16777216;
    private int borderColor = -1;
    private boolean bordered = false;
    private int borderWidth = 1;
    private int frameTick;

    private Consumer<String> responder = null;

    public Action charTypedAction;
    public Action valueChangedAction;
    public Action keyPressedAction;
    public Action mouseDraggedAction;
    public Action clickedAction;

    public MultiLineTextBox(){

    }

    public MultiLineTextBox(IEasyGuiScreen easyGuiScreen, int x, int y, int width, int height) {
        super(easyGuiScreen);
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);

    }

    public void setBackgroundColor(int color){
        this.backgroundColor = color;
    }
    public void setTextColor(int color){
        this.textColor = color;
    }
    public void setTextColorUnFocused(int color){
        this.textColorUnFocused = color;
    }
    public void setBackgroundTexture(ITextureData texture){
        this.backgroundTexture = texture;
    }
    public void setBorderColor(int color){
        this.borderColor = color;
    }
    public void setBorderWidth(int width){
        borderWidth = width;
    }

    public void setBordered(boolean bordered){
        this.bordered = bordered;
    }



    private String getCurrentPageText() {
        return pageText;
    }

    private void setCurrentPageText(String text) {
        pageText = text;
    }

    private void setClipboard(String clipboardValue) {
        if (this.minecraft != null) {
            TextFieldHelper.setClipboardContents(this.minecraft, clipboardValue);
        }
    }

    private String getClipboard() {
        return this.minecraft != null ? TextFieldHelper.getClipboardContents(this.minecraft) : "";
    }


    @Override
    public void renderSelf(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        int textColor = this.textColor;
        if(!isFocused())textColor = this.textColorUnFocused;
        DisplayData displayData = getDisplayData();

        if(bordered){
            guiGraphics.fill(-borderWidth,-borderWidth,getWidth()+borderWidth,getHeight()+borderWidth,borderColor);
        }


        guiGraphics.fill(0,0,getWidth(),getHeight(),backgroundColor);


        if(backgroundTexture != null) backgroundTexture.renderTexture(guiGraphics);

        for(int i =0;i<displayData.lines.length;i++){
            guiGraphics.drawString(font,displayData.lines[i],0,i*(font.lineHeight+1),textColor,false);
        }


        if(isFocused()){
            renderHighlighted(guiGraphics,displayData.selection);
            renderCursor(guiGraphics,displayData.cursorX,displayData.cursorY,displayData.cursorAtEnd);
        }
    }
    private void renderCursor(GuiGraphics guiGraphics, int cursorX, int cursorY, boolean isEndOfText) {
        if (this.frameTick / 6 % 2 == 0) {

            if (!isEndOfText) {
                guiGraphics.fill(cursorX, cursorY- 1, cursorX + 1, cursorY + font.lineHeight+1, textColor);
            } else {
                guiGraphics.drawString(this.font, "_", cursorX,cursorY, textColor, false);
            }
        }
    }

    private void renderHighlighted(GuiGraphics guiGraphics, Rect2i[] highlightAreas){
        for (Rect2i rect2i : highlightAreas) {
            int i = rect2i.getX();
            int j = rect2i.getY();
            int k = i + rect2i.getWidth();
            int l = j + rect2i.getHeight();
            guiGraphics.fill(RenderType.guiTextHighlight(), i, j, k, l, -16776961);
        }
    }

    @Override
    public void onCharTyped(char codePoint, int modifiers) {
        if(isFocused() && StringUtil.isAllowedChatCharacter(codePoint)){
            this.boxData.insertText(Character.toString(codePoint));
            if(responder != null) responder.accept(getCurrentPageText());
            if(charTypedAction != null) charTypedAction.accept(this);
            if(valueChangedAction != null) valueChangedAction.accept(this);
            this.clearDisplayData();
        }
    }



    @Override
    public void onClick(double mouseX, double mouseY, int button, boolean clicked) {
        if(clicked && button == InputConstants.MOUSE_BUTTON_LEFT){
            setFocused(true);
            long clickTime = Util.getMillis();
            DisplayData displayData = getDisplayData();
            int index = displayData.getIndexAtPosition(font,screenToLocalX((int) mouseX),screenToLocalY((int) mouseY));
            if(index >= 0){

                if(index != lastIndex|| clickTime-lastClickTime >=250L){
                    this.boxData.setCursorPos(index, Screen.hasShiftDown());
                }else if(!boxData.isSelecting()){
                    selectWord(index);
                }else{
                    boxData.selectAll();
                }
                clearDisplayData();
            }

            this.lastIndex = index;
            this.lastClickTime = clickTime;
        }else {
            setFocused(false);
        }
        if(clickedAction != null) clickedAction.accept(this,mouseX,mouseY,button,clicked);
    }

    @Override
    public void onKeyPressed(int keyCode, int scanCode, int modifier) {
        if(isFocused()){
            boolean flag = this.textBoxKeyPressed(keyCode, scanCode, modifier);
            if (flag) {
                if(responder != null) this.responder.accept(getCurrentPageText());
                if(keyPressedAction != null)keyPressedAction.accept(this);
                if(valueChangedAction != null) valueChangedAction.accept(this);
                clearDisplayData();
            }
        }
    }

    public boolean textBoxKeyPressed(int keyCode, int scanCode, int modifier){
        if (Screen.isSelectAll(keyCode)) {
            this.boxData.selectAll();
            return true;
        } else if (Screen.isCopy(keyCode)) {
            this.boxData.copy();
            return true;
        } else if (Screen.isPaste(keyCode)) {
            this.boxData.paste();
            return true;
        } else if (Screen.isCut(keyCode)) {
            this.boxData.cut();
            return true;
        }else{
            TextFieldHelper.CursorStep textfieldhelper$cursorstep = Screen.hasControlDown()
                    ? TextFieldHelper.CursorStep.WORD
                    : TextFieldHelper.CursorStep.CHARACTER;
            return switch (keyCode) {
                case 257, 335 -> {
                    this.boxData.insertText("\n");
                    yield true;
                }
                case 259 -> {
                    this.boxData.removeFromCursor(-1, textfieldhelper$cursorstep);
                    yield true;
                }
                case 261 -> {
                    this.boxData.removeFromCursor(1, textfieldhelper$cursorstep);
                    yield true;
                }
                case 262 -> {
                    this.boxData.moveBy(1, Screen.hasShiftDown(), textfieldhelper$cursorstep);
                    yield true;
                }
                case 263 -> {
                    this.boxData.moveBy(-1, Screen.hasShiftDown(), textfieldhelper$cursorstep);
                    yield true;
                }
                case 264 -> {
                    this.keyDown();
                    yield true;
                }
                case 265 -> {
                    this.keyUp();
                    yield true;
                }
                case 268 -> {
                    this.keyHome();
                    yield true;
                }
                case 269 -> {
                    this.keyEnd();
                    yield true;
                }
                default -> false;
            };

        }
    }
    private void keyUp() {
        this.changeLine(-1);
    }

    private void keyDown() {
        this.changeLine(1);
    }

    private void changeLine(int yChange) {
        int i = this.boxData.getCursorPos();
        int j = this.getDisplayData().changeLine(i, yChange);
        this.boxData.setCursorPos(j, Screen.hasShiftDown());
    }

    private void keyHome() {
        if (Screen.hasControlDown()) {
            this.boxData.setCursorToStart(Screen.hasShiftDown());
        } else {
            int i = this.boxData.getCursorPos();
            int j = this.getDisplayData().findLineStart(i);
            this.boxData.setCursorPos(j, Screen.hasShiftDown());
        }
    }

    private void keyEnd() {
        if (Screen.hasControlDown()) {
            this.boxData.setCursorToEnd(Screen.hasShiftDown());
        } else {
            int i = this.boxData.getCursorPos();
            int j = getDisplayData().findLineEnd(i);
            this.boxData.setCursorPos(j, Screen.hasShiftDown());
        }
    }


    public void setResponder(Consumer<String> responder){
        this.responder = responder;
    }
    @Override
    public void onDrag(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if(isFocused() && button == InputConstants.MOUSE_BUTTON_LEFT){

            int i = getDisplayData().getIndexAtPosition(
                    this.font,screenToLocalX((int) mouseX),screenToLocalY((int) mouseY));
            this.boxData.setCursorPos(i, true);
            this.clearDisplayData();

        }
        if(mouseDraggedAction != null) mouseDraggedAction.accept(this,mouseX,mouseY,button,dragX,dragX);
    }
    @Override
    public void tick() {
        frameTick += 1;
    }

    public DisplayData getDisplayData(){
        if (this.displayData == null) {
            this.displayData = this.buildDisplayData();
        }
        return this.displayData;
    }

    public void clearDisplayData(){
        displayData = null;
    }

    public DisplayData buildDisplayData(){
        if(pageText.isEmpty()){
            return DisplayData.EMPTY;
        }

        //calculate render data
        int cursorPos = boxData.getCursorPos();
        int selectionPos = boxData.getSelectionPos();

        List<Component> lines = new ArrayList<>();


        IntList intlist = new IntArrayList();
        MutableBoolean mutableboolean = new MutableBoolean();

        //TODO try to rename variables. dont make much sense rn
        this.font.getSplitter().splitLines(pageText,getWidth(), Style.EMPTY,true,(style, i, i1) -> {


            String s2 = pageText.substring(i, i1);
            mutableboolean.setValue(s2.endsWith("\n") );

            String s3 = StringUtils.stripEnd(s2, " \n");

            intlist.add(i);
            lines.add(Component.literal(s3));
        });
        int[] lineStarts = intlist.toIntArray();

        boolean endOfPage = cursorPos == pageText.length();
        int cursorX = 0;
        int cursorY = 0;
        if (endOfPage && mutableboolean.isTrue()) {
            cursorY = lines.size()* (font.lineHeight+1);

        }else {
            int line = (findLineFromPos(lineStarts,cursorPos));
            cursorX = this.font.width(pageText.substring(lineStarts[line],cursorPos));
            cursorY = line*(font.lineHeight+1);
        }
        List<Rect2i> selections = Lists.newArrayList();

        if(cursorPos != selectionPos){
            int startPos = Math.min(cursorPos,selectionPos);
            int endPos = Math.max(cursorPos,selectionPos);
            int startLine = findLineFromPos(lineStarts,startPos);
            int endLine = findLineFromPos(lineStarts,endPos);
            if(startLine == endLine){
                int yPos = startLine * (font.lineHeight+1);
                selections.add(this.createPartialLineSelection(pageText, font.getSplitter(), startPos, endPos, yPos, lineStarts[startLine]));
            }else {

                int endCord = startLine + 1 > lineStarts.length ? pageText.length() : lineStarts[startLine + 1];
                selections.add(this.createPartialLineSelection(pageText, font.getSplitter(), startPos,endCord, startLine *(font.lineHeight+1), lineStarts[startLine]));

                for(int i =startLine+1;i<endLine;i++){
                    int lineCord = i*(font.lineHeight+1);
                    String s1 = pageText.substring(lineStarts[i],lineStarts[i+1]);
                    int width = (int) font.getSplitter().stringWidth(s1);
                    selections.add(this.createSelection(0,lineCord,width, lineCord+(font.lineHeight+1)));
                }
                selections.add(this.createPartialLineSelection(pageText, font.getSplitter(), lineStarts[endLine],
                        endPos, endLine*(font.lineHeight+1), lineStarts[endLine]));
            }
        }
        return new DisplayData(pageText,cursorX,cursorY,lineStarts,lines.toArray(new Component[0]),selections.toArray(new Rect2i[0]),endOfPage);
    }

    private static int findLineFromPos(int[] lineStarts, int find){
        int i = Arrays.binarySearch(lineStarts, find);
        return i < 0 ? -(i + 2) : i;
    }
    private Rect2i createPartialLineSelection(String input, StringSplitter splitter, int startPos, int endPos, int y, int lineStart) {
        String s = input.substring(lineStart, startPos);
        String s1 = input.substring(lineStart, endPos);
        int x1 = (int) splitter.stringWidth(s);
        int y1 = y;
        int x2 = (int) splitter.stringWidth(s1);
        int y2 = y+(font.lineHeight+1);
        return this.createSelection(x1,y1,x2,y2);
    }
    private Rect2i createSelection(int x1, int y1,int x2,int y2) {

        int minX = Math.min(x1,x2);
        int maxX = Math.max(x1,x2);
        int minY = Math.min(y1,y2);
        int maxY = Math.max(y1,y2);
        return new Rect2i(minX, minY, maxX - minX, maxY-minY);
    }

    //when double-clicking to select a word
    private void selectWord(int index){
        String s = this.getCurrentPageText();
        this.boxData.setSelectionRange(StringSplitter.getWordPosition(s, -1, index, false), StringSplitter.getWordPosition(s, 1, index, false));

    }



    public static class DisplayData{
        public static DisplayData EMPTY = new DisplayData(
                "",
                0,
                0,
                new int[]{0},
                new Component[]{Component.empty()},
                new Rect2i[0],
                true
        );
        public int cursorX;
        public int cursorY;
        private final String text;
        private Rect2i[] selection;
        private final int[] lineStarts;
        private boolean cursorAtEnd;
        private Component[] lines;

        public DisplayData(String text,int cursorX,int cursorY,int[] lineStarts,Component[] lines,Rect2i[] selection,boolean cursorAtEnd){
            this.text = text;
            this.lineStarts= lineStarts;
            this.lines = lines;
            this.selection = selection;
            this.cursorAtEnd = cursorAtEnd;
            this.cursorX = cursorX;
            this.cursorY =cursorY;

        }
        public int getIndexAtPosition(Font font,int cursorX,int cursorY) {
            int line = cursorY / (font.lineHeight+1);
            if (line < 0) {
                return 0;
            } else if (line >= this.lines.length) {
                return this.text.length();
            } else {
                Component component = this.lines[line];
                return this.lineStarts[line]
                        + font.getSplitter().plainIndexAtWidth(component.getString(),cursorX,Style.EMPTY);
            }
        }
        public int changeLine(int xChange, int yChange) {
            int line = MultiLineTextBox.findLineFromPos(this.lineStarts, xChange);
            int newLine = line + yChange;
            int xPos;
            if (0 <= newLine && newLine < this.lineStarts.length) {
                int x1= xChange - this.lineStarts[line];
                int x2 = this.lines[newLine].getString().length();
                xPos = this.lineStarts[newLine] + Math.min(x1, x2);
            } else {
                xPos = xChange;
            }

            return xPos;
        }
        public int findLineStart(int line) {
            int i = MultiLineTextBox.findLineFromPos(this.lineStarts, line);
            return this.lineStarts[i];
        }

        public int findLineEnd(int line) {
            int i = MultiLineTextBox.findLineFromPos(this.lineStarts, line);
            return this.lineStarts[i] + this.lines[i].getString().length();
        }
    }
    public static class Deserializer extends SquareRenderableDeserializer {

        @Override
        public void parseHeight(String expr) {
            return;
        }

        public Deserializer(Supplier<? extends MultiLineTextBox> supplier) {
            super(supplier);
        }

        @Override
        public void buildRenderable(IEasyGuiScreen screen, IRenderableDeserializer parent, JsonObject obj) {
            super.buildRenderable(screen, parent, obj);

            ((MultiLineTextBox) getRenderable()).setTextColor(getOrDefault(obj,"text_color",14737632));
            ((MultiLineTextBox) getRenderable()).setTextColorUnFocused(getOrDefault(obj,"unfocused_text_color",7368816));
            ((MultiLineTextBox) getRenderable()).setBackgroundColor(getOrDefault(obj,"background_color", -16777216));
            ((MultiLineTextBox) getRenderable()).setBorderColor(getOrDefault(obj,"border_color", -1));
            ((MultiLineTextBox) getRenderable()).setBorderWidth(getOrDefault(obj,"border_width", 1));
            ((MultiLineTextBox) getRenderable()).setBordered(getOrDefault(obj,"border_visible",true));
            ((MultiLineTextBox) getRenderable()).charTypedAction = parseAction("on_char_typed",obj);

            ((MultiLineTextBox) getRenderable()).clickedAction =  parseAction("on_click",obj);
            ((MultiLineTextBox) getRenderable()).mouseDraggedAction =  parseAction("on_mouse_drag",obj);
            ((MultiLineTextBox) getRenderable()).keyPressedAction =  parseAction("on_key_pressed",obj);
            ((MultiLineTextBox) getRenderable()).valueChangedAction =  parseAction("on_value_changed",obj);
        }

        @Override
        public @NotNull IRenderableDeserializer createInstance() {
            return new Deserializer((Supplier<? extends MultiLineTextBox>) supplier);
        }
    }
}
