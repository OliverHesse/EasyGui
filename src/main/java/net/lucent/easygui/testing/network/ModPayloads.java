package net.lucent.easygui.testing.network;

import net.lucent.easygui.EasyGui;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class ModPayloads {

    @SubscribeEvent
    public static void registerPayloads(final RegisterPayloadHandlersEvent event){
        final PayloadRegistrar registrar =event.registrar(EasyGui.MOD_ID).versioned("1.0");
        registrar.playToServer(
                ClientOpenMenuPayload.TYPE,
                ClientOpenMenuPayload.STREAM_CODEC,
                ClientOpenMenuPayload::handlePayload

        );
    }
}
