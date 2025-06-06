package net.Lucent.EasyGui.handlers;

import net.Lucent.EasyGui.interfaces.events.MouseDragListener;

import java.util.ListIterator;

/**
 * executes basic logic needed for mouse dragging
 * can hold a currently dragged element most elements won't run if this is true
 */
public class MouseDraggedEventHandler extends AbstractGuiEventHandler<MouseDragListener>{

    public MouseDragListener dragged = null;

    public MouseDragListener getCurrentlyDragged(){
        return dragged;
    }

    public  void call(double mouseX, double mouseY, int button, double dragX, double dragY){
        ListIterator<MouseDragListener> itr = getLastIterator();
        while(itr.hasPrevious()){
            MouseDragListener listener = itr.previous();
            if(listener.isActive()) listener.onDrag(mouseX,mouseY,button,dragX,dragY);
        }

    }
}
