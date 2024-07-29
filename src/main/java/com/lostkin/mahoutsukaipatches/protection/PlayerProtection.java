package com.lostkin.mahoutsukaipatches.protection;

import com.lostkin.mahoutsukaipatches.eyes.PlayerEyes;
import com.lostkin.mahoutsukaipatches.eyes.PlayerEyesProvider;
import net.minecraft.nbt.CompoundTag;

public class PlayerProtection {
    private boolean protectionStatus = false;
    private boolean protectionUnlocked = false;

    public boolean getProtectionStatus() {
        return protectionStatus;
    }

    public void setProtectionStatus(boolean status) {
        protectionStatus = status;
    }

    public boolean getProtectionUnlocked() {
        return protectionUnlocked;
    }

    public void setProtectionUnlocked(boolean unlocked) {
        protectionUnlocked = unlocked;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putBoolean("protectionStatus", protectionStatus);
        nbt.putBoolean("protectionUnlocked", protectionUnlocked);
    }

    public void loadNBTData(CompoundTag nbt) {
        protectionStatus = nbt.getBoolean("protectionStatus");
        protectionUnlocked = nbt.getBoolean("protectionUnlocked");
    }

    public void copyFrom(PlayerProtection oldScore) {
        protectionStatus = oldScore.protectionStatus;
        protectionUnlocked = oldScore.protectionUnlocked;
    }

}
