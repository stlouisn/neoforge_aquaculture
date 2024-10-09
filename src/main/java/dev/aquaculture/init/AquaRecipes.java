package dev.aquaculture.init;

import dev.aquaculture.Aquaculture;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;

@EventBusSubscriber(modid = Aquaculture.MOD_ID)
public class AquaRecipes {

    @SubscribeEvent
    public static void registerBrewingRecipes(RegisterBrewingRecipesEvent event) {
        event.getBuilder().addStartMix(AquaItems.JELLYFISH.get(), Potions.POISON);
    }
}