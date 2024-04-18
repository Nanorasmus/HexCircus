package me.nanorasmus.nanodev.hexcircus.particle.custom;

import dev.architectury.registry.client.particle.ParticleProviderRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.ArrayList;

public class BezierLineParticle extends SpriteBillboardParticle {

    public int type = 1;

    Vec3d from = new Vec3d(0, 0, 0);
    Vec3d to = new Vec3d(0, 0, 0);
    Vec3d bend = null;

    private final Vec3d constantVelocity;
    protected BezierLineParticle(ClientWorld world, SpriteProvider sprites, Vec3d pos, Vec3d vel) {
        super(world, pos.x, pos.y, pos.z, vel.x, vel.y, vel.z);

        constantVelocity = vel;

        this.velocityMultiplier = 1f;
        this.setPos(pos.x, pos.y, pos.z);
        this.setSpriteForAge(sprites);

        this.red = 1f;
        this.green = 1f;
        this.blue = 1f;
    }

    public void updatePoints(Vec3d from, Vec3d to, Vec3d bend) {
        this.from = from;
        this.to = to;
        this.bend = bend;
    }


    @Override
    public void tick() {
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        if (this.age++ >= this.maxAge) {
            this.markDead();
            return;
        }

        if (type == 0) {
            Vec3d newPosition = new Vec3d(this.x, this.y, this.z).add(this.constantVelocity);
            this.setPos(newPosition.x, newPosition.y, newPosition.z);
        } else if (type == 1) {
            if (bend != null) {
                double stageProgress = this.age / (double) maxAge;

                Vec3d pre = from.lerp(bend, stageProgress);
                Vec3d post = bend.lerp(to, stageProgress);

                Vec3d newPosition = pre.lerp(post, stageProgress);

                this.setPos(newPosition.x, newPosition.y, newPosition.z);
            }
        }
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider sprites;
        public Factory(SpriteProvider sprites) {
            this.sprites = sprites;
        }

        @Nullable
        @Override
        public Particle createParticle(DefaultParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new BezierLineParticle(world, sprites, new Vec3d(x, y, z), new Vec3d(velocityX, velocityY, velocityZ));
        }
    }
}
