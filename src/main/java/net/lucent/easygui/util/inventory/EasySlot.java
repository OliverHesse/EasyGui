package net.lucent.easygui.util.inventory;

import com.mojang.blaze3d.vertex.PoseStack;
import net.lucent.easygui.elements.inventory.DisplaySlot;
import net.lucent.easygui.interfaces.ContainerRenderable;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.lucent.easygui.interfaces.events.Hoverable;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Matrix4f;

public class EasySlot  extends Slot implements IEasySlot {

    public String renderSlotId;
    public EasySlot(Container container, int slot, int x, int y, String renderSlotId) {
        super(container, slot, x, y);
        this.renderSlotId = renderSlotId;
    }


    @Override
    public String getRenderSlotId() {
        return renderSlotId;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public boolean isMouseOver(double mouseX, double mouseY,IEasyGuiScreen screen) {
        if(screen.getElementByID(getRenderSlotId()) instanceof Hoverable hoverable){
            return hoverable.isMouseOver(mouseX,mouseY);
        }
        return false;
    }
}


