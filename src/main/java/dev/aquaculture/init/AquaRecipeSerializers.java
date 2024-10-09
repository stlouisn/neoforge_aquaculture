package dev.aquaculture.init;

import dev.aquaculture.Aquaculture;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AquaRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> IRECIPE_SERIALIZERS_DEFERRED = DeferredRegister.create(Registries.RECIPE_SERIALIZER, Aquaculture.MOD_ID);

}
