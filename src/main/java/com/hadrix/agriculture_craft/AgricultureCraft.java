package com.hadrix.agriculture_craft;

import java.util.concurrent.CompletableFuture;

import com.hadrix.agriculture_craft.content.ponder.AgricultureCraftPonderPlugin;
import com.hadrix.agriculture_craft.datagen.AgricultureCraftCompactingRecipeGen;
import com.hadrix.agriculture_craft.datagen.AgricultureCraftCrushingRecipeGen;
import com.hadrix.agriculture_craft.datagen.AgricultureCraftCuttingRecipeGen;
import com.hadrix.agriculture_craft.datagen.AgricultureCraftDeployingRecipeGen;
import com.hadrix.agriculture_craft.datagen.AgricultureCraftEmptyingRecipeGen;
import com.hadrix.agriculture_craft.datagen.AgricultureCraftFillingRecipeGen;
import com.hadrix.agriculture_craft.datagen.AgricultureCraftLangMerger;
import com.hadrix.agriculture_craft.datagen.AgricultureCraftHauntingRecipeGen;
import com.hadrix.agriculture_craft.datagen.AgricultureCraftMillingRecipeGen;
import com.hadrix.agriculture_craft.datagen.AgricultureCraftMixingRecipeGen;
import com.hadrix.agriculture_craft.datagen.AgricultureCraftPressingRecipeGen;
import com.hadrix.agriculture_craft.datagen.AgricultureCraftSequencedAssemblyGen;
import com.hadrix.agriculture_craft.datagen.AgricultureCraftWashingRecipeGen;
import com.simibubi.create.content.kinetics.base.ShaftVisual;
import com.tterrag.registrate.providers.ProviderType;
import net.createmod.ponder.foundation.PonderIndex;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipModifier;
import dev.engine_room.flywheel.lib.visualization.SimpleBlockEntityVisualizer;
import net.createmod.catnip.lang.FontHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(AgricultureCraft.ID)
public class AgricultureCraft {
    public static final String ID = "agriculture_craft";
    public static final Logger LOGGER = LogManager.getLogger(ID);

    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(ID)
            .setTooltipModifierFactory(item ->
                    new ItemDescription.Modifier(item, FontHelper.Palette.STANDARD_CREATE)
                            .andThen(TooltipModifier.mapNull(KineticStats.create(item)))
            );

    public AgricultureCraft(IEventBus modBus) {
        REGISTRATE.registerEventListeners(modBus);

        AllCreativeModeTabs.register();
        REGISTRATE.setCreativeTab(AllCreativeModeTabs.MAIN_TAB);
        registerLangPartials();
        registerPonderLang();
        AllItems.register();
        AllDisplaySources.register();
        AllBlocks.register();
        AllBlockEntityTypes.register();

        modBus.addListener(this::onCommonSetup);
        modBus.addListener(this::onClientSetup);
        modBus.addListener(this::onGatherData);
        modBus.addListener(this::onRegisterCapabilities);

        NeoForge.EVENT_BUS.addListener(AgricultureCraft::OnTick);
    }

    public static ResourceLocation asResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(ID, path);
    }

    private void onCommonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("Common setup...");
    }

    private void onClientSetup(FMLClientSetupEvent event) {
        LOGGER.info("Client setup...");
        event.enqueueWork(() -> {
            // Ponder is client-only
            PonderIndex.addPlugin(new AgricultureCraftPonderPlugin());
            ModPartialModels.init();
        });
    }

    /**
     * Feeds the hand-authored language partials (assets/agriculture_craft/lang/default/*.json)
     * into Registrate's lang provider so runData merges them with the generated block and
     * item names into a single en_us.json. Keeps English copy out of Java, mirroring how
     * Create authors its own translations.
     */
    private void registerLangPartials() {
        REGISTRATE.addDataGenerator(ProviderType.LANG, provider ->
                AgricultureCraftLangMerger.mergeInto(provider::add));
    }

    /**
     * Feeds the Ponder scenes' text (titles and captions) into Registrate's lang provider
     * so runData writes it into the same en_us.json as the block and item names. The
     * registered callback only runs during data generation, so it is safe to touch the
     * client-only PonderIndex from here.
     */
    private void registerPonderLang() {
        REGISTRATE.addDataGenerator(ProviderType.LANG, provider -> {
            PonderIndex.addPlugin(new AgricultureCraftPonderPlugin());
            PonderIndex.getLangAccess().provideLang(ID, provider::add);
        });
    }

    /**
     * Registers the data generators. Running gradlew runData writes their output into
     * src/generated/resources.
     */
    private void onGatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> registries = event.getLookupProvider();

        generator.addProvider(event.includeServer(), new AgricultureCraftSequencedAssemblyGen(output, registries));
        generator.addProvider(event.includeServer(), new AgricultureCraftWashingRecipeGen(output, registries));
        generator.addProvider(event.includeServer(), new AgricultureCraftHauntingRecipeGen(output, registries));
        generator.addProvider(event.includeServer(), new AgricultureCraftCrushingRecipeGen(output, registries));
        generator.addProvider(event.includeServer(), new AgricultureCraftMillingRecipeGen(output, registries));
        generator.addProvider(event.includeServer(), new AgricultureCraftPressingRecipeGen(output, registries));
        generator.addProvider(event.includeServer(), new AgricultureCraftCuttingRecipeGen(output, registries));
        generator.addProvider(event.includeServer(), new AgricultureCraftMixingRecipeGen(output, registries));
        generator.addProvider(event.includeServer(), new AgricultureCraftCompactingRecipeGen(output, registries));
        generator.addProvider(event.includeServer(), new AgricultureCraftFillingRecipeGen(output, registries));
        generator.addProvider(event.includeServer(), new AgricultureCraftEmptyingRecipeGen(output, registries));
        generator.addProvider(event.includeServer(), new AgricultureCraftDeployingRecipeGen(output, registries));
    }

    private void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.FluidHandler.BLOCK,
                AllBlocks.SPRINKLER_BLOCK_ENTITY.get(),
                (blockEntity, side) -> blockEntity.tankBehaviour.getPrimaryHandler()
        );
    }

    private static void OnTick(final PlayerTickEvent.Pre event)
    {
        if (event.getEntity().level().isClientSide()) {
            ClimateManager.OnTick(Minecraft.getInstance());
        }

    }
}
