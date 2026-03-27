package net.lucent.easygui.gui.events.type;

import net.lucent.easygui.gui.RenderableElement;
import net.lucent.easygui.gui.events.EasyEvents;

public class EasyKeyEvent extends EasyEvent{
    private final int keyCode, scanCode, modifiers;



    public static EasyKeyEvent KeyDownEvent(RenderableElement target,int keyCode,int scanCode,int modifiers){
        return KeyDownEvent(target,keyCode,scanCode,modifiers,false);
    }
    public static EasyKeyEvent KeyDownEvent(RenderableElement target,int keyCode,int scanCode,int modifiers,boolean global){
        String event = global ? EasyEvents.GLOBAL_KEY_DOWN_EVENT : EasyEvents.KEY_DOWN_EVENT;
        return new EasyKeyEvent(target,event,keyCode,scanCode,modifiers);
    }

    public static EasyKeyEvent KeyUpEvent(RenderableElement target,int keyCode,int scanCode,int modifiers){
        return KeyUpEvent(target,keyCode,scanCode,modifiers,false);
    }
    public static EasyKeyEvent KeyUpEvent(RenderableElement target,int keyCode,int scanCode,int modifiers,boolean global){
        String event = global ? EasyEvents.GLOBAL_KEY_UP_EVENT : EasyEvents.KEY_UP_EVENT;
        return new EasyKeyEvent(target,event,keyCode,scanCode,modifiers);
    }

    public EasyKeyEvent(RenderableElement target, String event, int keyCode, int scanCode, int modifiers) {
        super(target, event);
        this.keyCode = keyCode;
        this.scanCode = scanCode;
        this.modifiers = modifiers;
    }


    public int getKeyCode(){return keyCode;}
    public int getScanCode(){return scanCode;}
    public int getModifiers(){return modifiers;}
}
