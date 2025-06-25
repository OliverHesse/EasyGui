package net.lucent.easygui.templating.actions;

import net.lucent.easygui.interfaces.ContainerRenderable;

public class Action {

    private final Object[] args;

    private final IAction action;

    public Action(IAction action,Object[] args){
        this.action =  action;
        this.args = args;
    }
    public static Object[] mergeArrays(Object[] first, Object[] second) {
        Object[] merged = new Object[first.length + second.length];
        System.arraycopy(first, 0, merged, 0, first.length);
        System.arraycopy(second, 0, merged, first.length, second.length);
        return merged;
    }
    public void accept(ContainerRenderable renderable, Object... args){

        action.accept(renderable,args,this.args);
    }



}
