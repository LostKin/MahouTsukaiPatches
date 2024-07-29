package com.lostkin.mahoutsukaipatches.mixin.eyes;

import com.lostkin.mahoutsukaipatches.eyes.PlayerEyesProvider;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import stepsword.mahoutsukai.capability.scrollmahou.IScrollMahou;
import stepsword.mahoutsukai.item.spells.eyes.DeathCollectionEyesSpellScroll;
import stepsword.mahoutsukai.potion.ModEffects;

@Mixin(DeathCollectionEyesSpellScroll.class)
public class DeathCollectionEyesMixin {
    @Inject(method = "doSpell", at = @At("HEAD"), remap = false, cancellable = true)
    public void injected(Player user, IScrollMahou scrollMahou, CallbackInfoReturnable<Boolean> cir) {
        if (!user.level.isClientSide()) {
            user.getCapability(PlayerEyesProvider.PLAYER_EYES).ifPresent(eyes -> {
                eyes.setEyeType(ModEffects.DEATH_COLLECTION_EYES);
            });
            cir.setReturnValue(true);
            cir.cancel();
        }
    }
}
