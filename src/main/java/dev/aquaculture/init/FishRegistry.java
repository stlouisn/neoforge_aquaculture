package dev.aquaculture.init;

import dev.aquaculture.Aquaculture;
import dev.aquaculture.entity.AquaFishEntity;
import dev.aquaculture.entity.FishType;
import dev.aquaculture.item.AquaFishBucket;
import dev.aquaculture.loot.BiomeTagCheck;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.RegisterEvent;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@EventBusSubscriber(modid = Aquaculture.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class FishRegistry {
    public static List<DeferredHolder<EntityType<?>, EntityType<AquaFishEntity>>> fishEntities = new ArrayList<>();

    /**
     * Same as {@link #register(Supplier, String, FishType)}, but with default size
     */
    public static DeferredItem<Item> register(@Nonnull Supplier<Item> initializer, @Nonnull String name) {
        return register(initializer, name, FishType.MEDIUM);
    }

    /**
     * Registers the fish item, fish entity and fish bucket
     *
     * @param initializer The fish initializer
     * @param name        The fish name
     * @return The fish Item that was registered
     */
    public static DeferredItem<Item> register(@Nonnull Supplier<Item> initializer, @Nonnull String name, FishType fishType) {
        DeferredHolder<EntityType<?>, EntityType<AquaFishEntity>> fish = AquaEntities.ENTITY_DEFERRED.register(name, () -> EntityType.Builder.<AquaFishEntity>of((f, w) -> new AquaFishEntity(f, w, fishType), MobCategory.WATER_AMBIENT).sized(fishType.getWidth(), fishType.getHeight()).build(Aquaculture.MOD_ID + ":" + name));
        fishEntities.add(fish);

        //Registers fish buckets
        DeferredItem<Item> bucket = AquaItems.ITEM_DEFERRED.register(name + "_bucket", () -> new AquaFishBucket(fish.value(), new Item.Properties().stacksTo(1)));
        AquaItems.ITEMS_FOR_TAB_LIST.add(bucket);

        return AquaItems.registerWithTab(initializer, name);
    }

    @SubscribeEvent
    public static void registerFishies(RegisterEvent event) {
        if (!event.getRegistryKey().equals(Registries.LOOT_CONDITION_TYPE)) return;
        event.register(Registries.LOOT_CONDITION_TYPE, ResourceLocation.fromNamespaceAndPath(Aquaculture.MOD_ID, "biome_tag_check"), () -> BiomeTagCheck.BIOME_TAG_CHECK);
    }
}