package top.seraphjack.foodeffects;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(FoodEffects.MOD_ID)
public class FoodEffects {
    public static final String MOD_ID = "foodeffects";
    public static final Logger logger = LogManager.getLogger();

    public FoodEffects() {
        MinecraftForge.EVENT_BUS.addListener(this::onReloading);
    }

    public void onReloading(AddReloadListenerEvent event) {
        event.addListener(new EffectsHandler());
    }
}
