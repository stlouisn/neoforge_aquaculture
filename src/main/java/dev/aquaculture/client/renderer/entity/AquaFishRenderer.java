package dev.aquaculture.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.aquaculture.Aquaculture;
import dev.aquaculture.client.ClientHandler;
import dev.aquaculture.client.renderer.entity.layers.JellyfishLayer;
import dev.aquaculture.client.renderer.entity.model.FishCatfishModel;
import dev.aquaculture.client.renderer.entity.model.FishLargeModel;
import dev.aquaculture.client.renderer.entity.model.FishLongnoseModel;
import dev.aquaculture.client.renderer.entity.model.FishMediumModel;
import dev.aquaculture.client.renderer.entity.model.FishSmallModel;
import dev.aquaculture.client.renderer.entity.model.JellyfishModel;
import dev.aquaculture.entity.AquaFishEntity;
import dev.aquaculture.entity.FishType;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.TropicalFishModelB;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import javax.annotation.Nonnull;

public class AquaFishRenderer extends MobRenderer<AquaFishEntity, EntityModel<AquaFishEntity>> {
    private static final ResourceLocation DEFAULT_LOCATION = ResourceLocation.fromNamespaceAndPath(Aquaculture.MOD_ID, "textures/entity/fish/cod.png");
    private final TropicalFishModelB<AquaFishEntity> tropicalFishBModel;
    private final FishSmallModel<AquaFishEntity> smallModel;
    private final FishMediumModel<AquaFishEntity> mediumModel;
    private final FishLargeModel<AquaFishEntity> largeModel;
    private final FishLongnoseModel<AquaFishEntity> longnoseModel;
    private final FishCatfishModel<AquaFishEntity> catfishModel;
    private final JellyfishModel<AquaFishEntity> jellyfishModel;

    public AquaFishRenderer(EntityRendererProvider.Context context, boolean isJellyfish) {
        super(context, new FishMediumModel<>(context.bakeLayer(ClientHandler.MEDIUM_MODEL)), 0.35F);
        this.tropicalFishBModel = new TropicalFishModelB<>(context.bakeLayer(ModelLayers.TROPICAL_FISH_LARGE));
        this.smallModel = new FishSmallModel<>(context.bakeLayer(ClientHandler.SMALL_MODEL));
        this.mediumModel = new FishMediumModel<>(context.bakeLayer(ClientHandler.MEDIUM_MODEL));
        this.largeModel = new FishLargeModel<>(context.bakeLayer(ClientHandler.LARGE_MODEL));
        this.longnoseModel = new FishLongnoseModel<>(context.bakeLayer(ClientHandler.LONGNOSE_MODEL));
        this.catfishModel = new FishCatfishModel<>(context.bakeLayer(ClientHandler.CATFISH_MODEL));
        this.jellyfishModel = new JellyfishModel<>(context.bakeLayer(ClientHandler.JELLYFISH_MODEL));

        if (isJellyfish) {
          this.addLayer(new JellyfishLayer(this, context.getModelSet()));
        }
    }

    @Override
    public void render(@Nonnull AquaFishEntity fishEntity, float entityYaw, float partialTicks, @Nonnull PoseStack matrixStack, @Nonnull MultiBufferSource buffer, int i) {
      if (fishEntity != null) {
            switch (fishEntity.getFishType()) {
                case SMALL -> this.model = smallModel;
                case LARGE -> this.model = largeModel;
                case LONGNOSE -> this.model = longnoseModel;
                case CATFISH -> this.model = catfishModel;
                case JELLYFISH -> this.model = jellyfishModel;
                case HALIBUT -> this.model = tropicalFishBModel;
                default -> this.model = mediumModel;
            }
            super.render(fishEntity, entityYaw, partialTicks, matrixStack, buffer, i);
        }
    }

    @Override
    @Nonnull
    public ResourceLocation getTextureLocation(@Nonnull AquaFishEntity fishEntity) {
        ResourceLocation location = BuiltInRegistries.ENTITY_TYPE.getKey(fishEntity.getType());
      if (location != null) {
            return ResourceLocation.fromNamespaceAndPath(Aquaculture.MOD_ID, "textures/entity/fish/" + location.getPath() + ".png");
        }
        return DEFAULT_LOCATION;
    }

    @Override
    protected void setupRotations(@Nonnull AquaFishEntity fishEntity, @Nonnull PoseStack matrixStack, float ageInTicks, float rotationYaw, float partialTicks, float f) {
        super.setupRotations(fishEntity, matrixStack, ageInTicks, rotationYaw, partialTicks, f);
        FishType fishType = fishEntity.getFishType();
        if (fishType != FishType.JELLYFISH) {
            float salmonRotation = 1.0F;
            float salmonMultiplier = 1.0F;
            if (fishType == FishType.LONGNOSE) {
                if (!fishEntity.isInWater()) {
                    salmonRotation = 1.3F;
                    salmonMultiplier = 1.7F;
                }
            }
            float fishRotation = fishType == FishType.LONGNOSE ? salmonRotation * 4.3F * Mth.sin(salmonMultiplier * 0.6F * ageInTicks) : 4.3F * Mth.sin(0.6F * ageInTicks);

            matrixStack.mulPose(Axis.YP.rotationDegrees(fishRotation));
            if (fishType == FishType.LONGNOSE) {
                matrixStack.translate(0.0F, 0.0F, -0.4F);
            }
            if (!fishEntity.isInWater() && fishType != FishType.HALIBUT) {
                if (fishType == FishType.MEDIUM || fishType == FishType.LARGE || fishType == FishType.CATFISH) {
                    matrixStack.translate(0.1F, 0.1F, -0.1F);
                } else {
                    matrixStack.translate(0.2F, 0.1F, 0.0F);
                }
                matrixStack.mulPose(Axis.ZP.rotationDegrees(90));
            }
            if (fishType == FishType.HALIBUT) {
                matrixStack.translate(-0.4F, 0.1F, 0.0F);
                matrixStack.mulPose(Axis.ZP.rotationDegrees(-90));
            }
        }
    }

    @Override
    protected void scale(AquaFishEntity fishEntity, @Nonnull PoseStack matrixStack, float partialTickTime) {
        ResourceLocation location = BuiltInRegistries.ENTITY_TYPE.getKey(fishEntity.getType());
        float scale = 0.0F;
      if (location != null) {
            switch (location.getPath()) {
                case "synodontis" -> scale = 0.8F;
                case "brown_trout", "piranha" -> scale = 0.9F;
                case "pollock" -> scale = 1.1F;
                case "cod", "blackfish", "catfish", "tambaqui" -> scale = 1.2F;
                case "pacific_halibut", "atlantic_halibut", "capitaine", "largemouth_bass", "gar", "arapaima", "tuna" -> scale = 1.4F;
            }
        }
        if (scale > 0) {
            matrixStack.pushPose();
            matrixStack.scale(scale, scale, scale);
            matrixStack.popPose();
        }
    }
}