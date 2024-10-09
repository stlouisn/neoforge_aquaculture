package dev.aquaculture.api;

import dev.aquaculture.Aquaculture;
import dev.aquaculture.api.fish.FishData;
import dev.aquaculture.api.fishing.Hook;
import dev.aquaculture.init.AquaItems;
import dev.aquaculture.init.FishRegistry;
import dev.aquaculture.item.BaitItem;
import dev.aquaculture.item.HookItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;

import javax.annotation.Nonnull;

public class AquacultureAPI {
    /**
     * Reference to setting weight for fish
     **/
    public static FishData FISH_DATA = new FishData();

    public static BaitItem createBait(int durability, int lureSpeedModifier) {
        return new BaitItem(durability, lureSpeedModifier);
    }

    public static DeferredItem<Item> registerFishMount(@Nonnull String name) {
        return FishRegistry.registerFishMount(name);
    }

    public static DeferredItem<Item> registerHook(Hook hook) {
        DeferredItem<Item> hookItem = AquaItems.registerWithTab(() -> new HookItem(hook), hook.getName() + "_hook");
        Hook.HOOKS.put(hook.getName(), hookItem);
        return hookItem;
    }

    public static class Tags {
        public static final TagKey<Item> FISHING_LINE = tag(Aquaculture.MOD_ID, "fishing_line");
        public static final TagKey<Item> BOBBER = tag(Aquaculture.MOD_ID, "bobber");
        public static final TagKey<Item> TACKLE_BOX = tag(Aquaculture.MOD_ID, "tackle_box");
        public static final TagKey<Item> TOOLTIP = tag(Aquaculture.MOD_ID, "tooltip");

        public static TagKey<Item> tag(String modID, String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(modID, name));
        }

        public static void init() {
        }
    }
}
