package me.nanorasmus.nanodev.hexcircus.entity;

import at.petrak.hexcasting.api.misc.MediaConstants;
import me.nanorasmus.nanodev.hexcircus.HexCircus;
import me.nanorasmus.nanodev.hexcircus.storage.StorageManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.*;
import net.minecraft.nbt.NbtCompound;

import java.util.UUID;

public class EntityManagement {
    public static final String PATHFINDING_COST_PATH = HexCircus.MOD_ID + ":pathfinding_cost";

    public static int getCost(Entity e) {
        if (!isSupported(e)) {
            return -1;
        }

        UUID uuid = e.getUuid();

        // Return it if it has it
        if (StorageManager.creatureWillPowers.containsKey(uuid)) {
            return StorageManager.creatureWillPowers.get(uuid);
        }

        // Else get the default for that mob
        return getBaseCost(e);
    }

    public static int getBaseCost(Entity e) {
        // Special cases
        if (e instanceof WardenEntity) {
            return 128 * MediaConstants.CRYSTAL_UNIT;
        }

        // Fallbacks
        if (e instanceof AmbientEntity || e instanceof WaterCreatureEntity) {
            return 4 * MediaConstants.CRYSTAL_UNIT;
        }
        if (e instanceof PassiveEntity || e instanceof GolemEntity) {
            return 8 * MediaConstants.CRYSTAL_UNIT;
        }
        if (e instanceof Angerable) {
            return 16 * MediaConstants.CRYSTAL_UNIT;
        }
        if (e instanceof HostileEntity) {
            return 32 * MediaConstants.CRYSTAL_UNIT;
        }

        // Wut
        return -1;
    }

    public static void saveCost(Entity e, int cost) {
        StorageManager.creatureWillPowers.put(e.getUuid(), cost);
    }

    public static boolean isSupported(Entity e) {
        if (!(e instanceof MobEntity)
                // Blacklist
                || e instanceof EnderDragonEntity
                || e instanceof WitherEntity
                || e instanceof VillagerEntity
        ) {
            return false;
        }
        return true;
    }

}
