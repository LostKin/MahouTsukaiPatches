package com.lostkin.durabilityfix.eyes;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.registries.RegistryObject;
import stepsword.mahoutsukai.potion.ModEffects;

import java.util.HashMap;
import java.util.Map;
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
        return 0;
    }

    public static int getEyeCost(RegistryObject<MobEffect> effect) {
        return 0;
    }

}
