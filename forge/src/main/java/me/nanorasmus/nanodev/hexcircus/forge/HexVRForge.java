package me.nanorasmus.nanodev.hexcircus.forge;

import dev.architectury.platform.forge.EventBuses;
import me.nanorasmus.nanodev.hexcircus.HexCircus;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.function.Supplier;

@Mod(HexCircus.MOD_ID)
public class HexVRForge {
    public HexVRForge() {
		// Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(HexCircus.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        HexCircus.init();

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, new Supplier<Runnable>() {
            @Override
            public Runnable get() {
                return HexCircus::initClient;
            }
        });
        DistExecutor.unsafeRunWhenOn(Dist.DEDICATED_SERVER, new Supplier<Runnable>() {
            @Override
            public Runnable get() {
                return HexCircus::initServer;
            }
        });
    }
}