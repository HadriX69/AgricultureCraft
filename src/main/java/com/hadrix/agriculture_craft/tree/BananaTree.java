package com.hadrix.agriculture_craft.tree;

import com.hadrix.agriculture_craft.AllBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BananaTree extends Block {

    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 8);


    public static final VoxelShape SHAPE_AGE_0 = Block.box(6.0, 0.0, 6.0, 10.0, 16.0, 10.0);

    public static final VoxelShape SHAPE_AGE_1 = Block.box(5.0, 0.0, 5.0, 10.0, 20.0, 10.0);

    public static final VoxelShape SHAPE_AGE_2_3 = Block.box(5.0, 0.0, 5.0, 11.0, 16.0, 11.0);

    public static final VoxelShape SHAPE_AGE_4 = Block.box(2.0, 0.0, 2.0, 14.0, 16.0, 14.0);

    public static final VoxelShape SHAPE_AGE_5 = Block.box(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);

    public static final VoxelShape SHAPE_AGE_ADULT = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0);

    public BananaTree(Properties properties) {
        super(properties);

        this.registerDefaultState(this.getStateDefinition().any().setValue(AGE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        int currentAge = state.getValue(AGE);

        return switch (currentAge) {
            case 0 -> SHAPE_AGE_0;
            case 1 -> SHAPE_AGE_1;
            case 2,3 -> SHAPE_AGE_2_3;
            case 4 -> SHAPE_AGE_4;
            case 5 -> SHAPE_AGE_5;
            case 6 -> SHAPE_AGE_ADULT;
            default -> SHAPE_AGE_ADULT;
        };
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int light = level.getMaxLocalRawBrightness(pos.above());

        int currentAge = state.getValue(AGE);

        if (random.nextInt(10) == 0) {
            if (light >= 9 && currentAge < 8) {

                // Calcul de la position tout en haut en fonction de l'âge
                BlockPos highestPos = pos.above(currentAge + 1);

                // On vérifie si c'est bien de l'air (et non null)
                if (level.isEmptyBlock(highestPos)) {
                    if (currentAge == 0) {
                        //level.setBlock(pos, AllBlocks.BANANA_TREE_STATE_2.getDefaultState(), 3);

                        level.setBlock(pos, level.getBlockState(pos).setValue(AGE, 1), 3);
                    }
                    else if (currentAge == 1) {
                        //level.setBlock(pos, AllBlocks.BANANA_TRUNK_STATE_6.getDefaultState(), 3);
                        level.setBlock(pos.above(1), AllBlocks.BANANA_TRUNK_STATE_7.getDefaultState(), 3);

                        level.setBlock(pos, level.getBlockState(pos).setValue(AGE, 2), 3);
                    }
                    else if (currentAge == 2)
                    {
                        //level.setBlock(pos, AllBlocks.BANANA_TRUNK_STATE_6.getDefaultState(), 3);
                        level.setBlock(pos.above(1), AllBlocks.BANANA_TRUNK_STATE_6.getDefaultState(), 3);
                        level.setBlock(pos.above(2), AllBlocks.BANANA_TRUNK_STATE_7.getDefaultState(), 3);

                        level.setBlock(pos, level.getBlockState(pos).setValue(AGE, 3), 3);
                    }
                    else if (currentAge == 3)
                    {

                        //level.setBlock(pos,AllBlocks.BANANA_TRUNK_STATE_3.getDefaultState(),3);

                        level.setBlock(pos.above(1),AllBlocks.BANANA_TRUNK_STATE_4.getDefaultState(),3);

                        level.setBlock(pos.above(2),AllBlocks.BANANA_TRUNK_STATE_4.getDefaultState(),3);

                        level.setBlock(pos.above(3),AllBlocks.BANANA_TRUNK_STATE_5.getDefaultState(),3);

                        level.setBlock(pos, level.getBlockState(pos).setValue(AGE, 4), 3);
                    }

                    else if (currentAge == 4)

                    {

                        //level.setBlock(pos,AllBlocks.BANANA_TRUNK_STATE_2.getDefaultState(),3);

                        level.setBlock(pos.above(1),AllBlocks.BANANA_TRUNK_STATE_3.getDefaultState(),3);

                        level.setBlock(pos.above(2),AllBlocks.BANANA_TRUNK_STATE_3.getDefaultState(),3);

                        level.setBlock(pos.above(3),AllBlocks.BANANA_TRUNK_STATE_3.getDefaultState(),3);

                        level.setBlock(pos.above(4),AllBlocks.BANANA_TRUNK_STATE_4.getDefaultState(),3);

                        level.setBlock(pos, level.getBlockState(pos).setValue(AGE, 5), 3);

                    }

                    else if (currentAge == 5)

                    {

                        //level.setBlock(pos,AllBlocks.BANANA_TRUNK_STATE_1.getDefaultState(),3);

                        level.setBlock(pos.above(1),AllBlocks.BANANA_TRUNK_STATE_2.getDefaultState(),3);

                        level.setBlock(pos.above(2),AllBlocks.BANANA_TRUNK_STATE_2.getDefaultState(),3);

                        level.setBlock(pos.above(3),AllBlocks.BANANA_TRUNK_STATE_2.getDefaultState(),3);

                        level.setBlock(pos.above(4),AllBlocks.BANANA_TRUNK_STATE_2.getDefaultState(),3);

                        level.setBlock(pos.above(5),AllBlocks.BANANA_TRUNK_STATE_3.getDefaultState(),3);

                        level.setBlock(pos, level.getBlockState(pos).setValue(AGE, 6), 3);
                    }
                }
            }
        }
    }
}