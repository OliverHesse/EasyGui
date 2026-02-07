package net.lucent.easygui.interfaces;

import net.lucent.easygui.elements.BaseRenderable;
import net.lucent.easygui.elements.containers.View;
import net.lucent.easygui.elements.tooltips.EasyTooltip;
import net.lucent.easygui.util.math.BoundChecker;
import net.minecraft.world.phys.Vec2;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import oshi.util.tuples.Pair;

import java.util.ArrayList;
import java.util.List;
@OnlyIn(Dist.CLIENT)
public interface IEasyGuiScreen {

    void register(ContainerRenderable renderable);
    void unregister(ContainerRenderable renderable);

    default void setActiveView(View view){}

    void setTooltip(EasyTooltip tooltip);

    void addView(View view);

    View getActiveView();

    void removeView(View view);

    void childIdSet(ContainerRenderable obj,String id);
    void childClassAdded(ContainerRenderable obj,String className);
    void childClassRemoved(ContainerRenderable obj,String className);

    ContainerRenderable getElementByID(String id);
    List<ContainerRenderable> getElementsByClassName(String className);

    default Pair<Vec2,Vec2> getFurthestAndNearestPoint(){
        View view = getActiveView();
        if(view == null) return new Pair<>(new Vec2(0,0),new Vec2(0,0));
        ArrayList<ContainerRenderable> unVisitedNodes = new ArrayList<>();
        unVisitedNodes.add(view);
        int furthestX = 0;
        int furthestY = 0;
        int closestX = Integer.MAX_VALUE;
        int closestY = Integer.MAX_VALUE;
        while(!unVisitedNodes.isEmpty()){
            ContainerRenderable renderable =  unVisitedNodes.removeFirst();
            unVisitedNodes.addAll(renderable.getChildren());
            if(!(renderable instanceof BaseRenderable baseRenderable)) continue;
            BoundChecker.Vec2 point = baseRenderable.getGlobalPoint();
            BoundChecker.Vec2 point2 = baseRenderable.getGlobalHeightWidthPoint();
            int nodeXPoint = point.x + point2.x;
            int nodeYPoint = point.y + point2.y;
            if(nodeXPoint > furthestX) furthestX = nodeXPoint;
            if(nodeYPoint > furthestY) furthestY = nodeYPoint;
            if(point.x < closestX) closestX = point.x;
            if(point.y < closestY) closestY = point.y;

        }
        return new Pair<>(new Vec2(closestX,closestY),new Vec2(furthestX,furthestY));
    }
    

}
