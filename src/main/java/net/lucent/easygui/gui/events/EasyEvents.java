package net.lucent.easygui.gui.events;


public class EasyEvents {

    /*TODO
        add a element position transform changed and element transform changed
        this is important for stuff like JEI compatibility


        TODO modify EasyEvent to use instances instead, e,g mouseEvent, transofmrEvent etc
        TODO also differentiate between targeted and global events
     */
    //=================== CAPTURE BUBBLE EVENTS ===================
    public static String MOUSE_DOWN_EVENT = "mouse_down_event";
    public static String MOUSE_UP_EVENT = "mouse_up_event";
    public static String MOUSE_MOVE_EVENT = "mouse_move_event";
    public static String MOUSE_SCROLL_EVENT = "mouse_scroll_event";
    public static String MOUSE_DRAG_EVENT = "mouse_drag_event";
    public static String KEY_DOWN_EVENT = "key_press_event";
    public static String KEY_UP_EVENT = "key_up_event";
    public static String CHILD_POSITION_TRANSFORM_CHANGED_EVENT = "child_position_transform_changed_event";
    public static String CHILD_TRANSFORM_CHANGED_EVENT = "child_transform_changed_event";

    //==================== GLOBAL EVENTS =====================
    public static String FRAME_DIMENSIONS_CHANGE_EVENT = "frame_dimension_change_event";
    public static String ELEMENT_POSITION_TRANSFORM_CHANGED_EVENT = "element_position_transform_changed_event";
    public static String ELEMENT_TRANSFORM_CHANGED_EVENT = "element_transform_changed_event";
    public static String GLOBAL_MOUSE_DOWN_EVENT = "global_mouse_down_event";
    public static String GLOBAL_MOUSE_UP_EVENT = "global_mouse_up_event";
    public static String GLOBAL_MOUSE_MOVE_EVENT = "global_mouse_move_event";
    public static String GLOBAL_MOUSE_SCROLL_EVENT = "global_mouse_scroll_event";
    public static String GLOBAL_MOUSE_DRAG_EVENT = "global_mouse_drag_event";
    public static String GLOBAL_KEY_DOWN_EVENT = "global_key_press_event";
    public static String GLOBAL_KEY_UP_EVENT = "global_key_up_event";

}
