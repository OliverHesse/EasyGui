package net.lucent.easygui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.lucent.easygui.gui.UIFrame;
import org.joml.Matrix4f;

public interface IEasyScreen {

    UIFrame getUIFrame();
    void setUIFrame(UIFrame frame);
}
