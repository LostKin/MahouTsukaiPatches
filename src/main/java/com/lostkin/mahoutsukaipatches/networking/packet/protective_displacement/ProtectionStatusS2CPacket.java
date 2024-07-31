package com.lostkin.mahoutsukaipatches.networking.packet.protective_displacement;

import com.lostkin.mahoutsukaipatches.client.ClientProtectionData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ProtectionStatusS2CPacket {
    private boolean protectionStatus = false;

    public ProtectionStatusS2CPacket(boolean protectionStatus) {
        this.protectionStatus = protectionStatus;
    }

    public ProtectionStatusS2CPacket(FriendlyByteBuf buf) {
        protectionStatus = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(protectionStatus);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientProtectionData.unlockProtection();
            ClientProtectionData.setProtectionStatus(protectionStatus);
        });
        return true;
    }
}
