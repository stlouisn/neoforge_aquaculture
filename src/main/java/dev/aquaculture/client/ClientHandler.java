package dev.aquaculture.client;

import dev.aquaculture.Aquaculture;
import dev.aquaculture.client.gui.screen.TackleBoxScreen;
import dev.aquaculture.client.renderer.entity.AquaBobberRenderer;
import dev.aquaculture.client.renderer.entity.AquaFishRenderer;
import dev.aquaculture.client.renderer.entity.FishMountRenderer;
import dev.aquaculture.client.renderer.entity.model.FishCathfishModel;
import dev.aquaculture.client.renderer.entity.model.FishLargeModel;
import dev.aquaculture.client.renderer.entity.model.FishLongnoseModel;
import dev.aquaculture.client.renderer.entity.model.FishMediumModel;
import dev.aquaculture.client.renderer.entity.model.FishSmallModel;
import dev.aquaculture.client.renderer.entity.model.JellyfishModel;
import dev.aquaculture.client.renderer.tileentity.TackleBoxRenderer;
import dev.aquaculture.entity.AquaFishEntity;
import dev.aquaculture.entity.FishMountEntity;
import dev.aquaculture.init.AquaBlockEntities;
import dev.aquaculture.init.AquaEntities;
import dev.aquaculture.init.AquaGuis;
import dev.aquaculture.init.AquaItems;
import dev.aquaculture.init.FishRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.DyedItemColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

@EventBusSubscriber(modid = Aquaculture.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ClientHandler {
    public static final ModelLayerLocation TACKLE_BOX = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Aquaculture.MOD_ID, "tackle_box"), "tackle_box");
    public static final ModelLayerLocation SMALL_MODEL = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Aquaculture.MOD_ID, "small_model"), "small_model");
    public static final ModelLayerLocation MEDIUM_MODEL = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Aquaculture.MOD_ID, "medium_model"), "medium_model");
    public static final ModelLayerLocation LARGE_MODEL = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Aquaculture.MOD_ID, "large_model"), "large_model");
    public static final ModelLayerLocation LONGNOSE_MODEL = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Aquaculture.MOD_ID, "longnose_model"), "longnose_model");
    public static final ModelLayerLocation CATFISH_MODEL = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Aquaculture.MOD_ID, "catfish_model"), "catfish_model");
    public static final ModelLayerLocation JELLYFISH_MODEL = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Aquaculture.MOD_ID, "jellyfish_model"), "jellyfish_model");

    public static void setupClient() {
        BlockEntityRenderers.register(AquaBlockEntities.TACKLE_BOX.get(), TackleBoxRenderer::new);

        registerFishingRodModelProperties(AquaItems.IRON_FISHING_ROD.get());
        registerFishingRodModelProperties(AquaItems.GOLD_FISHING_ROD.get());
        registerFishingRodModelProperties(AquaItems.DIAMOND_FISHING_ROD.get());
    }

    @SubscribeEvent
    public static void registerMenuScreen(RegisterMenuScreensEvent event) {
        event.register(AquaGuis.TACKLE_BOX.get(), TackleBoxScreen::new);
    }

    @SubscribeEvent
    public static void registerColors(RegisterColorHandlersEvent.Item event) {
        event.register((stack, tintIndex) -> tintIndex > 0 ? -1 : DyedItemColor.getOrDefault(stack, FastColor.ARGB32.color(0, 0, 0)), AquaItems.FISHING_LINE.get());
        event.register((stack, tintIndex) -> tintIndex > 0 ? -1 : DyedItemColor.getOrDefault(stack, FastColor.ARGB32.color(193, 38, 38)), AquaItems.BOBBER.get());
    }

    @SubscribeEvent
    public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(AquaEntities.BOBBER.get(), AquaBobberRenderer::new);
        for (DeferredHolder<EntityType<?>, EntityType<AquaFishEntity>> fish : FishRegistry.fishEntities) {
            event.registerEntityRenderer(fish.get(), (context) -> new AquaFishRenderer(context, BuiltInRegistries.ENTITY_TYPE.getKey(fish.get()).equals(ResourceLocation.fromNamespaceAndPath(Aquaculture.MOD_ID, "jellyfish"))));
        }
        for (DeferredHolder<EntityType<?>, EntityType<FishMountEntity>> fishMount : FishRegistry.fishMounts) {
            event.registerEntityRenderer(fishMount.get(), FishMountRenderer::new);
        }
    }

    @SubscribeEvent
    public static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(TACKLE_BOX, TackleBoxRenderer::createLayer);
        event.registerLayerDefinition(SMALL_MODEL, FishSmallModel::createBodyLayer);
        event.registerLayerDefinition(MEDIUM_MODEL, FishMediumModel::createBodyLayer);
        event.registerLayerDefinition(LARGE_MODEL, FishLargeModel::createBodyLayer);
        event.registerLayerDefinition(LONGNOSE_MODEL, FishLongnoseModel::createBodyLayer);
        event.registerLayerDefinition(CATFISH_MODEL, FishCathfishModel::createBodyLayer);
        event.registerLayerDefinition(JELLYFISH_MODEL, JellyfishModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerModels(ModelEvent.RegisterAdditional event) {
        event.register(ModelResourceLocation.standalone(ResourceLocation.fromNamespaceAndPath(Aquaculture.MOD_ID, "block/oak_fish_mount")));
        event.register(ModelResourceLocation.standalone(ResourceLocation.fromNamespaceAndPath(Aquaculture.MOD_ID, "block/spruce_fish_mount")));
        event.register(ModelResourceLocation.standalone(ResourceLocation.fromNamespaceAndPath(Aquaculture.MOD_ID, "block/birch_fish_mount")));
        event.register(ModelResourceLocation.standalone(ResourceLocation.fromNamespaceAndPath(Aquaculture.MOD_ID, "block/jungle_fish_mount")));
        event.register(ModelResourceLocation.standalone(ResourceLocation.fromNamespaceAndPath(Aquaculture.MOD_ID, "block/acacia_fish_mount")));
        event.register(ModelResourceLocation.standalone(ResourceLocation.fromNamespaceAndPath(Aquaculture.MOD_ID, "block/dark_oak_fish_mount")));
    }

    public static void registerFishingRodModelProperties(Item fishingRod) {
        ItemProperties.register(fishingRod, ResourceLocation.withDefaultNamespace("cast"), (stack, level, entity, i) -> {
            if (entity == null) {
                return 0.0F;
            } else {
                boolean isMainhand = entity.getMainHandItem() == stack;
                boolean isOffHand = entity.getOffhandItem() == stack;
                if (entity.getMainHandItem().getItem() instanceof FishingRodItem) {
                    isOffHand = false;
                }
                return (isMainhand || isOffHand) && entity instanceof Player && ((Player) entity).fishing != null ? 1.0F : 0.0F;
            }
        });
    }

}