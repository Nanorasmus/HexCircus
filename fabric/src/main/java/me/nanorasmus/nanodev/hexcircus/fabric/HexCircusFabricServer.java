package me.nanorasmus.nanodev.hexcircus.fabric;

import me.nanorasmus.nanodev.hexcircus.HexCircus;
import net.fabricmc.api.DedicatedServerModInitializer;

public class HexCircusFabricServer implements DedicatedServerModInitializer {
    @Override
    public void onInitializeServer() {
        HexCircus.initServer();
    }
}
