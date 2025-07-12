package net.lucent.easygui.util.math;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderType;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class Curves {


    //try to draw pixelated lines !?
    //generate the vectors
    //run some sort of algo to pixelate it?
    public static class Line{
        public DoubleVec2 p1;
        public DoubleVec2 p2;
        public Line(DoubleVec2 p1, DoubleVec2 p2){
            this.p1 = p1;
            this.p2 = p2;
        }

        public DoubleVec2 getTraveledPoint(double progress){
            return new DoubleVec2(p1.x+(p2.x-p1.x)*progress,p1.y+(p2.y-p1.y)*progress);
        }
    }
    public static class DoubleVec2 {
        public double x;
        public double y;
        public DoubleVec2(double x, double y){
            this.x = x;
            this.y = y;

        }
        public DoubleVec2(Vector3f v){
            this( v.x(), v.y());

        }
        public DoubleVec2(BoundChecker.Vec2 p){
            this.x = p.x;
            this.y = p.y;

        }

    }

    public static void drawLine(GuiGraphics guiGraphics,DoubleVec2 p1, DoubleVec2 p2,boolean horizontal){

        guiGraphics.pose().pushPose();
        Matrix4f pose = guiGraphics.pose().last().pose();

        VertexConsumer vc = guiGraphics.bufferSource().getBuffer(RenderType.guiOverlay());
        if(!horizontal) {
            vc.addVertex(pose, (float) p1.x, (float) p1.y, 0).setColor(255, 0, 0, 255);
            vc.addVertex(pose, (float) p1.x, (float) p1.y + 1, 0).setColor(255, 0, 0, 255);

            vc.addVertex(pose, (float) p2.x, (float) p2.y + 1, 0).setColor(255, 0, 0, 255);
            vc.addVertex(pose, (float) p2.x, (float) p2.y, 0).setColor(255, 0, 0, 255);
        }else{
            vc.addVertex(pose, (float) p1.x, (float) p1.y, 0).setColor(255, 0, 0, 255);
            vc.addVertex(pose, (float) p1.x+ 1, (float) p1.y , 0).setColor(255, 0, 0, 255);

            vc.addVertex(pose, (float) p2.x+1, (float) p2.y, 0).setColor(255, 0, 0, 255);
            vc.addVertex(pose, (float) p2.x, (float) p2.y, 0).setColor(255, 0, 0, 255);
        }
        guiGraphics.bufferSource().endBatch();
        guiGraphics.pose().popPose();


    }

    public static List<DoubleVec2> generateCurve3Construction( BoundChecker.Vec2 p1, BoundChecker.Vec2 p2,int segments){
        List<Line> constructionLines = new ArrayList<>(){
            {
                add(new Line(new DoubleVec2(p1),new DoubleVec2(p1.x+ (double) (p2.x - p1.x) /2,p1.y)));
                add(new Line(new DoubleVec2(p1.x+ (double) (p2.x - p1.x) /2,p1.y),new DoubleVec2(p1.x+ (double) (p2.x - p1.x) /2,p2.y)));
                add(new Line(new DoubleVec2(p1.x+ (double) (p2.x - p1.x) /2,p2.y),new DoubleVec2(p2)));
            }
        };
        //since i have fixed n construction lines i will make it flat instead of recursive
        List<DoubleVec2> points = new ArrayList<>();
        double increment = 1.0/segments;
        for(double t = 0; t< 1; t += increment){

            DoubleVec2 constructionP1 = constructionLines.getFirst().getTraveledPoint(t);
            DoubleVec2 constructionP2 = constructionLines.get(1).getTraveledPoint(t);
            DoubleVec2 constructionP3 = constructionLines.get(2).getTraveledPoint(t);

            Line layer2C1 = new Line(constructionP1,constructionP2);
            Line layer2C2 = new Line(constructionP2,constructionP3);

            Line finalLayerC = new Line(layer2C1.getTraveledPoint(t),layer2C2.getTraveledPoint(t));

            DoubleVec2 point = finalLayerC.getTraveledPoint(t);
            points.add(point);

        }



        return  points;
    }

    public static void drawCurve(GuiGraphics guiGraphics, BoundChecker.Vec2 p1, BoundChecker.Vec2 p2,int segments,boolean horizontal){
        guiGraphics.pose().pushPose();

        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        List<DoubleVec2> points = p1.x < p2.x ? generateCurve3Construction(p1,p2,segments) : generateCurve3Construction(p2,p1,segments);

        for(int i = 0;i<points.size()-1;i++){
            drawLine(guiGraphics,points.get(i),points.get(i+1),horizontal);
        }

        guiGraphics.pose().popPose();

    }
}
