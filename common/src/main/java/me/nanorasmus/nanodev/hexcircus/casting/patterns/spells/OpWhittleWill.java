package me.nanorasmus.nanodev.hexcircus.casting.patterns.spells;

import at.petrak.hexcasting.api.misc.MediaConstants;
import at.petrak.hexcasting.api.spell.ConstMediaAction;
import at.petrak.hexcasting.api.spell.OperationResult;
import at.petrak.hexcasting.api.spell.OperatorUtils;
import at.petrak.hexcasting.api.spell.casting.CastingContext;
import at.petrak.hexcasting.api.spell.casting.eval.SpellContinuation;
import at.petrak.hexcasting.api.spell.iota.Iota;
import dev.architectury.utils.GameInstance;
import me.nanorasmus.nanodev.hexcircus.casting.mishaps.JavaMishapThrower;
import me.nanorasmus.nanodev.hexcircus.casting.mishaps.MishapInfluencePlayer;
import me.nanorasmus.nanodev.hexcircus.casting.mishaps.MishapInvalidCreature;
import me.nanorasmus.nanodev.hexcircus.entity.EntityManagement;
import me.nanorasmus.nanodev.hexcircus.networking.NetworkingHandler;
import me.nanorasmus.nanodev.hexcircus.networking.custom.SpawnLinearParticle;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class OpWhittleWill implements ConstMediaAction {
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
    public int getMediaCost() { return MediaConstants.CRYSTAL_UNIT * 64; }

    @NotNull
    @Override
    public List<Iota> execute(@NotNull List<? extends Iota> args, @NotNull CastingContext ctx) {
        // Get target entity
        LivingEntity entity = OperatorUtils.getLivingEntityButNotArmorStand(args, 0, getArgc());
        ctx.assertEntityInRange(entity);


        if (entity instanceof ServerPlayerEntity) {
            JavaMishapThrower.throwMishap(new MishapInfluencePlayer(ctx.getCaster(), (ServerPlayerEntity) entity));
            return new ArrayList<>();
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


        // Whittle the willpower of the creature
        EntityManagement.saveCost(entity, cost / 2);

        // Check if the target has no will to live
        if (cost < MediaConstants.DUST_UNIT / 10) {
            entity.kill();
            ctx.getCaster().sendMessage(Text.of("The creature lost it's will to live"));
        }


        return new ArrayList<>();
    }

    @NotNull
    @Override
    public OperationResult operate(SpellContinuation continuation, List<Iota> stack, Iota ravenmind, CastingContext castingContext){
        return DefaultImpls.operate(this, continuation, stack, ravenmind, castingContext);
    }
}
