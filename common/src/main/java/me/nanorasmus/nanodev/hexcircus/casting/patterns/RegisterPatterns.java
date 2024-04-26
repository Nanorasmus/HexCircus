package me.nanorasmus.nanodev.hexcircus.casting.patterns;

import at.petrak.hexcasting.api.PatternRegistry;
import at.petrak.hexcasting.api.spell.math.HexDir;
import at.petrak.hexcasting.api.spell.math.HexPattern;
import me.nanorasmus.nanodev.hexcircus.HexCircus;
import me.nanorasmus.nanodev.hexcircus.casting.patterns.spells.*;
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

            // Pathfinding
            PatternRegistry.mapPattern(HexPattern.fromAngles("awwdaqwqwqadwwa", HexDir.SOUTH_WEST),
                    new Identifier(HexCircus.MOD_ID, "command_creature"),
                    new OpOvertakeCreature()
            );
            PatternRegistry.mapPattern(HexPattern.fromAngles("eewqqqwqqaeq", HexDir.NORTH_WEST),
                    new Identifier(HexCircus.MOD_ID, "whittle_will"),
                    new OpWhittleWill()
            );
            PatternRegistry.mapPattern(HexPattern.fromAngles("eewqqqwqq", HexDir.NORTH_WEST),
                    new Identifier(HexCircus.MOD_ID, "get_will"),
                    new OpGetWill()
            );

        } catch (PatternRegistry.RegisterPatternException exn) {
            exn.printStackTrace();
        }
    }
}
