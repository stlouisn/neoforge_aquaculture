package dev.aquaculture.item;

import javax.annotation.Nonnull;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class BaitItem extends Item {

  private final double useChance;
  private final int lureSpeedModifier;

  public BaitItem(double useChance, int lureSpeedModifier) {
    super(new Item.Properties());
    this.useChance = useChance;
    this.lureSpeedModifier = lureSpeedModifier;
  }

  public double getUseChance() {
    return this.useChance;
  }

  public int getLureSpeedModifier() {
    return this.lureSpeedModifier;
  }

  @Override
  public int getMaxStackSize(@Nonnull ItemStack stack) {
    return 64;
  }

  @Override
  public int getBarColor(@Nonnull ItemStack stack) {
    return 16761035;
  }
}