package com.lostkin.durabilityfix.eyes;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.RegistryObject;
import stepsword.mahoutsukai.potion.ModEffects;

public class PlayerEyes {
    private RegistryObject<MobEffect> eyeType = null;
    private boolean eyeStatus = false;
    private int eyeCostCooldown = 0;

    public RegistryObject<MobEffect> getEyeType() {
        return eyeType;
    }

    public boolean getEyeStatus() {
        return eyeStatus;
    }

    public int getEyeCostCooldown() {
        return eyeCostCooldown;
    }

    public void setEyeType(RegistryObject<MobEffect> effect) {
        eyeType = effect;
    }

    public void setEyeStatus(boolean status) {
        eyeStatus = status;
    }

    public void setEyeCostCooldown(int costCooldown) {
        eyeCostCooldown = costCooldown;
    }

    private int serializeEffect(RegistryObject<MobEffect> effect) {
        if (effect == ModEffects.FAY_SIGHT_EYES) {
            return 1;
        }
        if (effect == ModEffects.BINDING_EYES) {
            return 2;
        }
        if (effect == ModEffects.BLACK_FLAME_EYES) {
            return 3;
        }
        if (effect == ModEffects.DEATH_COLLECTION_EYES) {
            return 4;
        }
        if (effect == ModEffects.CLAIRVOYANCE) {
            return 5;
        }
        if (effect == ModEffects.INSIGHT) {
            return 6;
        }
        if (effect == ModEffects.REVERSION_EYES) {
            return 7;
        }
        return 0;
    }

    private RegistryObject<MobEffect> deserializeEffect(int type) {
        switch(type){
            case 1: return ModEffects.FAY_SIGHT_EYES;
            case 2: return ModEffects.BINDING_EYES;
            case 3: return ModEffects.BLACK_FLAME_EYES;
            case 4: return ModEffects.DEATH_COLLECTION_EYES;
            case 5: return ModEffects.CLAIRVOYANCE;
            case 6: return ModEffects.INSIGHT;
            case 7: return ModEffects.REVERSION_EYES;
        }
        return null;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("eyeCostCooldown", eyeCostCooldown);
        nbt.putBoolean("eyeStatus", eyeStatus);
        nbt.putInt("eyeType", serializeEffect(eyeType));
    }

    public void loadNBTData(CompoundTag nbt) {
        eyeType = deserializeEffect(nbt.getInt("eyeType"));
        eyeStatus = nbt.getBoolean("eyeStatus");
        eyeCostCooldown = nbt.getInt("eyeCostCooldown");
    }

    public void copyFrom(PlayerEyes oldScore) {
        eyeType = oldScore.eyeType;
        eyeStatus = oldScore.eyeStatus;
        eyeCostCooldown = oldScore.eyeCostCooldown;
    }
}
