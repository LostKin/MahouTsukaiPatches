package com.lostkin.durabilityfix.event;

import com.lostkin.durabilityfix.DurabilityFix;
import com.lostkin.durabilityfix.eyes.EyesStorage;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import stepsword.mahoutsukai.potion.ModEffects;

public class ServerEvent {

    @Mod.EventBusSubscriber(modid = DurabilityFix.MODID, value= Dist.DEDICATED_SERVER)
    public static class ServerForgeEvents {

        @SubscribeEvent
       public static void onPlayerTick(TickEvent.PlayerTickEvent event) {

            if (!EyesStorage.eyesType.containsKey(event.player.getUUID())) {
                //EyesStorage.eyesType.put(event.player.getUUID(), null);
                EyesStorage.eyesType.put(event.player.getUUID(),
                        null);
                EyesStorage.eyesStatus.put(event.player.getUUID(), false);
            }

            if (EyesStorage.eyesType.get(event.player.getUUID()) == null) {
                EyesStorage.eyesStatus.put(event.player.getUUID(), false);
            }


            if (EyesStorage.eyesType.get(event.player.getUUID()) != null &&
                    EyesStorage.eyesStatus.get(event.player.getUUID())) {
                event.player.addEffect(new MobEffectInstance(
                        ModEffects.BINDING_EYES.get(),
                        100,
                        0,
                        false,
                        false,
                        false
                ));
            }
       }
    }

}
