package net.lucent.easygui.util;


import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;

import java.util.OptionalDouble;

public class EasyGuiRenderTypes {

    public static final RenderType.CompositeRenderType EASY_GUI_GUI_LINE = RenderType.create(
            "easy_gui_gui_line",
            DefaultVertexFormat.POSITION_COLOR,
            VertexFormat.Mode.LINE_STRIP,
            786432,
            RenderType.CompositeState.builder()
                    .setShaderState(RenderType.RENDERTYPE_LINES_SHADER)
                    .setLineState(new RenderStateShard.LineStateShard(OptionalDouble.of(4.0)))
                    .setTransparencyState(RenderType.TRANSLUCENT_TRANSPARENCY)
                    .setDepthTestState(RenderType.NO_DEPTH_TEST)
                    .setWriteMaskState(RenderType.COLOR_WRITE)
                    .createCompositeState(false)
    );
}
