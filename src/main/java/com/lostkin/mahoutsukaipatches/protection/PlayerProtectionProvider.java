package com.lostkin.mahoutsukaipatches.protection;

import com.lostkin.mahoutsukaipatches.eyes.PlayerEyes;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerProtectionProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerProtection> PLAYER_PROTECTION = CapabilityManager.get(new CapabilityToken<PlayerProtection>() {});

    private PlayerProtection protection = null;
    private final LazyOptional<PlayerProtection> optional = LazyOptional.of(this::createPlayerProtection);

    private PlayerProtection createPlayerProtection() {
        if (this.protection == null) {
            this.protection = new PlayerProtection();
        }
        return this.protection;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
        if (capability == PLAYER_PROTECTION) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerProtection().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerProtection().loadNBTData(nbt);
    }

}
