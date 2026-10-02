package com.hadrix.agriculture_craft;

import com.hadrix.agriculture_craft.block.SprinklerBlockEntity;
import com.hadrix.agriculture_craft.block.SprinklerRenderer;
import com.hadrix.agriculture_craft.content.kinetics.AgricultureCraftGeneratorBlockEntity;
import com.hadrix.agriculture_craft.content.kinetics.AgricultureCraftKineticBlockEntity;
import com.hadrix.agriculture_craft.content.kinetics.AgricultureCraftShaftRenderer;
import com.simibubi.create.content.kinetics.base.ShaftVisual;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

/**
 * Block entity type registration.
 */
public class AllBlockEntityTypes {

    /**
     * Block entity for EXAMPLE_KINETIC_BLOCK, rendered with AgricultureCraftShaftRenderer so a
     * shaft visibly spins through the casing.
     */
    public static final BlockEntityEntry<AgricultureCraftKineticBlockEntity> EXAMPLE_KINETIC = AgricultureCraft.REGISTRATE
            .blockEntity("agriculture_craft_kinetic", AgricultureCraftKineticBlockEntity::new)
            // visual for flywheel renderer
            .visual(() -> ShaftVisual::new)
            .validBlock(AllBlocks.EXAMPLE_KINETIC_BLOCK)
            // fallback renderer if flywheel is not available
            .renderer(() -> AgricultureCraftShaftRenderer::new)
            .register();

    public static final BlockEntityEntry<SprinklerBlockEntity> SPRINKLER_KINETIC = AgricultureCraft.REGISTRATE
            .blockEntity("agriculture_craft_sprinkler_kinetic", SprinklerBlockEntity::new)
            // visual for flywheel renderer
            .visual(() -> ShaftVisual::new)
            .validBlock(AllBlocks.SPRINKLER_BLOCK)
            // fallback renderer if flywheel is not available
            .renderer(() -> SprinklerRenderer::new)
            .register();

    /**
     * Block entity for EXAMPLE_GENERATOR_BLOCK, also rendered with AgricultureCraftShaftRenderer.
     */
    public static final BlockEntityEntry<AgricultureCraftGeneratorBlockEntity> EXAMPLE_GENERATOR = AgricultureCraft.REGISTRATE
            .blockEntity("agriculture_craft_generator", AgricultureCraftGeneratorBlockEntity::new)
            .visual(() -> ShaftVisual::new)
            .validBlock(AllBlocks.EXAMPLE_GENERATOR_BLOCK)
            .renderer(() -> AgricultureCraftShaftRenderer::new)
            .register();

    public static void register() {
        // Force class loading to trigger Registrate calls
    }
}
