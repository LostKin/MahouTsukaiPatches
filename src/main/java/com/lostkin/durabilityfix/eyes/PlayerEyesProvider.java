package com.lostkin.durabilityfix.eyes;

import net.minecraft.client.particle.MobAppearanceParticle;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.level.NoteBlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stepsword.mahoutsukai.potion.ModEffects;

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
