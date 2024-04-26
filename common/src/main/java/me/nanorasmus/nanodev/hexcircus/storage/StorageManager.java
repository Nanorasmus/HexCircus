package me.nanorasmus.nanodev.hexcircus.storage;


import me.nanorasmus.nanodev.hexcircus.HexCircus;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;

import java.util.HashMap;
import java.util.UUID;

public class StorageManager extends PersistentState {
    public static final String saveId = HexCircus.MOD_ID + ".save";
    public static StorageManager INSTANCE;

    public static HashMap<UUID, Integer> creatureWillPowers = new HashMap<>();


    public static void save(MinecraftServer server) {
        server.getOverworld().getPersistentStateManager().set(saveId, INSTANCE);
    }

    public static void load(MinecraftServer server) {
        INSTANCE = server.getOverworld().getPersistentStateManager().getOrCreate(StorageManager::createFromNbt, StorageManager::new, saveId);
    }

    public StorageManager() {
        // Initialize values

    }

    static void loadWillPowers(NbtCompound nbt) {
        for (int i = 0; i < nbt.getInt("will_count"); i++) {
            creatureWillPowers.put(
                    nbt.getUuid("will_uuid_"+i),
                    nbt.getInt("will_value_"+i)
            );
        }
    }

    static NbtCompound saveWillPowers(NbtCompound nbt) {
        nbt.putInt("will_count", creatureWillPowers.size());

        int i = 0;
        for (UUID key : creatureWillPowers.keySet()) {
            nbt.putUuid("will_uuid_"+i, key);
            nbt.putInt("will_value_"+i, creatureWillPowers.get(key));

            i++;
        }

        return nbt;
    }

    public static StorageManager createFromNbt(NbtCompound nbt) {
        StorageManager output = new StorageManager();

        // Load values
        loadWillPowers(nbt);

        // Return
        return output;
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        // Save values
        nbt = saveWillPowers(nbt);

        // Return
        return nbt;
    }
}
