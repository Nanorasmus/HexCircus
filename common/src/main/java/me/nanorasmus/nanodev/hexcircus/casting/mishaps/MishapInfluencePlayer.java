package me.nanorasmus.nanodev.hexcircus.casting.mishaps;

import at.petrak.hexcasting.api.misc.FrozenColorizer;
import at.petrak.hexcasting.api.spell.casting.CastingContext;
import at.petrak.hexcasting.api.spell.casting.CastingHarness;
import at.petrak.hexcasting.api.spell.iota.EntityIota;
import at.petrak.hexcasting.api.spell.iota.Iota;
import at.petrak.hexcasting.api.spell.mishaps.Mishap;
import at.petrak.hexcasting.common.items.ItemStaff;
import at.petrak.hexcasting.xplat.IXplatAbstractions;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MishapInfluencePlayer extends Mishap {
    ServerPlayerEntity influencer;
    ServerPlayerEntity influencee;

    public MishapInfluencePlayer(ServerPlayerEntity influencer, ServerPlayerEntity influencee) {
        this.influencer = influencer;
        this.influencee = influencee;
    }

    @NotNull
    @Override
    public FrozenColorizer accentColor(@NotNull CastingContext castingContext, @NotNull Mishap.Context context) {
        return dyeColor(DyeColor.BLACK);
    }

    @NotNull
    @Override
    public Text errorMessage(@NotNull CastingContext castingContext, @NotNull Mishap.Context context) {
        return error("hex_circus.influence_player.influencer");
    }

    @Override
    public void execute(@NotNull CastingContext castingContext, @NotNull Mishap.Context context, @NotNull List<Iota> list) {
        // Give influencer negative status effects
        influencer.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 400, 3));
        influencer.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 400, 3));
        influencer.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 400, 3));


        // Tell Influencee an attack was attempted
        influencee.sendMessage(error("hex_circus.influence_player.influencee", influencer.getDisplayName().getString()));
    }
}