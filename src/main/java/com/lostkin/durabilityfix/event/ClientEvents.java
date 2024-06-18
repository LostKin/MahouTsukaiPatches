package com.lostkin.durabilityfix.event;

import com.lostkin.durabilityfix.DurabilityFix;
import com.lostkin.durabilityfix.networking.ModMessages;
import com.lostkin.durabilityfix.networking.packet.EyesStatusC2SPacket;
import com.lostkin.durabilityfix.util.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents {

    @Mod.EventBusSubscriber(modid = DurabilityFix.MODID, value= Dist.CLIENT)
    public static class ClientForgeEvents {

        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBinding.EYES_KEY);
        }

        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            if (KeyBinding.EYES_KEY.consumeClick()) {
                ModMessages.sendToServer(new EyesStatusC2SPacket());
            }
        }
    }

    @Mod.EventBusSubscriber(modid = DurabilityFix.MODID, value= Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBinding.EYES_KEY);
        }
    }

}
