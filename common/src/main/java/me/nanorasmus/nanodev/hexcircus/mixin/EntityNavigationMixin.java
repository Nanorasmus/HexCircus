package me.nanorasmus.nanodev.hexcircus.mixin;

import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Set;

@Mixin(EntityNavigation.class)
public interface EntityNavigationMixin{
    @Invoker("findPathToAny")
    Path findPathToAny(Set<BlockPos> positions, int range, boolean useHeadPos, int distance, float followRange);
}
