package net.lucent.easygui.holders;


import net.lucent.easygui.handlers.event_handlers.*;
import net.lucent.easygui.interfaces.ContainerRenderable;

import net.lucent.easygui.interfaces.events.*;


public class EasyGuiEventHolder {
    public ClickEventHandler CLICK_EVENT = new ClickEventHandler();
    public MouseReleaseEventHandler MOUSE_RELEASE_EVENT = new MouseReleaseEventHandler();
    public MouseOverEventHandler MOUSE_OVER_EVENT = new MouseOverEventHandler();
    public MouseDraggedEventHandler MOUSE_DRAG_EVENT = new MouseDraggedEventHandler();
    public ScreenResizeEventHandler SCREEN_RESIZE_EVENT = new ScreenResizeEventHandler();
    public KeyPressedEventHandler KEY_PRESS_EVENT = new KeyPressedEventHandler();
    public KeyReleasedEventHandler KEY_RELEASED_EVENT = new KeyReleasedEventHandler();
    public CharTypedEventHandler CHAR_TYPED_EVENT = new CharTypedEventHandler();
    public MouseScrollEventHandler MOUSE_SCROLL_EVENT = new MouseScrollEventHandler();
    public MouseMovedEventHandler MOUSE_MOVED_EVENT = new MouseMovedEventHandler();

    public GuiScaleChangedEventHandler GUI_SCALE_CHANGED_EVENT = new GuiScaleChangedEventHandler();

    public TickEventHandler TICK_EVENT = new TickEventHandler();

    public void unregister(ContainerRenderable obj){
        TICK_EVENT.unregister(obj);
        if(obj instanceof Clickable){
            CLICK_EVENT.unregister((Clickable) obj);
        }
        if(obj instanceof Hoverable){
            MOUSE_OVER_EVENT.unregister((Hoverable) obj);
        }
        if(obj instanceof ScreenResizeListener){
            SCREEN_RESIZE_EVENT.unregister((ScreenResizeListener) obj);

        }
        if(obj instanceof KeyPressedListener){
            KEY_PRESS_EVENT.unregister((KeyPressedListener) obj);
        }
        if(obj instanceof MouseReleaseListener){
            MOUSE_RELEASE_EVENT.unregister((MouseReleaseListener) obj);
        }
        if(obj instanceof MouseDragListener){
            MOUSE_DRAG_EVENT.unregister((MouseDragListener) obj);
        }
        if(obj instanceof CharTypedListener){
            CHAR_TYPED_EVENT.unregister((CharTypedListener) obj);
        }
        if(obj instanceof KeyReleasedListener){
            KEY_RELEASED_EVENT.unregister((KeyReleasedListener) obj);
        }
        if(obj instanceof MouseScrollListener){
            MOUSE_SCROLL_EVENT.unregister((MouseScrollListener) obj);
        }
        if(obj instanceof MouseMovedListener){
            MOUSE_MOVED_EVENT.unregister((MouseMovedListener) obj);
        }
        if(obj instanceof GuiScaleListener){
            GUI_SCALE_CHANGED_EVENT.unregister((GuiScaleListener) obj);
        }
    }

    public void register(ContainerRenderable obj){

        TICK_EVENT.register(obj);
        if(obj instanceof Clickable){
            CLICK_EVENT.register((Clickable) obj);
        }
        if(obj instanceof Hoverable){
            MOUSE_OVER_EVENT.register((Hoverable) obj);
        }
        if(obj instanceof ScreenResizeListener){
            SCREEN_RESIZE_EVENT.register((ScreenResizeListener) obj);

        }
        if(obj instanceof KeyPressedListener){
            KEY_PRESS_EVENT.register((KeyPressedListener) obj);
        }
        if(obj instanceof MouseReleaseListener){
            MOUSE_RELEASE_EVENT.register((MouseReleaseListener) obj);
        }
        if(obj instanceof MouseDragListener){

            MOUSE_DRAG_EVENT.register((MouseDragListener) obj);
        }
        if(obj instanceof CharTypedListener){
            CHAR_TYPED_EVENT.register((CharTypedListener) obj);
        }
        if(obj instanceof KeyReleasedListener){
            KEY_RELEASED_EVENT.register((KeyReleasedListener) obj);
        }
        if(obj instanceof MouseScrollListener){
            MOUSE_SCROLL_EVENT.register((MouseScrollListener) obj);
        }
        if(obj instanceof MouseMovedListener){
            MOUSE_MOVED_EVENT.register((MouseMovedListener) obj);
        }
        if(obj instanceof GuiScaleListener){
            GUI_SCALE_CHANGED_EVENT.register((GuiScaleListener) obj);
        }
    }

}
