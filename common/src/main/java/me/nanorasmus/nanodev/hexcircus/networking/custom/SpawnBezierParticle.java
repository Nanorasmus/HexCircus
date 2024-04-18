package me.nanorasmus.nanodev.hexcircus.networking.custom;

import dev.architectury.networking.NetworkManager;
import dev.architectury.utils.GameInstance;
import me.nanorasmus.nanodev.hexcircus.networking.NetworkingHandler;
import me.nanorasmus.nanodev.hexcircus.particle.CastingParticles;
import net.fabricmc.api.EnvType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.UUID;
import java.util.function.Supplier;

public class SpawnBezierParticle {
    public final Vec3d from;
    public final Vec3d to;
    public final Vec3d bend;

    public final float speed;
    public final Vec3d color;

    public SpawnBezierParticle(PacketByteBuf buf) {
        from = new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
        to = new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
        bend = new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());

        speed = buf.readFloat();
        color = new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
    }

    public SpawnBezierParticle(Vec3d from, Vec3d to, Vec3d bend, float speed, Vec3d color) {
        this.from = from;
        this.to = to;
        this.bend = bend;

        this.speed = speed;
        this.color = color;
    }

    public void encode(PacketByteBuf buf) {
        // From
        buf.writeDouble(from.x);
        buf.writeDouble(from.y);
        buf.writeDouble(from.z);

        // To
        buf.writeDouble(to.x);
        buf.writeDouble(to.y);
        buf.writeDouble(to.z);

        // Bend
        buf.writeDouble(bend.x);
        buf.writeDouble(bend.y);
        buf.writeDouble(bend.z);

        // Speed
        buf.writeFloat(speed);

        // Color
        buf.writeDouble(color.x);
        buf.writeDouble(color.y);
        buf.writeDouble(color.z);
    }

    public void apply(Supplier<NetworkManager.PacketContext> contextSupplier) {
        if (contextSupplier.get().getEnv() == EnvType.SERVER) {
            // Send to all other players
            ArrayList<ServerPlayerEntity> players = new ArrayList<>();
            players.addAll(GameInstance.getServer().getPlayerManager().getPlayerList());

            for (int i = 0; i < players.size(); i++) {
                ServerPlayerEntity p = players.get(i);
                NetworkingHandler.CHANNEL.sendToPlayer(p, this);
            }
        } else {
            CastingParticles.renderBeizer(from, to, bend, speed, color);
        }
    }
}
