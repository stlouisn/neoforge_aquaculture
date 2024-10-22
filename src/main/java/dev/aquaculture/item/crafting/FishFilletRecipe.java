package dev.aquaculture.item.crafting;

import dev.aquaculture.api.AquacultureAPI;
import dev.aquaculture.api.fish.FishData;
import dev.aquaculture.init.AquaDataComponents;
import dev.aquaculture.init.AquaItems;
import dev.aquaculture.init.AquaRecipeSerializers;
import dev.aquaculture.misc.AquaConfig;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import javax.annotation.Nonnull;

public class FishFilletRecipe extends CustomRecipe {

  public FishFilletRecipe(CraftingBookCategory craftingBookCategory) {
    super(craftingBookCategory);
  }

  @Override
  public boolean matches(@Nonnull CraftingInput craftingInventory, @Nonnull Level world) {
    ItemStack stack = ItemStack.EMPTY;

    for (int i = 0; i < craftingInventory.size(); ++i) {
      ItemStack slotStack = craftingInventory.getItem(i);
      if (!slotStack.isEmpty()) {
        if (AquacultureAPI.FISH_DATA.hasFilletAmount(slotStack.getItem())) {
          if (!stack.isEmpty()) {
            return false;
          }
          stack = slotStack;
        }
        else {
          return false;
        }
      }
    }
    return !stack.isEmpty();
  }

  @Override
  @Nonnull
  public ItemStack assemble(@Nonnull CraftingInput craftingInventory, @Nonnull HolderLookup.Provider provider) {
    ItemStack fish = ItemStack.EMPTY;

    for (int i = 0; i < craftingInventory.size(); ++i) {
      ItemStack stackSlot = craftingInventory.getItem(i);
      if (!stackSlot.isEmpty()) {
        Item item = stackSlot.getItem();
        if (AquacultureAPI.FISH_DATA.hasFilletAmount(item)) {
          if (!fish.isEmpty()) {
            return ItemStack.EMPTY;
          }
          fish = stackSlot.copy();
        }
      }
    }
    if (!fish.isEmpty()) {
      int filletAmount = AquacultureAPI.FISH_DATA.getFilletAmount(fish.getItem());
      Float fishWeight = fish.get(AquaDataComponents.FISH_WEIGHT.get());
      if (AquaConfig.BASIC_OPTIONS.randomWeight.get() && fish.has(AquaDataComponents.FISH_WEIGHT) && fishWeight != null) {
        filletAmount = FishData.getFilletAmountFromWeight(fishWeight);
      }
      return new ItemStack(AquaItems.FISH_FILLET.get(), filletAmount);
    } else {
      return ItemStack.EMPTY;
    }
  }

  @Override
  @Nonnull
  public NonNullList<ItemStack> getRemainingItems(CraftingInput craftingInventory) {
    return NonNullList.withSize(craftingInventory.size(), ItemStack.EMPTY);
  }

  @Override
  @Nonnull
  public RecipeSerializer<?> getSerializer() {
    return AquaRecipeSerializers.FISH_FILLET_SERIALIZER.get();
  }

  @Override
  public boolean canCraftInDimensions(int width, int height) {
    return width * height >= 2;
  }
}