package me.nanorasmus.nanodev.hexcircus;

import me.nanorasmus.nanodev.hexcircus.casting.patterns.RegisterPatterns;
import me.nanorasmus.nanodev.hexcircus.networking.NetworkingHandler;
import me.nanorasmus.nanodev.hexcircus.particle.CastingParticles;


public class HexCircus
{
	public static final String MOD_ID = "hex_circus";

	public static void init() {
		// Register Patterns
		RegisterPatterns.registerPatterns();

		NetworkingHandler.registerPackets();

	}

	public static Runnable initClient() {
		// Register Visuals
		CastingParticles.registerParticles();


        return null;
    }

	public static Runnable initServer() {
		return null;
	}
}
