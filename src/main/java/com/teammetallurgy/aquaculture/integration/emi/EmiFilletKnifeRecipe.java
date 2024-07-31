package com.teammetallurgy.aquaculture.integration.emi;

import dev.emi.emi.api.recipe.EmiCraftingRecipe;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class EmiFilletKnifeRecipe extends EmiCraftingRecipe {

    public EmiFilletKnifeRecipe(List<EmiIngredient> input, EmiStack output, ResourceLocation id) {
        super(input, output, id);
    }
}