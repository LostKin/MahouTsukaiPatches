package com.lostkin.durabilityfix.networking.packet;

import com.lostkin.durabilityfix.eyes.EyesStorage;
import com.lostkin.durabilityfix.eyes.PlayerEyesProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class EyesStatusC2SPacket {
    public EyesStatusC2SPacket() {

    }

    public EyesStatusC2SPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            //ModMessages.register();
            ServerPlayer player = context.getSender();

            player.getCapability(PlayerEyesProvider.PLAYER_EYES).ifPresent(eyes -> {
                //Trying to activate eyes
                boolean newEyeStatus = !eyes.getEyeStatus();
                eyes.setEyeStatus(newEyeStatus);
            });

            /*ServerLevel level = player.getLevel();

            boolean showparticles = false;
            int time = 10;


            player.addEffect(new MobEffectInstance(
                    ModEffects.BINDING_EYES.get(),
                    1200,
                    0,
                    false,
                    false,
                    false
            ));*/

            //player.addEffect(new MobEffectInstance(ModEffects.BINDING_EYES.get(),
            //        time, 0, false, showparticles));
            //EntityType.COW.spawn(level, null, null, player.blockPosition(), MobSpawnType.COMMAND,
            //        true, false);
        });
        return true;
    }

}
