package net.lucent.easygui.gui.events;

import net.lucent.easygui.gui.RenderableElement;

import java.util.ArrayList;
import java.util.List;

public class EventHelper {

    //creates a direct path from target element too root;
    public static List<RenderableElement> createEventPath(RenderableElement element){
        List<RenderableElement> path = new ArrayList<>();
        RenderableElement currentElement = element;
        while (currentElement != null) {
            path.addFirst(currentElement);
            currentElement = currentElement.getParent();
        }
        return path;
    }
    public static List<RenderableElement> getAllElements(RenderableElement startElement){
        if(startElement == null) return List.of();

        ArrayList<RenderableElement> finalList = new ArrayList<>();
        ArrayList<RenderableElement> notExplored = new ArrayList<>();
        notExplored.add(startElement);

        while(!notExplored.isEmpty()){
            RenderableElement element = notExplored.removeFirst();
            notExplored.addAll(element.getChildren());
            finalList.add(element);
        }
        return finalList;

    }
}
