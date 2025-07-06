package net.lucent.easygui.elements.inventory;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.math.Axis;
import net.lucent.easygui.EasyGui;
import net.lucent.easygui.elements.BaseRenderable;
import net.lucent.easygui.screens.EasyGuiScreen;
import net.lucent.easygui.util.math.BoundChecker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

public class DisplayEntity extends BaseRenderable {


    public LivingEntity entity;
    public double entityScale;
    public DisplayEntity(EasyGuiScreen easyGuiScreen, LivingEntity entity, int x, int y,double entityScale){
        super(easyGuiScreen);
        setX(x);
        setY(y);
        setWidth((int) (entity.getBbWidth()*entityScale));
        setHeight((int) (entity.getBbHeight()*entityScale));

        this.entity =entity;
        this.entityScale = entityScale;
    }

    @Override
    public void setRenderScale(GuiGraphics guiGraphics) {


        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(getX(),getY()+(entity.getBbHeight()*entityScale/2),0);

        this.positionTransform = new Matrix4f(guiGraphics.pose().last().pose());

        Quaternionf rotation = new Quaternionf()
                .rotateZ((float) Math.toRadians(getRotationZ()))
                .rotateY((float) Math.toRadians(getRotationY()))
                .rotateX((float) Math.toRadians(getRotationX()));
        guiGraphics.pose().rotateAround(rotation,  0f, (float) -(entity.getBbHeight()*entityScale/2),1);
        if(useCustomScaling){
            guiGraphics.pose().scale((float) getScaleX(), (float) getScaleY(),1);
        }

        this.transform = new Matrix4f(guiGraphics.pose().last().pose());

        if(shouldCull()){

            guiGraphics.enableScissor(
                    (getGlobalPoint().x - getCullBorderWidth()),
                    (getGlobalPoint().y - getCullBorderWidth()),
                    (getGlobalHeightWidthPoint().x + getCullBorderWidth()),
                    (getGlobalHeightWidthPoint().y + getCullBorderWidth()));

            this.setCullRegion(new BoundChecker.Rec2d(
                    getGlobalPoint(),
                    getGlobalWidthPoint(),
                    getGlobalHeightWidthPoint(),
                    getGlobalHeightPoint()));
        }

    }

    @Override
    public void renderSelf(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if(entity != null){


            Lighting.setupForEntityInInventory();
            EntityRenderDispatcher entityrenderdispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
            int lightLevel = 15728880;
            float f4 = entity.yBodyRot;
            float f5 = entity.getYRot();
            float f6 = entity.getXRot();
            float f7 = entity.yHeadRotO;
            float f8 = entity.yHeadRot;
            entity.yBodyRot = 180.0F;
            entity.setYRot(180.0F );
            entity.setXRot(0);
            entity.yHeadRot = entity.getYRot();
            entity.yHeadRotO = entity.getYRot();

            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate(entity.getBbWidth()/2,entity.getBbHeight()/2,0);
            guiGraphics.pose().scale((float) entityScale, (float) entityScale, (float) entityScale);
            entityrenderdispatcher.setRenderShadow(false);
            guiGraphics.pose().mulPose(Axis.XP.rotationDegrees(180.0F));
            //guiGraphics.pose().mulPose(Axis.YP.rotationDegrees(180.0F)); // face forward

            entityrenderdispatcher.render(entity,0.0,0.0,0.0,0.0F,1.0F,guiGraphics.pose(),guiGraphics.bufferSource(),lightLevel);
            guiGraphics.bufferSource().endBatch();

            guiGraphics.flush();
            entityrenderdispatcher.setRenderShadow(true);
            guiGraphics.pose().popPose();
            Lighting.setupFor3DItems();
            entity.yBodyRot = f4;
            entity.setYRot(f5);
            entity.setXRot(f6);
            entity.yHeadRotO = f7;
            entity.yHeadRot = f8;



        }
    }
}
