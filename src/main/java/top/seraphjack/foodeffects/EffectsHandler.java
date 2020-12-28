package top.seraphjack.foodeffects;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.item.Item;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static top.seraphjack.foodeffects.FoodEffects.logger;

@ParametersAreNonnullByDefault
public class EffectsHandler extends JsonReloadListener {
    private static final Gson GSON = new Gson();
    private static final TypeToken<Map<String, List<POJOFoodEffect>>> TYPE_TOKEN = new TypeToken<Map<String, List<POJOFoodEffect>>>() {
    };

    public EffectsHandler() {
        super(GSON, "food_effects");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> objectIn, IResourceManager resourceManagerIn, IProfiler profilerIn) {
        for (Map.Entry<ResourceLocation, JsonElement> entry : objectIn.entrySet()) {
            ResourceLocation resourceLocation = entry.getKey();
            if (resourceLocation.getPath().startsWith("_")) {
                continue;
            }
            Map<String, List<POJOFoodEffect>> effectMap = GSON.fromJson(entry.getValue(), TYPE_TOKEN.getType());
            effectMap.forEach((registryName, effects) -> {
                Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(registryName));
                if (item == null || item.getFood() == null) {
                    logger.error("Unknown food: " + registryName + ", ignoring...");
                    return;
                }
                try {
                    Utils.setEffects(effects.stream().map(Utils::parseFoodEffect).collect(Collectors.toList()), item.getFood());
                } catch (Exception e) {
                    logger.error("Failed to replace effects for {}", registryName);
                    logger.error(e);
                }
            });
        }
    }
}
