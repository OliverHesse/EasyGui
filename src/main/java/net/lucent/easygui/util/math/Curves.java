package net.lucent.easygui.util.math;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.lucent.easygui.util.EasyGuiRenderTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.vehicle.Minecart;
import org.joml.Matrix4f;

public class Curves {


    public static void drawCurve(GuiGraphics guiGraphics, BoundChecker.Vec2 p1, BoundChecker.Vec2 p2){

        System.out.println("TRYING TO DRAW CURVE");
        guiGraphics.pose().pushPose();

        Matrix4f pose = guiGraphics.pose().last().pose();

        VertexConsumer vc = guiGraphics.bufferSource().getBuffer(EasyGuiRenderTypes.EASY_GUI_GUI_LINE);
        vc.addVertex(pose, 0, 0, 0).setColor(255, 0, 0, 255);
        vc.addVertex(pose, 0, 50, 0).setColor(255, 0, 0, 255);


        vc.addVertex(pose, 0, 50, 0).setColor(255, 0, 0, 255);
        vc.addVertex(pose, 50, 50, 0).setColor(255, 0, 0, 255);

        vc.addVertex(pose, 50, 50, 0).setColor(255, 0, 0, 255);
        vc.addVertex(pose, 50, 0, 0).setColor(255, 0, 0, 255);


        vc.addVertex(pose, 50, 0, 0).setColor(255, 0, 0, 255);
        vc.addVertex(pose, 0, 0, 0).setColor(255, 0, 0, 255);

        guiGraphics.flush();
        guiGraphics.pose().popPose();


    }
        /*

        vc.addVertex(pose, 0, 0, 0).setColor(255, 0, 0, 255);
        vc.addVertex(pose, 0, 50, 0).setColor(255, 0, 0, 255);


        vc.addVertex(pose, 0, 50, 0).setColor(255, 0, 0, 255);
        vc.addVertex(pose, 50, 50, 0).setColor(255, 0, 0, 255);

        vc.addVertex(pose, 50, 50, 0).setColor(255, 0, 0, 255);
        vc.addVertex(pose, 50, 0, 0).setColor(255, 0, 0, 255);


        vc.addVertex(pose, 50, 0, 0).setColor(255, 0, 0, 255);
        vc.addVertex(pose, 0, 0, 0).setColor(255, 0, 0, 255);

         */
    public static void drawCurve(GuiGraphics guiGraphics, BoundChecker.Vec2 p1, BoundChecker.Vec2 p2,int itr) {

    }
}
