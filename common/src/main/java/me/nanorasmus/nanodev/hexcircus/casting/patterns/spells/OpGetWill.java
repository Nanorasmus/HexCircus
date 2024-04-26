package me.nanorasmus.nanodev.hexcircus.casting.patterns.spells;

import at.petrak.hexcasting.api.misc.MediaConstants;
import at.petrak.hexcasting.api.spell.ConstMediaAction;
import at.petrak.hexcasting.api.spell.OperationResult;
import at.petrak.hexcasting.api.spell.OperatorUtils;
import at.petrak.hexcasting.api.spell.casting.CastingContext;
import at.petrak.hexcasting.api.spell.casting.eval.SpellContinuation;
import at.petrak.hexcasting.api.spell.iota.DoubleIota;
import at.petrak.hexcasting.api.spell.iota.Iota;
import at.petrak.hexcasting.api.spell.iota.NullIota;
import me.nanorasmus.nanodev.hexcircus.casting.mishaps.JavaMishapThrower;
import me.nanorasmus.nanodev.hexcircus.casting.mishaps.MishapInfluencePlayer;
import me.nanorasmus.nanodev.hexcircus.casting.mishaps.MishapInvalidCreature;
import me.nanorasmus.nanodev.hexcircus.entity.EntityManagement;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OpGetWill implements ConstMediaAction {
    @NotNull
    @Override
    public Text getDisplayName() {
        return DefaultImpls.getDisplayName(this);
    }

    @Override
    public boolean getAlwaysProcessGreatSpell() { return false; }

    @Override
    public boolean getCausesBlindDiversion() { return false; }

    @Override
    public boolean isGreat() { return false; }

    @Override
    public int getArgc() { return 1; }

    @Override
    public int getMediaCost() { return 0; }

    @NotNull
    @Override
    public List<Iota> execute(@NotNull List<? extends Iota> args, @NotNull CastingContext ctx) {
        // Get target entity
        LivingEntity entity = OperatorUtils.getLivingEntityButNotArmorStand(args, 0, getArgc());
        ctx.assertEntityInRange(entity);


        // Check if target entity is mob
        if (!(entity instanceof PathAwareEntity)) {
            return List.of(new NullIota());
        }



        // Get the cost of overtaking the target
        int cost = EntityManagement.getCost(entity);

        // Check if target is invalid
        if (cost == -1) {
            return List.of(new NullIota());
        }

        return List.of(new DoubleIota((double) cost / MediaConstants.DUST_UNIT));
    }

    @NotNull
    @Override
    public OperationResult operate(SpellContinuation continuation, List<Iota> stack, Iota ravenmind, CastingContext castingContext){
        return DefaultImpls.operate(this, continuation, stack, ravenmind, castingContext);
    }
}
