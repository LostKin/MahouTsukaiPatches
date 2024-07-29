package com.lostkin.mahoutsukaipatches.networking;

import com.lostkin.mahoutsukaipatches.MahouTsukaiPatches;
import com.lostkin.mahoutsukaipatches.networking.packet.EyesStatusC2SPacket;
import com.lostkin.mahoutsukaipatches.networking.packet.EyesStatusS2CPacket;
import com.lostkin.mahoutsukaipatches.networking.packet.ProtectionStatusC2SPacket;
import com.lostkin.mahoutsukaipatches.networking.packet.ProtectionStatusS2CPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModMessages {

    private static SimpleChannel INSTANCE;

    private static int packetId = 0;

    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(MahouTsukaiPatches.MODID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(EyesStatusC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(EyesStatusC2SPacket::new)
                .encoder(EyesStatusC2SPacket::toBytes)
                .consumerMainThread(EyesStatusC2SPacket::handle)
                .add();

        net.messageBuilder(ProtectionStatusC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ProtectionStatusC2SPacket::new)
                .encoder(ProtectionStatusC2SPacket::toBytes)
                .consumerMainThread(ProtectionStatusC2SPacket::handle)
                .add();

        net.messageBuilder(EyesStatusS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(EyesStatusS2CPacket::new)
                .encoder(EyesStatusS2CPacket::toBytes)
                .consumerMainThread(EyesStatusS2CPacket::handle)
                .add();
        net.messageBuilder(ProtectionStatusS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ProtectionStatusS2CPacket::new)
                .encoder(ProtectionStatusS2CPacket::toBytes)
                .consumerMainThread(ProtectionStatusS2CPacket::handle)
                .add();

    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

}
