package dev.aquaculture.init;

import dev.aquaculture.Aquaculture;
import dev.aquaculture.entity.AquaFishEntity;
import dev.aquaculture.entity.AquaFishingBobberEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

@EventBusSubscriber(modid = Aquaculture.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class AquaEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_DEFERRED = DeferredRegister.create(Registries.ENTITY_TYPE, Aquaculture.MOD_ID);
    public static final DeferredHolder<EntityType<?>, EntityType<AquaFishingBobberEntity>> BOBBER = register("bobber", () -> EntityType.Builder.<AquaFishingBobberEntity>of(AquaFishingBobberEntity::new, MobCategory.MISC)
                                                                                                                                               .noSave()
                                                                                                                                               .noSummon()
                                                                                                                                               .sized(0.25F, 0.25F)
                                                                                                                                               .setTrackingRange(4)
                                                                                                                                               .setUpdateInterval(5));

    public static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> register(String name, Supplier<EntityType.Builder<T>> builder) {
        ResourceLocation location = ResourceLocation.fromNamespaceAndPath(Aquaculture.MOD_ID, name);
        return ENTITY_DEFERRED.register(name, () -> builder.get().build(location.toString()));
    }

    @SubscribeEvent
    public static void setSpawnPlacement(RegisterSpawnPlacementsEvent event) {

        for (DeferredHolder<EntityType<?>, EntityType<AquaFishEntity>> entityType : FishRegistry.fishEntities) {
            event.register(entityType.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AquaFishEntity::checkSurfaceWaterAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        }
    }

    @SubscribeEvent
    public static void addEntityAttributes(EntityAttributeCreationEvent event) {

        for (DeferredHolder<EntityType<?>, EntityType<AquaFishEntity>> entityType : FishRegistry.fishEntities) {
            event.put(entityType.get(), AquaFishEntity.createAttributes().build());
        }
    }
}