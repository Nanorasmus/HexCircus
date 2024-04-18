package me.nanorasmus.nanodev.hexcircus.fabric;

import me.nanorasmus.nanodev.hexcircus.HexCircus;
import net.fabricmc.api.ClientModInitializer;

public class HexCircusFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        HexCircus.initClient();
    }
}
