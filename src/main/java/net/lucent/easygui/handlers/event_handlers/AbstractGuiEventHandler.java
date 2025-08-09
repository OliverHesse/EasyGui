package net.lucent.easygui.handlers.event_handlers;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
@OnlyIn(Dist.CLIENT)
public abstract class AbstractGuiEventHandler<T>{
    private List<T> HANDLER_LIST = new ArrayList<>();
    private List<T> HANDLER_BUFFER_LIST = new ArrayList<>();
    private List<T> HANDLER_REMOVE_LIST = new ArrayList<>();
    public List<T> getHandlerList(){
        if(!HANDLER_BUFFER_LIST.isEmpty()){
            HANDLER_LIST.addAll(HANDLER_BUFFER_LIST);
            HANDLER_BUFFER_LIST.clear();
        }
        for(T item : HANDLER_REMOVE_LIST){
            HANDLER_LIST.remove(item);
        }
        HANDLER_REMOVE_LIST.clear();
        return List.copyOf(HANDLER_LIST);
    }

    protected ListIterator<T> getLastIterator(){
        if(!HANDLER_BUFFER_LIST.isEmpty()){
            HANDLER_LIST.addAll(HANDLER_BUFFER_LIST);
            HANDLER_BUFFER_LIST.clear();
        }
        for(T item : HANDLER_REMOVE_LIST){
            HANDLER_LIST.remove(item);
        }
        HANDLER_REMOVE_LIST.clear();
        return HANDLER_LIST.listIterator(HANDLER_LIST.size());
    }

    public void register(T obj){
        if(isObjRegistered(obj)) return;
        HANDLER_BUFFER_LIST.add(obj);
    }

    public void unregister(T obj){
        HANDLER_REMOVE_LIST.remove(obj);
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
