package dev.aquaculture.init;

import dev.aquaculture.Aquaculture;
import dev.aquaculture.misc.AquaConfig;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.FishingHookPredicate;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.LootTableLoadEvent;

import java.lang.reflect.Field;
import java.util.ArrayList;

@EventBusSubscriber(modid = Aquaculture.MOD_ID)
public class AquaLootTables {

    public static final ResourceKey<LootTable> FISH = register("gameplay/fishing/fish");
    public static final ResourceKey<LootTable> JUNK = register("gameplay/fishing/junk");
    public static final ResourceKey<LootTable> LAVA_FISHING = register("gameplay/fishing/lava/fishing");
    public static final ResourceKey<LootTable> LAVA_FISH = register("gameplay/fishing/lava/fish");
    public static final ResourceKey<LootTable> LAVA_JUNK = register("gameplay/fishing/lava/junk");
    public static final ResourceKey<LootTable> LAVA_TREASURE = register("gameplay/fishing/lava/treasure");
    public static final ResourceKey<LootTable> NETHER_FISHING = register("gameplay/fishing/nether/fishing");
    public static final ResourceKey<LootTable> NETHER_FISH = register("gameplay/fishing/nether/fish");
    public static final ResourceKey<LootTable> NETHER_JUNK = register("gameplay/fishing/nether/junk");
    public static final ResourceKey<LootTable> NETHER_TREASURE = register("gameplay/fishing/nether/treasure");

    private static ResourceKey<LootTable> register(String path) {
        return BuiltInLootTables.register(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(Aquaculture.MOD_ID, path)));
    }

    @SubscribeEvent
    public static void onLootTableLoad(LootTableLoadEvent event) {
        ResourceLocation name = event.getName();
        if (name != null && name.equals(BuiltInLootTables.FISHING.location())) {
            LootPool pool = event.getTable().getPool("main");
            if (pool != null) {
                addEntry(pool, getInjectEntry(FISH, 85, -1));
                addEntry(pool, getInjectEntry(JUNK, 10, -2));
            }
        }
    }

    private static LootPoolEntryContainer getInjectEntry(ResourceKey<LootTable> resourceKey, int weight, int quality) {
        return NestedLootTable.lootTableReference(resourceKey).setWeight(weight).setQuality(quality).build();
    }

    private static void addEntry(LootPool pool, LootPoolEntryContainer entry) {
        try {
            Field entries = LootPool.class.getDeclaredField("entries");
            entries.setAccessible(true);

            ArrayList<LootPoolEntryContainer> lootPoolEntriesArray = new ArrayList<>(pool.entries);
            ArrayList<LootPoolEntryContainer> newLootEntries = new ArrayList<>(lootPoolEntriesArray);

            if (newLootEntries.stream().anyMatch(e -> e == entry)) {
                throw new RuntimeException("Attempted to add a duplicate entry to pool: " + entry);
            }

            newLootEntries.add(entry);
            entries.set(pool, newLootEntries);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            Aquaculture.LOG.error("Error occurred when attempting to add a new entry, to the fishing loot table");
            e.printStackTrace();
        }
    }
}