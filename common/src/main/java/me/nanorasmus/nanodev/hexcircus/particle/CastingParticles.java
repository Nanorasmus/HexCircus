package me.nanorasmus.nanodev.hexcircus.particle;

import dev.architectury.registry.client.particle.ParticleProviderRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import me.nanorasmus.nanodev.hexcircus.HexCircus;
import me.nanorasmus.nanodev.hexcircus.particle.custom.BezierLineParticle;
import me.nanorasmus.nanodev.hexcircus.particle.custom.BezierLineParticleType;
import me.nanorasmus.nanodev.hexcircus.particle.custom.LinearLineParticle;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.EndRodParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.Arrays;

public class CastingParticles {
    public static final DefaultParticleType STATIC_PARTICLE = ParticleTypes.END_ROD;
    public static final DefaultParticleType BEZIER_PARTICLE = ParticleTypes.TOTEM_OF_UNDYING;

    static MinecraftClient client = MinecraftClient.getInstance();

    public static void registerParticles() {

        ParticleProviderRegistry.register(BEZIER_PARTICLE, BezierLineParticle.Factory::new);
        ParticleProviderRegistry.register(STATIC_PARTICLE, LinearLineParticle.Factory::new);

    }

    // Rendering

    public static ArrayList<Particle> initializeLine(Vec3d from, Vec3d to, float particleDistance) {
        ArrayList<Particle> particles = new ArrayList<>();

        Vec3d direction = to.subtract(from);
        for (int i = 1; i < 100; i++) {
            // Get total distance to be incremented
            double increment = particleDistance * i;

            if (increment > direction.length()) break;

            // Turn it into a relative distance for lerping
            double lerpIncrement = increment / direction.length();

            particles.add(
                    renderLine(from.lerp(to, lerpIncrement), to)
            );
        }

        return particles;
    }

    public static Particle renderSpot(Vec3d point, int maxAge, float red, float green, float blue) {
        Particle particle = renderSpot(point, maxAge);
        particle.setColor(red, green, blue);
        return particle;
    }


    public static Particle renderSpot(Vec3d point, int maxAge) {
        return renderSpot(point, maxAge, 0.15f);
    }

    public static Particle renderSpot(Vec3d point, int maxAge, float sizeMultiplier) {
        Particle particle = client.particleManager.addParticle(CastingParticles.STATIC_PARTICLE, point.x, point.y, point.z, 0, 0, 0);
        particle.setMaxAge(maxAge);
        particle.scale(sizeMultiplier);
        return particle;
    }

    private static float clamp01(double value) {
        return (float) Math.min(1, Math.max(0, value));
    }

    // Linear

    public static Particle renderLine(Vec3d from, Vec3d to, float red, float green, float blue) {
        return renderLine(from, to, 1, red, green, blue);
    }
    public static Particle renderLine(Vec3d from, Vec3d to, float speed, Vec3d color) {
        Particle particle = renderLine(from, to, speed);
        particle.setColor(clamp01(color.x), clamp01(color.y), clamp01(color.z));
        return particle;
    }
    
    public static Particle renderLine(Vec3d from, Vec3d to, float speed, float red, float green, float blue) {
        Particle particle = renderLine(from, to, speed);
        particle.setColor(red, green, blue);
        return particle;
    }

    public static Particle renderLine(Vec3d from, Vec3d to) {
        return renderLine(from, to, 1);
    }

    public static Particle renderLine(Vec3d from, Vec3d to, float speed) {
        Vec3d vel = to.subtract(from).multiply(0.05 * speed);
        Particle particle = client.particleManager.addParticle(CastingParticles.STATIC_PARTICLE, from.x, from.y, from.z, vel.x, vel.y, vel.z);
        particle.setMaxAge(Math.round(20 / speed));
        particle.scale(0.5f);
        return particle;
    }


    // Bezier
    public static Particle renderBeizer(Vec3d from, Vec3d to, Vec3d bend, float speed, Vec3d color) {
        return renderBeizer(from, to, bend, speed, clamp01(color.x), clamp01(color.y), clamp01(color.z));
    }

    public static Particle renderBeizer(Vec3d from, Vec3d to, Vec3d bend, float speed, float red, float green, float blue) {
        Particle particle = renderBeizer(from, to, bend, speed);
        particle.setColor(red, green, blue);
        return particle;
    }


    public static Particle renderBeizer(Vec3d from, Vec3d to, Vec3d bend, float speed) {
        Vec3d vel = bend.subtract(from).multiply(0.1 * speed);
        BezierLineParticle particle = (BezierLineParticle) client.particleManager.addParticle(BEZIER_PARTICLE, from.x, from.y, from.z, vel.x, vel.y, vel.z);
        particle.updatePoints(from, to, bend);
        particle.setMaxAge(Math.round(20 / speed));
        particle.scale(0.5f);
        return particle;
    }
}
