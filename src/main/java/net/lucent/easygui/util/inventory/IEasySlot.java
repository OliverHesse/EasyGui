package net.lucent.easygui.util.inventory;

import net.lucent.easygui.interfaces.ContainerRenderable;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.minecraft.world.inventory.Slot;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Matrix4f;

public interface IEasySlot {

    String getRenderSlotId();

    @OnlyIn(Dist.CLIENT)
    default Matrix4f getPosPose(IEasyGuiScreen screen){
        return screen.getElementByID(getRenderSlotId()).getPositionTransform();
    }
    @OnlyIn(Dist.CLIENT)
    default Matrix4f getPose(IEasyGuiScreen screen){
        return screen.getElementByID(getRenderSlotId()).getTransform();
    }
    @OnlyIn(Dist.CLIENT)
     boolean isMouseOver( double mouseX, double mouseY,IEasyGuiScreen screen);
}
