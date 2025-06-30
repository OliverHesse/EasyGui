package net.lucent.easygui.util.math;

import org.joml.Vector3f;
import org.joml.Vector4f;

public class BoundChecker {

    public static class Vec2{
        public int x;
        public int y;
        public Vec2(int x,int y){
            this.x = x;
            this.y = y;

        }
        public Vec2(Vector3f v){
            this((int) v.x(),(int) v.y());

        }


    }
    public static class Rec2d{
        public Vec2 p1;
        public Vec2 p2;
        public Vec2 p3;
        public Vec2 p4;
        public Rec2d(Vec2 p1,Vec2 p2,Vec2 p3, Vec2 p4){
            this.p1 = p1;
            this.p2 = p2;
            this.p3 = p3;
            this.p4 = p4;
        }
    }

    /*
        works even if it is rotated
        uses the area sum method
        split into 2 triangles. check if it is in one of them.

     */
    public static boolean containsPoint(Rec2d rec, Vec2 checkpoint){
        return containsPoint(rec.p1,rec.p2,rec.p3,rec.p4,checkpoint);
    }


    public static boolean containsPoint(Vec2 p1,Vec2 p2, Vec2 p3 ,Vec2 p4, Vec2 checkPoint){
        //triangle 1 p1, p2, p3,
        //triangle 2 p3,p4,p1

        return containsPoint(p1,p2,p3,checkPoint) || containsPoint(p3,p4,p1,checkPoint);

    }

    public static double getArea(Vec2 p1, Vec2 p2, Vec2 p3){
        return Math.abs(0.5*(p1.x*(p2.y-p3.y)+p2.x*(p3.y-p1.y)+p3.x*(p1.y-p2.y)));
    }

    public static boolean containsPoint(Vec2 p1, Vec2 p2, Vec2 p3, Vec2 checkPoint){
            double targetArea = getArea(p1,p2,p3);
            //calc 3 sub triangles

            double a1 = getArea(p1,p2,checkPoint);
            double a2 = getArea(p1,p3,checkPoint);
            double a3 = getArea(p2,p3,checkPoint);

            return (a1+a2+a3) <= targetArea;


    }

}
