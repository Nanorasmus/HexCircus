package me.nanorasmus.nanodev.hexcircus.networking;

import dev.architectury.networking.NetworkChannel;
import me.nanorasmus.nanodev.hexcircus.HexCircus;
import me.nanorasmus.nanodev.hexcircus.networking.custom.SpawnBezierParticle;
import me.nanorasmus.nanodev.hexcircus.networking.custom.SpawnLinearParticle;
import net.minecraft.util.Identifier;

public class NetworkingHandler {
    public static final NetworkChannel CHANNEL = NetworkChannel.create(new Identifier(HexCircus.MOD_ID, "networking_channel"));

    public static void registerPackets() {
        CHANNEL.register(SpawnBezierParticle.class, SpawnBezierParticle::encode, SpawnBezierParticle::new, SpawnBezierParticle::apply);
        CHANNEL.register(SpawnLinearParticle.class, SpawnLinearParticle::encode, SpawnLinearParticle::new, SpawnLinearParticle::apply);
    }
}
