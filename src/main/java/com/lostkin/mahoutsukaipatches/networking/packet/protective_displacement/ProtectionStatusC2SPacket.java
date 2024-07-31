package com.lostkin.mahoutsukaipatches.networking.packet.protective_displacement;

import com.lostkin.mahoutsukaipatches.MahouTsukaiPatches;
import com.lostkin.mahoutsukaipatches.networking.ModMessages;
import com.lostkin.mahoutsukaipatches.protection.PlayerProtectionProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ProtectionStatusC2SPacket {
    public ProtectionStatusC2SPacket() {

    }

    public ProtectionStatusC2SPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            //ModMessages.register();
            MahouTsukaiPatches.DebugLog("Handling the player protection packet");
            ServerPlayer player = context.getSender();

            player.getCapability(PlayerProtectionProvider.PLAYER_PROTECTION).ifPresent(protection -> {
                if (protection.getProtectionUnlocked()) {
                    boolean newProtectionStatus = !protection.getProtectionStatus();
                    protection.setProtectionStatus(newProtectionStatus);
                    /*if (newProtectionStatus) {
                        player.sendSystemMessage(Component.literal("You are now protected from arrows"));
                    } else {
                        player.sendSystemMessage(Component.literal("You are no longer protected from arrows"));
                    }*/

                    ModMessages.sendToPlayer(new ProtectionStatusS2CPacket(protection.getProtectionStatus()), player);
                }
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
