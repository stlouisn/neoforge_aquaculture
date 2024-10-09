package dev.aquaculture;

import dev.aquaculture.api.AquacultureAPI;
import dev.aquaculture.api.fishing.Hooks;
import dev.aquaculture.client.ClientHandler;
import dev.aquaculture.init.AquaBlockEntities;
import dev.aquaculture.init.AquaBlocks;
import dev.aquaculture.init.AquaDataComponents;
import dev.aquaculture.init.AquaEntities;
import dev.aquaculture.init.AquaGuis;
import dev.aquaculture.init.AquaItems;
import dev.aquaculture.init.AquaRecipeSerializers;
import dev.aquaculture.init.AquaSounds;
import dev.aquaculture.item.AquaFishingRodItem;
import dev.aquaculture.item.crafting.ConditionFactory;
import dev.aquaculture.loot.AquaBiomeModifiers;
import dev.aquaculture.loot.FishWeightHandler;
import dev.aquaculture.misc.AquaConfig;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(value = Aquaculture.MOD_ID)
public class Aquaculture {
    public static Aquaculture instance;
    public final static String MOD_ID = "aquaculture";
    public static final Logger LOG = LogManager.getLogger(MOD_ID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> GROUP = CREATIVE_TABS.register("tab", () -> new CreativeModeTab.Builder(CreativeModeTab.Row.TOP, 0)
        .icon(() -> new ItemStack(AquaItems.IRON_FISHING_ROD.get()))
        .title(Component.translatable("tabs." + MOD_ID + ".tab"))
        .displayItems((featureFlagSet, tabOutput) -> AquaItems.ITEMS_FOR_TAB_LIST.forEach(registryObject -> tabOutput.accept(new ItemStack(registryObject.get())))).build()
    );

    public Aquaculture(ModContainer modContainer, IEventBus modBus) {
        instance = this;
        modBus.addListener(this::setupCommon);
        modBus.addListener(this::setupClient);
        this.registerDeferredRegistries(modBus);
        modBus.addListener(this::registerCapabilities);
        modBus.addListener(this::addItemsToTabs);
        modContainer.registerConfig(ModConfig.Type.COMMON, AquaConfig.spec);
        AquacultureAPI.Tags.init();

        AquaBiomeModifiers.BIOME_MODIFIER_SERIALIZERS_DEFERRED.register("mob_spawn", AquaBiomeModifiers.MobSpawnBiomeModifier::makeCodec);
        AquaBiomeModifiers.BIOME_MODIFIER_SERIALIZERS_DEFERRED.register("fish_spawn", AquaBiomeModifiers.FishSpawnBiomeModifier::makeCodec);
    }

    private void setupCommon(FMLCommonSetupEvent event) {
        event.enqueueWork(Hooks::load);
        event.enqueueWork(FishWeightHandler::registerFishData);
    }

    private void setupClient(FMLClientSetupEvent event) {
        event.enqueueWork(ClientHandler::setupClient);
    }

    public void registerDeferredRegistries(IEventBus modBus) {
        AquaBlocks.BLOCK_DEFERRED.register(modBus);
        AquaItems.ITEM_DEFERRED.register(modBus);
        AquaDataComponents.DATA_COMPONENT_TYPE_DEFERRED.register(modBus);
        CREATIVE_TABS.register(modBus);
        AquaBlockEntities.BLOCK_ENTITY_DEFERRED.register(modBus);
        AquaEntities.ENTITY_DEFERRED.register(modBus);
        AquaSounds.SOUND_EVENT_DEFERRED.register(modBus);
        AquaGuis.MENU_DEFERRED.register(modBus);
        AquaRecipeSerializers.IRECIPE_SERIALIZERS_DEFERRED.register(modBus);
        AquaBiomeModifiers.BIOME_MODIFIER_SERIALIZERS_DEFERRED.register(modBus);
        ConditionFactory.CONDITION_CODECS.register(modBus);
    }

    private void addItemsToTabs(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            AquaItems.SPAWN_EGGS.forEach(registryObject -> event.accept(new ItemStack(registryObject.get())));
        }
    }

    public void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, AquaBlockEntities.TACKLE_BOX.get(), (blockEntity, side) -> blockEntity.handler);
        event.registerItem(Capabilities.ItemHandler.ITEM, (stack, context) -> new AquaFishingRodItem.FishingRodEquipmentHandler(stack), AquaItems.IRON_FISHING_ROD.get(), AquaItems.GOLD_FISHING_ROD.get(), AquaItems.DIAMOND_FISHING_ROD.get());
    }
}