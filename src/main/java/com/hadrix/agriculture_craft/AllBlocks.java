package com.hadrix.agriculture_craft;

import com.hadrix.agriculture_craft.block.SprinklerBlock;
import com.hadrix.agriculture_craft.block.SprinklerBlockEntity;
import com.hadrix.agriculture_craft.block.SprinklerRenderer;
import com.hadrix.agriculture_craft.block.banana_tree_block.*;
import com.hadrix.agriculture_craft.content.kinetics.AgricultureCraftGeneratorBlock;
import com.hadrix.agriculture_craft.content.kinetics.AgricultureCraftKineticBlock;
import com.simibubi.create.api.behaviour.display.DisplaySource;
import com.simibubi.create.api.stress.BlockStressValues;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.tterrag.registrate.util.entry.BlockEntry;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.client.model.generators.ModelFile;

/**
 * Block registration.
 */
public class AllBlocks {

    /**
     * A kinetic block that draws stress from the network. Its blockstate is generated as
     * an axis-aligned column and it reuses vanilla textures; swap them for your own.
     */
    public static final BlockEntry<AgricultureCraftKineticBlock> EXAMPLE_KINETIC_BLOCK = AgricultureCraft.REGISTRATE
            .block("agriculture_craft_kinetic_block", AgricultureCraftKineticBlock::new)
            .initialProperties(() -> Blocks.ANDESITE)
            .properties(p -> p.noOcclusion())
            .blockstate((ctx, prov) -> {
                ModelFile model = encasedShaftModel(prov, ctx.getName(),
                        ResourceLocation.withDefaultNamespace("block/stripped_spruce_log"),
                        ResourceLocation.withDefaultNamespace("block/spruce_planks"));
                BlockStateGen.axisBlock(ctx, prov, state -> model);
            })
            // here is where you can adjust how much stress your block scales with (for example rpm x 128 for this block is the amount of su)
            .onRegister(b -> BlockStressValues.IMPACTS.register(b, () -> 128))
            .item()
            .build()
            .register();

    /**
     * A kinetic generator, the counterpart to EXAMPLE_KINETIC_BLOCK; its capacity is
     * registered in AgricultureCraft. The transform call attaches the EXAMPLE_SOURCE display
     * source to this block.
     */
    public static final BlockEntry<AgricultureCraftGeneratorBlock> EXAMPLE_GENERATOR_BLOCK = AgricultureCraft.REGISTRATE
            .block("agriculture_craft_generator_block", AgricultureCraftGeneratorBlock::new)
            .initialProperties(() -> Blocks.POLISHED_ANDESITE)
            .properties(p -> p.noOcclusion())
            .blockstate((ctx, prov) -> {
                ModelFile model = encasedShaftModel(prov, ctx.getName(),
                        ResourceLocation.withDefaultNamespace("block/polished_andesite"),
                        ResourceLocation.withDefaultNamespace("block/chiseled_polished_blackstone"));
                BlockStateGen.axisBlock(ctx, prov, state -> model);
            })
            .transform(DisplaySource.displaySource(AllDisplaySources.EXAMPLE_SOURCE))
            .onRegister(b -> BlockStressValues.CAPACITIES.register(b, () -> 128))
            .item()
            .build()
            .register();

//    public static final BlockEntry<SprinklerBlock> SPRINKLER_BLOCK = AgricultureCraft.REGISTRATE
//            .block("sprinkler_block", SprinklerBlock::new)
//            .properties(p -> p.noOcclusion())
//            .blockstate((c, p) -> p.simpleBlock(c.get(), p.models().getExistingFile(p.modLoc("block/sprinkler_base"))))
//            .item()
//            .model((c, p) -> p.withExistingParent(c.getName(), p.modLoc("item/sprinkler_block")))
//            .build()
//            .register();

    public static final BlockEntry<SprinklerBlock> SPRINKLER_BLOCK = AgricultureCraft.REGISTRATE
            .block("sprinkler", SprinklerBlock::new)
            .blockstate((c, p) -> p.simpleBlock(c.get(), p.models().getExistingFile(p.modLoc("block/sprinkler_block"))))
            .properties(p -> p.noOcclusion())
            .item()
            .build()
            .register();



    public static final BlockEntityEntry<SprinklerBlockEntity> SPRINKLER_BLOCK_ENTITY = AgricultureCraft.REGISTRATE
            .blockEntity("sprinkler_entity" , SprinklerBlockEntity::new)
            .validBlocks(AllBlocks.SPRINKLER_BLOCK)
            .renderer(() -> SprinklerRenderer::new)
            .register();

    public static final BlockEntry<banana_tree_state_1> BANANA_TREE_STATE_1 = AgricultureCraft.REGISTRATE
            .block("banana_tree_state_1", banana_tree_state_1::new)
            .properties(p -> p.noCollission().noOcclusion())
            .blockstate((c, p) -> p.simpleBlock(c.get(), p.models().getExistingFile(p.modLoc("block/banana_tree_state_1_1"))))
            .item()
            .model((c, p) -> p.withExistingParent(c.getName(), p.modLoc("block/banana_tree_state_1_1")))
            .build()
            .register();

    public static final BlockEntry<banana_tree_state_2> BANANA_TREE_STATE_2 = AgricultureCraft.REGISTRATE
            .block("banana_tree_state_2", banana_tree_state_2::new)
            .properties(p -> p.noCollission().noOcclusion())
            .blockstate((c, p) -> p.simpleBlock(c.get(), p.models().getExistingFile(p.modLoc("block/banana_tree_state_2_1"))))
            .item()
            .model((c, p) -> p.withExistingParent(c.getName(), p.modLoc("block/banana_tree_state_2_1")))
            .build()
            .register();

    public static final BlockEntry<Block> BANANA_TRUNK_STATE_1 = AgricultureCraft.REGISTRATE
            .block("banana_trunk_state_1", Block::new)
            .properties(p -> p.noOcclusion())
            .blockstate((c, p) -> p.simpleBlock(c.get(), p.models().getExistingFile(p.modLoc("block/banana_tree_trunck_block_1"))))
            .item()
            .model((c, p) -> p.withExistingParent(c.getName(), p.modLoc("block/banana_tree_trunck_block_1")))
            .build()
            .register();

    public static final BlockEntry<banana_trunk_state_2> BANANA_TRUNK_STATE_2 = AgricultureCraft.REGISTRATE
            .block("banana_trunk_state_2", banana_trunk_state_2::new)
            .properties(p -> p.noOcclusion())
            .blockstate((c, p) -> p.simpleBlock(c.get(), p.models().getExistingFile(p.modLoc("block/banana_tree_trunck_block_2"))))
            .item()
            .model((c, p) -> p.withExistingParent(c.getName(), p.modLoc("block/banana_tree_trunck_block_2")))
            .build()
            .register();

    public static final BlockEntry<banana_trunk_state_3> BANANA_TRUNK_STATE_3 = AgricultureCraft.REGISTRATE
            .block("banana_trunk_state_3", banana_trunk_state_3::new)
            .properties(p -> p.noOcclusion())
            .blockstate((c, p) -> p.simpleBlock(c.get(), p.models().getExistingFile(p.modLoc("block/banana_tree_trunck_block_3"))))
            .item()
            .model((c, p) -> p.withExistingParent(c.getName(), p.modLoc("block/banana_tree_trunck_block_3")))
            .build()
            .register();

    public static final BlockEntry<banana_trunk_state_4> BANANA_TRUNK_STATE_4 = AgricultureCraft.REGISTRATE
            .block("banana_trunk_state_4", banana_trunk_state_4::new)
            .properties(p -> p.noOcclusion())
            .blockstate((c, p) -> p.simpleBlock(c.get(), p.models().getExistingFile(p.modLoc("block/banana_tree_trunck_block_4"))))
            .item()
            .model((c, p) -> p.withExistingParent(c.getName(), p.modLoc("block/banana_tree_trunck_block_4")))
            .build()
            .register();

    public static final BlockEntry<banana_trunk_state_5> BANANA_TRUNK_STATE_5 = AgricultureCraft.REGISTRATE
            .block("banana_trunk_state_5", banana_trunk_state_5::new)
            .properties(p -> p.noOcclusion())
            .blockstate((c, p) -> p.simpleBlock(c.get(), p.models().getExistingFile(p.modLoc("block/banana_tree_trunck_block_5"))))
            .item()
            .model((c, p) -> p.withExistingParent(c.getName(), p.modLoc("block/banana_tree_trunck_block_5")))
            .build()
            .register();

    public static final BlockEntry<banana_trunk_state_6> BANANA_TRUNK_STATE_6 = AgricultureCraft.REGISTRATE
            .block("banana_trunk_state_6", banana_trunk_state_6::new)
            .properties(p -> p.noOcclusion())
            .blockstate((c, p) -> p.simpleBlock(c.get(), p.models().getExistingFile(p.modLoc("block/banana_tree_trunck_block_6"))))
            .item()
            .model((c, p) -> p.withExistingParent(c.getName(), p.modLoc("block/banana_tree_trunck_block_6")))
            .build()
            .register();

    public static final BlockEntry<banana_trunk_state_7> BANANA_TRUNK_STATE_7 = AgricultureCraft.REGISTRATE
            .block("banana_trunk_state_7", banana_trunk_state_7::new)
            .properties(p -> p.noOcclusion())
            .blockstate((c, p) -> p.simpleBlock(c.get(), p.models().getExistingFile(p.modLoc("block/banana_tree_trunck_block_7"))))
            .item()
            .model((c, p) -> p.withExistingParent(c.getName(), p.modLoc("block/banana_tree_trunck_block_7")))
            .build()
            .register();

    /**
     * Builds an "encased shaft" style model: a casing box inset by 2px on the rotation
     * axis so the shaft rendered by AgricultureCraftShaftRenderer visibly pokes out of both ends.
     * The box is authored along the Y axis (caps on top and bottom); BlockStateGen.axisBlock
     * rotates it to match the block's AXIS, using the same rotations as Create's shaft, so
     * the static casing and the spinning shaft always line up. Side faces use the casing
     * texture, the two end caps use the cap texture.
     */
    private static ModelFile encasedShaftModel(RegistrateBlockstateProvider prov, String name,
                                               ResourceLocation casing, ResourceLocation cap) {
        return prov.models()
                .withExistingParent(name, ResourceLocation.withDefaultNamespace("block/block"))
                .texture("particle", casing)
                .texture("casing", casing)
                .texture("cap", cap)
                .element()
                    // Inset 3px on the top and bottom (the Y axis) so the shaft pokes out.
                    .from(0, 3, 0).to(16, 13, 16)
                    .face(Direction.NORTH).texture("#casing").end()
                    .face(Direction.SOUTH).texture("#casing").end()
                    .face(Direction.WEST).texture("#casing").end()
                    .face(Direction.EAST).texture("#casing").end()
                    .face(Direction.UP).texture("#cap").end()
                    .face(Direction.DOWN).texture("#cap").end()
                    .end();
    }

    public static void register() {
        // Force class loading to trigger Registrate calls
    }
}
