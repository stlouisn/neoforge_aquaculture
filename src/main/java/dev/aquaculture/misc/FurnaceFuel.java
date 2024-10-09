package dev.aquaculture.misc;

import dev.aquaculture.Aquaculture;
import dev.aquaculture.init.AquaItems;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.furnace.FurnaceFuelBurnTimeEvent;

@EventBusSubscriber(modid = Aquaculture.MOD_ID)
public class FurnaceFuel {

    @SubscribeEvent
    public static void fuel(FurnaceFuelBurnTimeEvent event) {
        ItemStack stack = event.getItemStack();
        if (stack.getItem() == AquaItems.DRIFTWOOD.get()) {
            event.setBurnTime(600);
        }
    }
}