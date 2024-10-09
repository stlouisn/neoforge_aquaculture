package dev.aquaculture.misc;

import javax.annotation.Nonnull;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.IItemHandler;

public class StackHelper {

  public static void giveItem(Player player, @Nonnull ItemStack stack) {
    if (!player.getInventory().add(stack)) {
      player.drop(stack, false);
    }
    else if (player instanceof ServerPlayer) {
      player.inventoryMenu.sendAllDataToRemote();
    }
  }

  public static void dropInventory(Level world, BlockPos pos, IItemHandler handler) {
    for (int slot = 0; slot < handler.getSlots(); ++slot) {
      Containers.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), handler.getStackInSlot(slot));
    }
  }
}