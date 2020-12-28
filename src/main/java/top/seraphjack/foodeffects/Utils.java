package top.seraphjack.foodeffects;

import com.mojang.datafixers.util.Pair;
import net.minecraft.item.Food;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.function.Supplier;

import static top.seraphjack.foodeffects.FoodEffects.logger;

public class Utils {
    private static Field EFFECTS;

    static {
        try {
            Field MODIFIERS = Field.class.getDeclaredField("modifiers");
            EFFECTS = ObfuscationReflectionHelper.findField(Food.class, "field_221475_f");
            MODIFIERS.setAccessible(true);
            MODIFIERS.setInt(EFFECTS, EFFECTS.getModifiers() & ~Modifier.FINAL);
        } catch (Exception e) {
            logger.error("Failed to initialize reflection utils", e);
            e.printStackTrace();
        }
    }

    public static void setEffects(List<Pair<Supplier<EffectInstance>, Float>> effects, Food food) {
        try {
            EFFECTS.set(food, effects);
        } catch (IllegalAccessException e) {
            logger.error("Failed to modify effects", e);
        }
    }

    public static Pair<Supplier<EffectInstance>, Float> parseFoodEffect(POJOFoodEffect effect) {
        return Pair.of(() -> getEffectInstance(effect), effect.possibility);
    }

    public static EffectInstance getEffectInstance(POJOFoodEffect effect) {
        Effect potion = ForgeRegistries.POTIONS.getValue(new ResourceLocation(effect.effect));
        if (potion == null) {
            throw new IllegalArgumentException("Unknown effect type: " + effect.effect);
        }
        return new EffectInstance(potion, effect.duration, effect.amplifier);
    }
}
