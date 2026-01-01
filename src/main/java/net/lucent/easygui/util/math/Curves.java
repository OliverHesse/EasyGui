package net.lucent.easygui.util.math;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.phys.Vec2;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class Curves {


    //try to draw pixelated lines !?
    //generate the vectors
    //run some sort of algo to pixelate it?
    public static class Line{
        public Vec2 p1;
        public Vec2 p2;
        public Line(Vec2 p1, Vec2 p2){
            this.p1 = p1;
            this.p2 = p2;
        }

        public Vec2 getTraveledPoint(double progress){
            return new Vec2((float) (p1.x+(p2.x-p1.x)*progress), (float) (p1.y+(p2.y-p1.y)*progress));
        }
    }

    public static void drawLine(GuiGraphics guiGraphics,Vec2 p1, Vec2 p2,boolean horizontal){

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

    public static List<Vec2> generateCurve3Construction( Vec2 p1, Vec2 p2,int segments){
        List<Line> constructionLines = new ArrayList<>(){
            {
                add(new Line(p1.add(Vec2.ZERO),new Vec2((float) (p1.x+ (double) (p2.x - p1.x) /2),p1.y)));
                add(new Line(new Vec2((float) (p1.x+ (double) (p2.x - p1.x) /2),p1.y),new Vec2((float) (p1.x+ (double) (p2.x - p1.x) /2),p2.y)));
                add(new Line(new Vec2((float) (p1.x+ (double) (p2.x - p1.x) /2),p2.y),p2.add(Vec2.ZERO)));
            }
        };
        //since i have fixed n construction lines i will make it flat instead of recursive
        List<Vec2> points = new ArrayList<>();
        double increment = 1.0/segments;
        for(double t = 0; t< 1; t += increment){

            Vec2 constructionP1 = constructionLines.getFirst().getTraveledPoint(t);
            Vec2 constructionP2 = constructionLines.get(1).getTraveledPoint(t);
            Vec2 constructionP3 = constructionLines.get(2).getTraveledPoint(t);

            Line layer2C1 = new Line(constructionP1,constructionP2);
            Line layer2C2 = new Line(constructionP2,constructionP3);

            Line finalLayerC = new Line(layer2C1.getTraveledPoint(t),layer2C2.getTraveledPoint(t));

            Vec2 point = finalLayerC.getTraveledPoint(t);
            points.add(point);

        }



        return  points;
    }

    public static void drawCurve(GuiGraphics guiGraphics, Vec2 p1, Vec2 p2, int segments, boolean horizontal){
        guiGraphics.pose().pushPose();

        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        List<Vec2> points = p1.x < p2.x ? generateCurve3Construction(p1,p2,segments) : generateCurve3Construction(p2,p1,segments);

        for(int i = 0;i<points.size()-1;i++){
            drawLine(guiGraphics,points.get(i),points.get(i+1),horizontal);
        }

        guiGraphics.pose().popPose();

    }
}
