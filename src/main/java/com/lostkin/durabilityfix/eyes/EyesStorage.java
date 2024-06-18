package com.lostkin.durabilityfix.eyes;

import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EyesStorage {
    public static HashMap<UUID, RegistryObject<MobEffect>> eyesType = new HashMap<UUID, RegistryObject<MobEffect>>();
    public static HashMap<UUID, Boolean> eyesStatus = new HashMap<UUID, Boolean>();

    public static void flipEyesStatus(UUID id) {
        Boolean currentStatus = eyesStatus.get(id);
        Boolean newStatus = !currentStatus;
        eyesStatus.put(id, newStatus);
    }

}
