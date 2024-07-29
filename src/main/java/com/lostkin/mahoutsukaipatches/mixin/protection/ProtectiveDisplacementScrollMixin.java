package com.lostkin.mahoutsukaipatches.mixin.protection;

import com.lostkin.mahoutsukaipatches.MahouTsukaiPatches;
import com.lostkin.mahoutsukaipatches.eyes.PlayerEyesProvider;
import com.lostkin.mahoutsukaipatches.protection.PlayerProtectionProvider;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import stepsword.mahoutsukai.capability.scrollmahou.IScrollMahou;
import stepsword.mahoutsukai.item.spells.displacement.ProtectiveDisplacementSpellScroll;
import stepsword.mahoutsukai.item.spells.eyes.BindingEyesSpellScroll;
import stepsword.mahoutsukai.potion.ModEffects;

@Mixin(ProtectiveDisplacementSpellScroll.class)
public class ProtectiveDisplacementScrollMixin {

    /*@Inject(method = "use", at = @At("HEAD"), remap = true, cancellable = false)
    public void eyesMethod(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        DurabilityFix.DebugLog("Mixin just worked");
        player.sendSystemMessage(Component.literal("Mixin just worked"));
    }*/

    @Inject(method = "doSpell", at = @At("HEAD"), remap = false, cancellable = true)
    public void injected(Player user, IScrollMahou scrollMahou, CallbackInfoReturnable<Boolean> cir) {
        if (!user.level.isClientSide()) {
            MahouTsukaiPatches.DebugLog("Unlocking defensive teleport");
            user.getCapability(PlayerProtectionProvider.PLAYER_PROTECTION).ifPresent(protection -> {
                protection.setProtectionUnlocked(true);
            });
            cir.setReturnValue(true);
            cir.cancel();
        }
    }
}
