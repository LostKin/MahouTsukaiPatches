package com.lostkin.durabilityfix.mixin.spells;

import com.lostkin.durabilityfix.DurabilityFix;
import com.lostkin.durabilityfix.networking.ModMessages;
import com.lostkin.durabilityfix.networking.packet.EyesStatusC2SPacket;
import com.lostkin.durabilityfix.networking.packet.EyesTypeC2SPacket;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import stepsword.mahoutsukai.capability.scrollmahou.IScrollMahou;
import stepsword.mahoutsukai.item.spells.eyes.BindingEyesSpellScroll;
import stepsword.mahoutsukai.potion.ModEffects;

@Mixin(BindingEyesSpellScroll.class)
public class EyesMixin{

    /*@Inject(method = "use", at = @At("HEAD"), remap = true, cancellable = false)
    public void eyesMethod(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        DurabilityFix.DebugLog("Mixin just worked");
        player.sendSystemMessage(Component.literal("Mixin just worked"));
    }*/

    @Inject(method = "doSpell", at = @At("HEAD"), remap = false)
    public void injected(Player user, IScrollMahou scrollMahou, CallbackInfoReturnable<Boolean> cir) {
        DurabilityFix.DebugLog("Mixin just worked");
        if (user.level.isClientSide()) {
            ModMessages.sendToServer(new EyesTypeC2SPacket(ModEffects.BINDING_EYES));
        }
    }
}


