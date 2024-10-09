package dev.aquaculture.client.gui.screen;

import dev.aquaculture.Aquaculture;
import dev.aquaculture.inventory.container.TackleBoxContainer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import javax.annotation.Nonnull;

public class TackleBoxScreen extends AbstractContainerScreen<TackleBoxContainer> {
    private static final ResourceLocation TACKLE_BOX_GUI = ResourceLocation.fromNamespaceAndPath(Aquaculture.MOD_ID, "textures/gui/container/tackle_box.png");

    public TackleBoxScreen(TackleBoxContainer tackleBoxContainer, Inventory playerInventory, Component title) {
        super(tackleBoxContainer, playerInventory, title);
        this.imageHeight = 172;
    }

    @Override
    public void render(@Nonnull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(@Nonnull GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, this.title, 100, 6, 4210752, false);
        guiGraphics.drawString(this.font, this.playerInventoryTitle, 8, (this.imageHeight - 96 + 4), 4210752, false);
    }

    @Override
    protected void renderBg(@Nonnull GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(TACKLE_BOX_GUI, x, y, 0, 0, this.imageWidth, this.imageHeight);

        if (this.menu.slotHook != null && this.menu.slotHook.isActive()) { //Only checking one slot, since they're all disabled at the same time
            if (this.menu.slotHook.hasItem()) {
                this.renderEmptySlot(x + 105, y + 43, guiGraphics);
            } else {
                guiGraphics.blit(TACKLE_BOX_GUI, x + 105, y + 43, 176, 0, 18, 18);
            }
            if (this.menu.slotBait.hasItem()) {
                this.renderEmptySlot(x + 128, y + 43, guiGraphics);
            } else {
                guiGraphics.blit(TACKLE_BOX_GUI, x + 128, y + 43, 176, 18, 18, 18);
            }
            if (this.menu.slotLine.hasItem()) {
                this.renderEmptySlot(x + 105, y + 66, guiGraphics);
            } else {
                guiGraphics.blit(TACKLE_BOX_GUI, x + 105, y + 66, 176, 36, 18, 18);
            }
            if (this.menu.slotBobber.hasItem()) {
                this.renderEmptySlot(x + 128, y + 66, guiGraphics);
            } else {
                guiGraphics.blit(TACKLE_BOX_GUI, x + 128, y + 66, 176, 54, 18, 18);
            }
        }
    }

    private void renderEmptySlot(int x, int y, GuiGraphics guiGraphics) {
        guiGraphics.blit(TackleBoxScreen.TACKLE_BOX_GUI, x, y, 7, 7, 18, 18);
    }
}