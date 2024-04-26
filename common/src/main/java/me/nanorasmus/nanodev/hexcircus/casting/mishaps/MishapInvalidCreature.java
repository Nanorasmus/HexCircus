package me.nanorasmus.nanodev.hexcircus.casting.mishaps;

import at.petrak.hexcasting.api.misc.FrozenColorizer;
import at.petrak.hexcasting.api.spell.casting.CastingContext;
import at.petrak.hexcasting.api.spell.iota.Iota;
import at.petrak.hexcasting.api.spell.mishaps.Mishap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MishapInvalidCreature extends Mishap {

    @NotNull
    @Override
    public FrozenColorizer accentColor(@NotNull CastingContext castingContext, @NotNull Mishap.Context context) {
        return dyeColor(DyeColor.GREEN);
    }

    @NotNull
    @Override
    public Text errorMessage(@NotNull CastingContext castingContext, @NotNull Mishap.Context context) {
        return error("hex_circus.invalid_creature");
    }

    @Override
    public void execute(@NotNull CastingContext castingContext, @NotNull Mishap.Context context, @NotNull List<Iota> list) {

    }
}
