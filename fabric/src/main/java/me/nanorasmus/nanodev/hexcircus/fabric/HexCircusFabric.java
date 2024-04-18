package me.nanorasmus.nanodev.hexcircus.fabric;

import me.nanorasmus.nanodev.hexcircus.HexCircus;
import net.fabricmc.api.ModInitializer;

public class HexCircusFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        HexCircus.init();
    }
}