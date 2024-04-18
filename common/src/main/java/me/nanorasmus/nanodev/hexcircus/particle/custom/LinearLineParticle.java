package me.nanorasmus.nanodev.hexcircus.particle.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

public class LinearLineParticle extends SpriteBillboardParticle {

    public int type = 0;

    private final Vec3d constantVelocity;
    protected LinearLineParticle(ClientWorld world, SpriteProvider sprites, Vec3d pos, Vec3d vel) {
        super(world, pos.x, pos.y, pos.z, vel.x, vel.y, vel.z);

        constantVelocity = vel;

        this.velocityMultiplier = 1f;
        this.setPos(pos.x, pos.y, pos.z);
        this.setSpriteForAge(sprites);

        this.red = 1f;
        this.green = 1f;
        this.blue = 1f;
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

        Vec3d newPosition = new Vec3d(this.x, this.y, this.z).add(this.constantVelocity);
        this.setPos(newPosition.x, newPosition.y, newPosition.z);
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
            return new LinearLineParticle(world, sprites, new Vec3d(x, y, z), new Vec3d(velocityX, velocityY, velocityZ));
        }
    }
}
