package com.lostkin.durabilityfix.networking.packet;

import com.lostkin.durabilityfix.DurabilityFix;
import com.lostkin.durabilityfix.eyes.EyesStorage;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.RegistryObject;
import stepsword.mahoutsukai.potion.ModEffects;

import java.util.function.Supplier;

public class EyesTypeC2SPacket {

    RegistryObject<MobEffect> effect;

    public EyesTypeC2SPacket(RegistryObject<MobEffect> _effect) {
        effect = _effect;
    }

    public EyesTypeC2SPacket(FriendlyByteBuf buf) {
        if (effect == ModEffects.BINDING_EYES) {
            buf.writeByte(1);
        } else if (effect == ModEffects.BLACK_FLAME_EYES) {
            buf.writeByte(2);
        } else if (effect == ModEffects.DEATH_COLLECTION_EYES) {
            buf.writeByte(3);
        } else if (effect == ModEffects.FAY_SIGHT_EYES) {
            buf.writeByte(4);
        }
    }

    public void toBytes(FriendlyByteBuf buf) {
        int type = buf.readByte();

        switch(type) {
            case 1: {
                effect = ModEffects.BINDING_EYES;
            }
            case 2: {
                effect = ModEffects.BLACK_FLAME_EYES;
            }
            case 3: {
                effect = ModEffects.DEATH_COLLECTION_EYES;
            }
            case 4: {
                effect = ModEffects.FAY_SIGHT_EYES;
            }
        }

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            DurabilityFix.DebugLog(effect.toString());
            //ModMessages.register();
            ServerPlayer player = context.getSender();

            EyesStorage.eyesType.put(player.getUUID(), effect);

        });
        return true;
    }

}
