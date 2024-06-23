package com.lostkin.mahoutsukaipatches.eyes;

import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.RegistryObject;
import stepsword.mahoutsukai.config.MTConfig;
import stepsword.mahoutsukai.potion.ModEffects;

import java.util.HashMap;
import java.util.UUID;

public class EyesStorage {
    public static HashMap<UUID, RegistryObject<MobEffect>> eyesType = new HashMap<UUID, RegistryObject<MobEffect>>();
    public static HashMap<UUID, Boolean> eyesStatus = new HashMap<UUID, Boolean>();
    public static HashMap<UUID, Integer> eyesCostCounter = new HashMap<UUID, Integer>();

    public static void flipEyesStatus(UUID id) {
        Boolean currentStatus = eyesStatus.get(id);
        Boolean newStatus = !currentStatus;
        eyesStatus.put(id, newStatus);
    }

    public static int getEyeDuration(RegistryObject<MobEffect> effect) {
        if (effect == ModEffects.FAY_SIGHT_EYES) {
            return MTConfig.FAY_SIGHT_TIME;
        }
        if (effect == ModEffects.BINDING_EYES) {
            return MTConfig.MYSTIC_EYES_TIME;
        }
        if (effect == ModEffects.BLACK_FLAME_EYES) {
            return MTConfig.BLACK_FLAME_TIME;
        }
        if (effect == ModEffects.DEATH_COLLECTION_EYES) {
            return MTConfig.DEATH_COLLECTION_TIME;
        }
        if (effect == ModEffects.CLAIRVOYANCE) {
            return MTConfig.CLAIRVOYANCE_TIME;
        }
        if (effect == ModEffects.INSIGHT) {
            return MTConfig.INSIGHT_TIME;
        }
        if (effect == ModEffects.REVERSION_EYES) {
            return MTConfig.REVERSION_EYES_TIME;
        }
        return 0;
    }

    public static int getEyeCost(RegistryObject<MobEffect> effect) {
        if (effect == ModEffects.FAY_SIGHT_EYES) {
            return MTConfig.FAY_SIGHT_MANA_COST;
        }
        if (effect == ModEffects.BINDING_EYES) {
            return MTConfig.MYSTIC_EYES_MANA_COST;
        }
        if (effect == ModEffects.BLACK_FLAME_EYES) {
            return MTConfig.BLACK_FLAME_MANA_COST;
        }
        if (effect == ModEffects.DEATH_COLLECTION_EYES) {
            return MTConfig.DEATH_COLLECTION_MANA_COST;
        }
        if (effect == ModEffects.CLAIRVOYANCE) {
            return MTConfig.CLAIRVOYANCE_MANA_COST;
        }
        if (effect == ModEffects.INSIGHT) {
            return MTConfig.INSIGHT_MANA_COST;
        }
        if (effect == ModEffects.REVERSION_EYES) {
            return MTConfig.REVERSION_EYES_MANA_COST;
        }
        return 0;
    }

}
