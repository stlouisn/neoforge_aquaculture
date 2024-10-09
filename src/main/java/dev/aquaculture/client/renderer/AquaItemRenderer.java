package dev.aquaculture.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.aquaculture.block.TackleBoxBlock;
import dev.aquaculture.block.blockentity.TackleBoxBlockEntity;
import dev.aquaculture.init.AquaBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nonnull;

public class AquaItemRenderer extends BlockEntityWithoutLevelRenderer {

    public AquaItemRenderer(BlockEntityRenderDispatcher renderDispatcher, EntityModelSet entityModelSet) {
        super(renderDispatcher, entityModelSet);
    }

    @Override
    public void renderByItem(@Nonnull ItemStack stack, ItemDisplayContext itemDisplayContext, @Nonnull PoseStack matrixStack, @Nonnull MultiBufferSource buffer, int i, int i1) {
        Minecraft mc = Minecraft.getInstance();
        Item item = stack.getItem();
        if (item instanceof BlockItem) {
            Block block = ((BlockItem) item).getBlock();
            if (block instanceof TackleBoxBlock) {
                mc.getBlockEntityRenderDispatcher().renderItem(new TackleBoxBlockEntity(BlockPos.ZERO, AquaBlocks.TACKLE_BOX.get().defaultBlockState()), matrixStack, buffer, i, i1);
            }
        }
    }
}