package net.lucent.easygui.handlers.event_handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public abstract class AbstractGuiEventHandler<T>{
    public List<T> HANDLER_LIST = new ArrayList<>();

    protected ListIterator<T> getLastIterator(){
        return HANDLER_LIST.listIterator(HANDLER_LIST.size());
    }

    public void register(T obj){
        if(isObjRegistered(obj)) return;
        HANDLER_LIST.add(obj);
    }

    public void unregister(T obj){
        HANDLER_LIST.remove(obj);
    }

    private boolean isObjRegistered(T obj){

        for(T listener : HANDLER_LIST){
            if(listener == obj){
                return true;
            }
        }
        return false;
    }

}
