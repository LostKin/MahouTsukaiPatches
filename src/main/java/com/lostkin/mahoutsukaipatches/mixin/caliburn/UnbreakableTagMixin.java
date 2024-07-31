package com.lostkin.mahoutsukaipatches.mixin.caliburn;


import com.lostkin.mahoutsukaipatches.MahouTsukaiPatches;
import com.lostkin.mahoutsukaipatches.networking.ModMessages;
import com.lostkin.mahoutsukaipatches.networking.packet.protective_displacement.ProtectionStatusS2CPacket;
import com.lostkin.mahoutsukaipatches.protection.PlayerProtectionProvider;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import stepsword.mahoutsukai.capability.caliburn.CaliburnMahouStorage;
import stepsword.mahoutsukai.capability.caliburn.ICaliburnMahou;
import stepsword.mahoutsukai.capability.mahou.IMahou;
import stepsword.mahoutsukai.capability.mahou.PlayerManaManager;
import stepsword.mahoutsukai.config.MTConfig;
import stepsword.mahoutsukai.effects.displacement.ProtectiveDisplacementSpellEffect;
import stepsword.mahoutsukai.effects.projection.PowerConsolidationSpellEffect;
import stepsword.mahoutsukai.item.ModItems;
import stepsword.mahoutsukai.item.spells.projection.PowerConsolidation.Caliburn;
import stepsword.mahoutsukai.util.Utils;

import java.util.concurrent.atomic.AtomicBoolean;

@Mixin(Caliburn.class)
public class UnbreakableTagMixin {

    @Inject(method = "initCapabilities", at = @At("HEAD"), remap = false, cancellable = true)
    public void initCapabilitiesInjected(ItemStack stack, CompoundTag nbt, CallbackInfoReturnable<ICapabilityProvider> cir) {
        CompoundTag tag = new CompoundTag();
        stack.deserializeNBT(tag);
        if (!tag.contains("Unbreakable")) {
            tag.putBoolean("Unbreakable", true);
            stack.setTag(tag);
        }
        //tag.putBoolean("Unbreakable", true);
        //stack.setTag(tag);
    }

}
