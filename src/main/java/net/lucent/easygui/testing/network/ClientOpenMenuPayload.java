package net.lucent.easygui.testing.network;

import io.netty.buffer.ByteBuf;
import net.lucent.easygui.EasyGui;
import net.lucent.easygui.testing.test_menu.InventoryMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ClientOpenMenuPayload() implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ClientOpenMenuPayload> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(EasyGui.MOD_ID, "open_menu"));
    public static final StreamCodec<ByteBuf, ClientOpenMenuPayload> STREAM_CODEC =  StreamCodec.unit(new ClientOpenMenuPayload());;

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
    public static void handlePayload(ClientOpenMenuPayload payload, IPayloadContext context) {
        context.player().openMenu(new SimpleMenuProvider((a, b, c)->{
            System.out.println("trying to create menu");
            return new InventoryMenu(b);
        }, Component.literal("test")));

    }
}
