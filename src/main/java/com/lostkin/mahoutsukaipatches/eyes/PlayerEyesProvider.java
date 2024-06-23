package com.lostkin.mahoutsukaipatches.eyes;

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

public class PlayerEyesProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerEyes> PLAYER_EYES = CapabilityManager.get(new CapabilityToken<PlayerEyes>() {});

    private PlayerEyes eyes = null;
    private final LazyOptional<PlayerEyes> optional = LazyOptional.of(this::createPlayerEyes);

    private PlayerEyes createPlayerEyes() {
        if (this.eyes == null) {
            this.eyes = new PlayerEyes();
        }
        return this.eyes;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
        if (capability == PLAYER_EYES) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerEyes().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerEyes().loadNBTData(nbt);
    }
}
