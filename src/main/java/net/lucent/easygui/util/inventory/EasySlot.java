package net.lucent.easygui.util.inventory;

import com.mojang.blaze3d.vertex.PoseStack;
import net.lucent.easygui.elements.inventory.DisplaySlot;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import org.joml.Matrix4f;

public class EasySlot  extends Slot {
    public Matrix4f posPose = new Matrix4f().identity();
    public Matrix4f pose = new Matrix4f().identity();
    public DisplaySlot slot;
    public EasySlot(Container container, int slot, int x, int y, DisplaySlot displaySlot) {
        super(container, slot, x, y);
        this.slot = displaySlot;
    }

    public void setPose(Matrix4f pose1,Matrix4f pose2){
        this.pose =pose1;
        this.posPose = pose2;
    }
    public void updatePose(){
        this.pose = slot.getTransform();
        this.posPose = slot.getPositionTransform();
    }
}
