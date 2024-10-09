package dev.aquaculture.misc;

import com.mojang.blaze3d.platform.InputConstants;
import dev.aquaculture.Aquaculture;
import dev.aquaculture.api.AquacultureAPI;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid = Aquaculture.MOD_ID, value = Dist.CLIENT)
public class AquaTooltip {

    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (!stack.isEmpty()) {
            Item item = stack.getItem();
            if (stack.is(AquacultureAPI.Tags.TOOLTIP)) {
                ResourceLocation id = BuiltInRegistries.ITEM.getKey(item);
                if (id != null) {
                    String itemIdentifier = id.getPath() + ".tooltip";
                    if (InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_LEFT_SHIFT)) {
                        event.getToolTip().add(Component.translatable(Aquaculture.MOD_ID + "." + itemIdentifier + ".desc").withStyle(ChatFormatting.AQUA));
                    } else {
                        event.getToolTip().add(Component.translatable(Aquaculture.MOD_ID + "." + itemIdentifier + ".title").withStyle(ChatFormatting.AQUA)
                                .append(" ").append(Component.translatable(Aquaculture.MOD_ID + ".shift").withStyle(ChatFormatting.DARK_GRAY)));
                    }
                }
            }
           //stack.getTags().forEach((tag) -> event.getToolTip().add(Component.literal(tag.location().toString()))); //Debug code for tooltips. Comment out when not used
        }
    }
}