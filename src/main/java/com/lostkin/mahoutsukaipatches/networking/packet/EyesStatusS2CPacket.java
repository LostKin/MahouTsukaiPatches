package com.lostkin.mahoutsukaipatches.networking.packet;

import com.lostkin.mahoutsukaipatches.client.ClientEyeData;
import com.lostkin.mahoutsukaipatches.eyes.PlayerEyesProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class EyesStatusS2CPacket {

    private boolean eyesStatus = false;

    public EyesStatusS2CPacket(boolean eyesStatus) {
        this.eyesStatus = eyesStatus;
    }

    public EyesStatusS2CPacket(FriendlyByteBuf buf) {
        eyesStatus = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(eyesStatus);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientEyeData.setEyesType();
            ClientEyeData.setEyesStatus(eyesStatus);
        });
        return true;
    }
}
