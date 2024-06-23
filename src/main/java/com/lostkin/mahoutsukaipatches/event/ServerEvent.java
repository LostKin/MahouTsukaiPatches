package com.lostkin.mahoutsukaipatches.event;

import com.lostkin.mahoutsukaipatches.MahouTsukaiPatches;
import com.lostkin.mahoutsukaipatches.eyes.EyesStorage;
import com.lostkin.mahoutsukaipatches.eyes.PlayerEyes;
import com.lostkin.mahoutsukaipatches.eyes.PlayerEyesProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import stepsword.mahoutsukai.capability.mahou.PlayerManaManager;

public class ServerEvent {

    @Mod.EventBusSubscriber(modid = MahouTsukaiPatches.MODID, value= Dist.DEDICATED_SERVER)
    public static class ServerForgeEvents {

        @SubscribeEvent
        public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
            if (event.player.level.isClientSide()) {
                return;
            }

            event.player.getCapability(PlayerEyesProvider.PLAYER_EYES).ifPresent(eyes -> {
                if (eyes.getEyeType() != null && eyes.getEyeStatus()) {
                    //Trying to activate eyes

                    boolean canAfford = true;

                    if (eyes.getEyeCostCooldown() <= 0) {
                        //need to spend mana
                        int manaCost = EyesStorage.getEyeCost(eyes.getEyeType());

                        int manaDrained = PlayerManaManager.drainMana(event.player, manaCost, false, false);

                        if (manaDrained <= 0) {
                            eyes.setEyeStatus(false);
                            eyes.setEyeCostCooldown(0);
                            canAfford = false;
                        } else {
                            eyes.setEyeCostCooldown(2 * EyesStorage.getEyeDuration(eyes.getEyeType()));
                        }
                    }

                    if (canAfford) {
                        eyes.setEyeCostCooldown(eyes.getEyeCostCooldown() - 1);
                        event.player.addEffect(new MobEffectInstance(
                                eyes.getEyeType().get(),
                                1,
                                0,
                                false,
                                false,
                                false
                        ));
                    }
                }
            });
        }

        @SubscribeEvent
        public static void onAttachCapability(AttachCapabilitiesEvent<Entity> event) {
            if (event.getObject() instanceof Player) {
                if (!event.getObject().getCapability(PlayerEyesProvider.PLAYER_EYES).isPresent()) {
                    event.addCapability(new ResourceLocation(MahouTsukaiPatches.MODID, "properties"), new PlayerEyesProvider());
                }
            }
        }

        @SubscribeEvent
        public static void onPlayerCloned(PlayerEvent.Clone event) {
            if (event.isWasDeath()) {
                event.getOriginal().reviveCaps();
                event.getOriginal().getCapability(PlayerEyesProvider.PLAYER_EYES).ifPresent(oldScore -> {
                    if (oldScore.getEyeType() != null) {
                    }
                    event.getEntity().getCapability(PlayerEyesProvider.PLAYER_EYES).ifPresent(newScore -> {
                        newScore.copyFrom(oldScore);
                    });
                });
                event.getOriginal().invalidateCaps();

            }
        }

        @SubscribeEvent
        public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
            event.register(PlayerEyes.class);
        }

    }

}