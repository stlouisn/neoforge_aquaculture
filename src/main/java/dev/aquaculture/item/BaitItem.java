package dev.aquaculture.item;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class BaitItem extends Item {

    private int usesLeft;

    private final int lureSpeedModifier;

    public BaitItem(int usesMax, int lureSpeedModifier) {
        super(new Item.Properties());
        this.usesLeft = usesMax;
        this.lureSpeedModifier = lureSpeedModifier;
    }

    public int getUsesLeft() {
        return usesLeft;
    }

    public void setUsesLeft(int usesLeft) {
        this.usesLeft = usesLeft;
    }

    public void decreaseUsesLeft(ItemStack stack, Player player) {
        this.usesLeft--;
        if (this.usesLeft <= 0) {
            stack.shrink(1);
            player.displayClientMessage(Component.translatable("aquaculture.bait.used.message"),false);
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@NotNull ItemStack stack, Item.@NotNull TooltipContext tooltipContext, List<Component> tooltips, @NotNull TooltipFlag tooltipFlag) {
        MutableComponent usesLeftTooltip = Component.translatable("aquaculture.bait.uses_left.tooltip", this.usesLeft);
        tooltips.add(usesLeftTooltip.withStyle(usesLeftTooltip.getStyle().withColor(ChatFormatting.GRAY)));
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