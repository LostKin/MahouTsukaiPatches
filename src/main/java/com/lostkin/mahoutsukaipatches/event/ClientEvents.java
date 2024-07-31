package com.lostkin.mahoutsukaipatches.event;

import com.lostkin.mahoutsukaipatches.MahouTsukaiPatches;
import com.lostkin.mahoutsukaipatches.client.EyesHudOverlay;
import com.lostkin.mahoutsukaipatches.client.ProtectionHudOverlay;
import com.lostkin.mahoutsukaipatches.networking.ModMessages;
import com.lostkin.mahoutsukaipatches.networking.packet.mystic_eyes.EyesStatusC2SPacket;
import com.lostkin.mahoutsukaipatches.networking.packet.protective_displacement.ProtectionStatusC2SPacket;
import com.lostkin.mahoutsukaipatches.util.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents {

    @Mod.EventBusSubscriber(modid = MahouTsukaiPatches.MODID, value= Dist.CLIENT)
    public static class ClientForgeEvents {

        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {

            event.register(KeyBinding.EYES_KEY);
            event.register(KeyBinding.PROTECTION_KEY);
        }

        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            if (KeyBinding.EYES_KEY.consumeClick()) {
                ModMessages.sendToServer(new EyesStatusC2SPacket());
            }
            if (KeyBinding.PROTECTION_KEY.consumeClick()) {
                ModMessages.sendToServer(new ProtectionStatusC2SPacket());
            }
            
        }
    }

    @Mod.EventBusSubscriber(modid = MahouTsukaiPatches.MODID, value= Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBinding.EYES_KEY);
            event.register(KeyBinding.PROTECTION_KEY);
        }

        @SubscribeEvent
        public static  void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
            event.registerAboveAll("mystic_eyes", EyesHudOverlay.HUD_EYES);
            event.registerAboveAll("protective_displacement", ProtectionHudOverlay.HUD_EYES);
        }

    }

}
