package me.nanorasmus.nanodev.hexcircus.casting.patterns;

import at.petrak.hexcasting.api.spell.ConstMediaAction;
import at.petrak.hexcasting.api.spell.OperationResult;
import at.petrak.hexcasting.api.spell.OperatorUtils;
import at.petrak.hexcasting.api.spell.casting.CastingContext;
import at.petrak.hexcasting.api.spell.casting.eval.SpellContinuation;
import at.petrak.hexcasting.api.spell.iota.Iota;
import dev.architectury.utils.GameInstance;
import me.nanorasmus.nanodev.hexcircus.networking.NetworkingHandler;
import me.nanorasmus.nanodev.hexcircus.networking.custom.SpawnBezierParticle;
import me.nanorasmus.nanodev.hexcircus.particle.CastingParticles;
import net.minecraft.client.particle.Particle;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class OpBeizerParticle implements ConstMediaAction {
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
    public int getArgc() { return 5; }

    @Override
    public int getMediaCost() { return 5; }

    @NotNull
    @Override
    public List<Iota> execute(@NotNull List<? extends Iota> args, @NotNull CastingContext ctx) {
        // Get player in question
        Vec3d a = OperatorUtils.getVec3(args, 0, getArgc());
        ctx.assertVecInRange(a);
        Vec3d b = OperatorUtils.getVec3(args, 1, getArgc());
        ctx.assertVecInRange(b);
        Vec3d c = OperatorUtils.getVec3(args, 2, getArgc());
        ctx.assertVecInRange(b);
        Vec3d color = OperatorUtils.getVec3(args, 3, getArgc());
        Double speed = OperatorUtils.getDoubleBetween(args, 4, 0.1, 5, getArgc());


        // Send to all players
        ArrayList<ServerPlayerEntity> players = new ArrayList<>();
        players.addAll(GameInstance.getServer().getPlayerManager().getPlayerList());

        for (int i = 0; i < players.size(); i++) {
            ServerPlayerEntity p = players.get(i);
            NetworkingHandler.CHANNEL.sendToPlayer(p, new SpawnBezierParticle(a, b, c, speed.floatValue(), color));
        }

        return new ArrayList<>();
    }


    @NotNull
    @Override
    public OperationResult operate(SpellContinuation continuation, List<Iota> stack, Iota ravenmind, CastingContext castingContext){
        return DefaultImpls.operate(this, continuation, stack, ravenmind, castingContext);
    }
}
