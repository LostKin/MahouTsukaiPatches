package com.lostkin.mahoutsukaipatches.mixin.protection;

import com.lostkin.mahoutsukaipatches.MahouTsukaiPatches;
import com.lostkin.mahoutsukaipatches.protection.PlayerProtectionProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import stepsword.mahoutsukai.capability.mahou.IMahou;
import stepsword.mahoutsukai.capability.mahou.Mahou;
import stepsword.mahoutsukai.capability.mahou.PlayerManaManager;
import stepsword.mahoutsukai.config.MTConfig;
import stepsword.mahoutsukai.effects.displacement.ProtectiveDisplacementSpellEffect;
import stepsword.mahoutsukai.util.Utils;

import java.util.concurrent.atomic.AtomicBoolean;

@Mixin(ProtectiveDisplacementSpellEffect.class)
public class ProtectiveDisplacementEffectMixin {

    /*@Inject(method = "use", at = @At("HEAD"), remap = true, cancellable = false)
    public void eyesMethod(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        DurabilityFix.DebugLog("Mixin just worked");
        player.sendSystemMessage(Component.literal("Mixin just worked"));
    }*/

    @Inject(method = "protectiveDisplacementProjectileImpact", at = @At("HEAD"), remap = false, cancellable = true)
    private static void injected(ProjectileImpactEvent event, CallbackInfoReturnable<Boolean> cir) {

        //MahouTsukaiPatches.DebugLog("Mixin is called to action");

        if (!event.getEntity().level.isClientSide &&
                event.getRayTraceResult() != null &&
                event.getRayTraceResult() instanceof EntityHitResult &&
                ((EntityHitResult)event.getRayTraceResult()).getEntity() instanceof Player) {

            //MahouTsukaiPatches.DebugLog("Player is hit by an Arrow");

            Player player = (Player)((EntityHitResult)event.getRayTraceResult()).getEntity();

            if (player.level.isClientSide()) {
                return;
            }

            AtomicBoolean haveProtectionOn = new AtomicBoolean(false);

            player.getCapability(PlayerProtectionProvider.PLAYER_PROTECTION).ifPresent(protection -> {
                haveProtectionOn.set(protection.getProtectionStatus());
            });

            if (!haveProtectionOn.get()) {
                return;
            }

            int manaCost = MTConfig.PROTECTIVE_DISPLACEMENT_MANA_COST / 5;

            MahouTsukaiPatches.DebugLog(((Integer)manaCost).toString());

            int manaDrained = PlayerManaManager.drainMana(player, manaCost, false, false);

            if (manaDrained <= 0) {
                IMahou mahou = Utils.getPlayerMahou(player);
                player.sendSystemMessage(Component.literal("You don't have enough mana to protect yourself"));
                player.getCapability(PlayerProtectionProvider.PLAYER_PROTECTION).ifPresent(protection -> {
                    protection.setProtectionStatus(false);
                });
                mahou.setProtectiveDisplacement(0);
            }
        }
    }

}