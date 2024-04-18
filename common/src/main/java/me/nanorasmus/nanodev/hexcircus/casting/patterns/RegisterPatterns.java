package me.nanorasmus.nanodev.hexcircus.casting.patterns;

import at.petrak.hexcasting.api.PatternRegistry;
import at.petrak.hexcasting.api.spell.math.HexDir;
import at.petrak.hexcasting.api.spell.math.HexPattern;
import me.nanorasmus.nanodev.hexcircus.HexCircus;
import net.minecraft.util.Identifier;

public class RegisterPatterns {
    public static void registerPatterns() {
        try {
            // Particles
            PatternRegistry.mapPattern(HexPattern.fromAngles("eqqqqaw", HexDir.NORTH_EAST),
                    new Identifier(HexCircus.MOD_ID, "linear_particle"),
                    new OpLinearParticle());
            PatternRegistry.mapPattern(HexPattern.fromAngles("eqqqqaq", HexDir.NORTH_EAST),
                    new Identifier(HexCircus.MOD_ID, "bezier_particle"),
                    new OpBeizerParticle());

        } catch (PatternRegistry.RegisterPatternException exn) {
            exn.printStackTrace();
        }
    }
}
