package com.lostkin.mahoutsukaipatches.networking;

import com.lostkin.mahoutsukaipatches.MahouTsukaiPatches;
import com.lostkin.mahoutsukaipatches.networking.packet.EyesStatusC2SPacket;
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

        net.messageBuilder(EyesStatusC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(EyesStatusC2SPacket::new)
                .encoder(EyesStatusC2SPacket::toBytes)
                .consumerMainThread(EyesStatusC2SPacket::handle)
                .add();

    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

}