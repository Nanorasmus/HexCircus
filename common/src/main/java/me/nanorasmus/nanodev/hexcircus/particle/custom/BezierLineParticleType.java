package me.nanorasmus.nanodev.hexcircus.particle.custom;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import net.minecraft.client.option.GameOptions;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;

public class BezierLineParticleType extends ParticleType<BezierLineParticleType> implements ParticleEffect {


    /**
     * @param alwaysShow whether this particle type should appear regardless of {@linkplain GameOptions#particles particle mode}
     */
    public BezierLineParticleType(boolean alwaysShow) {
        super(alwaysShow, PARAMETER_FACTORY);
    }

    @Override
    public Codec<BezierLineParticleType> getCodec() {
        return null;
    }

    @Override
    public ParticleType<?> getType() {
        return null;
    }

    @Override
    public void write(PacketByteBuf buf) {

    }

    @Override
    public String asString() {
        return null;
    }

    private static final ParticleEffect.Factory<BezierLineParticleType> PARAMETER_FACTORY = new ParticleEffect.Factory<BezierLineParticleType>(){

        @Override
        public BezierLineParticleType read(ParticleType<BezierLineParticleType> type, StringReader reader) throws CommandSyntaxException {
            return this.read(type, reader);
        }

        @Override
        public BezierLineParticleType read(ParticleType<BezierLineParticleType> type, PacketByteBuf buf) {
            return this.read(type, buf);
        }
    };
}
