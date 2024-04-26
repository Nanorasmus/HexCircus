package me.nanorasmus.nanodev.hexcircus;

import me.nanorasmus.nanodev.hexcircus.casting.patterns.RegisterPatterns;
import me.nanorasmus.nanodev.hexcircus.networking.NetworkingHandler;
import me.nanorasmus.nanodev.hexcircus.particle.CastingParticles;
import dev.architectury.event.events.common.LifecycleEvent;
import me.nanorasmus.nanodev.hexcircus.storage.StorageManager;
import net.minecraft.server.MinecraftServer;


public class HexCircus
{
	public static final String MOD_ID = "hex_circus";

	public static void init() {
		// Register Patterns
		RegisterPatterns.registerPatterns();

		NetworkingHandler.registerPackets();

		LifecycleEvent.SERVER_STARTING.register((MinecraftServer server) -> {
			StorageManager.load(server);
		});
		LifecycleEvent.SERVER_STOPPING.register((MinecraftServer server) -> {
			StorageManager.save(server);
		});
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
