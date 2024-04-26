package me.nanorasmus.nanodev.hexcircus.casting.patterns.spells;

import at.petrak.hexcasting.api.misc.MediaConstants;
import at.petrak.hexcasting.api.spell.*;
import at.petrak.hexcasting.api.spell.casting.CastingContext;
import at.petrak.hexcasting.api.spell.casting.eval.SpellContinuation;
import at.petrak.hexcasting.api.spell.iota.Iota;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import kotlin.Triple;
import me.nanorasmus.nanodev.hexcircus.casting.mishaps.JavaMishapThrower;
import me.nanorasmus.nanodev.hexcircus.casting.mishaps.MishapInfluencePlayer;
import me.nanorasmus.nanodev.hexcircus.casting.mishaps.MishapInvalidCreature;
import me.nanorasmus.nanodev.hexcircus.entity.EntityManagement;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OpOvertakeCreature implements SpellAction {
    @NotNull
    @Override
    public Text getDisplayName() {
        return DefaultImpls.getDisplayName(this);
    }

    @Override
    public boolean getAlwaysProcessGreatSpell() { return false; }

    @Override
    public boolean getCausesBlindDiversion() { return true; }

    @Override
    public boolean isGreat() { return true; }

    @Override
    public int getArgc() { return 2; }


    @NotNull
    @Override
    public OperationResult operate(SpellContinuation continuation, List<Iota> stack, Iota ravenmind, CastingContext castingContext){
        return DefaultImpls.operate(this, continuation, stack, ravenmind, castingContext);
    }

    @Override
    public boolean awardsCastingStat(@NotNull CastingContext castingContext) {
        return true;
    }

    @Override
    public boolean hasCastingSound(@NotNull CastingContext castingContext) {
        return false;
    }


    @Override
    public Triple<RenderedSpell, Integer, List<ParticleSpray>> execute(@NotNull List<? extends Iota> args, @NotNull CastingContext ctx) {
        // Get target entity
        LivingEntity entity = OperatorUtils.getLivingEntityButNotArmorStand(args, 0, getArgc());
        ctx.assertEntityInRange(entity);
        // Get pathfinding target
        Vec3d target = OperatorUtils.getVec3(args, 1, getArgc());
        ctx.assertVecInRange(target);


        if (entity instanceof ServerPlayerEntity) {
            JavaMishapThrower.throwMishap(new MishapInfluencePlayer(ctx.getCaster(), (ServerPlayerEntity) entity));
        }

        // Check if target entity is mob
        if (!(entity instanceof PathAwareEntity)) {
            JavaMishapThrower.throwMishap(new MishapInvalidCreature());
        }




        // Get the cost of overtaking the target
        int cost = EntityManagement.getCost(entity);


        // Check if target is invalid
        if (cost == -1) {
            JavaMishapThrower.throwMishap(new MishapInvalidCreature());
        }

        return new Triple<>(new Spell((PathAwareEntity) entity, target), cost, List.of());
    }

    class Spell implements RenderedSpell {

        PathAwareEntity entity;
        Vec3d target;

        public Spell(PathAwareEntity entity, Vec3d target) {
            this.entity = entity;
            this.target = target;
        }

        @Override
        public void cast(@NotNull CastingContext ctx) {

            entity.getNavigation().startMovingTo(target.x, target.y, target.z, 1);
        }
    }
}
