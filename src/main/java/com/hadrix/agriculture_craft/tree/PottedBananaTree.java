package com.hadrix.agriculture_craft.tree;

import com.hadrix.agriculture_craft.AllBlocks;
import com.hadrix.agriculture_craft.block.Banana;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import java.util.function.Supplier;

public class PottedBananaTree extends FlowerPotBlock {

    public static final IntegerProperty POTTED_AGE = IntegerProperty.create("potted_age", 0, 1);
    //public static final IntegerProperty BANANA_NBR = IntegerProperty.create("banana_nbr", 0, 1);
    Direction facing = Direction.NORTH;


    public PottedBananaTree(Supplier<FlowerPotBlock> emptyPot, Supplier<? extends Block> pPlant, Properties properties) {
        super(emptyPot, pPlant, properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(POTTED_AGE, 0));
        //this.registerDefaultState(this.stateDefinition.any().setValue(BANANA_NBR, 0));
    }

    public void DoParticle(ServerLevel level, BlockPos pos)
    {
        level.sendParticles(ParticleTypes.HAPPY_VILLAGER,
                pos.getX() + 0.5,
                pos.getY() + 0.5,
                pos.getZ() + 0.5,
                5,
                0.5,  // X
                0.5,  // Y
                0.5,  // Z
                0.0   // Speed
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(POTTED_AGE);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random)
    {
        int currentAge = state.getValue(POTTED_AGE);

        if (currentAge < 1 && random.nextInt(5) == 0) {
            DoParticle(level, pos);
            level.setBlock(pos, state.setValue(POTTED_AGE, 1), 3);
        }
//        if (currentAge == 1 && random.nextInt(10) == 0)
//        {
//            int banana_nbr = state.getValue(POTTED_AGE);
//
//            for(int x = -1; x <= 1; x++)
//            {
//                for(int z = -1; z <= 1; z++)
//                {
//                    if (x == 0 && z == 0) {
//                        continue;
//                    }
//                    else if (x == 1 && z == 1 || x == -1 && z == 1 || x == -1 && z == -1 || x == 1 && z == -1)
//                    {
//                        continue;
//                    }
//
//
//                    BlockPos highestPos = pos.above(2);
//                    BlockPos bananaPos = new BlockPos(highestPos.getX() + x, highestPos.getY(), highestPos.getZ() + z);
//
//                    if(level.getBlockState(bananaPos).is(AllBlocks.BANANA))
//                    {
//                        state.setValue(POTTED_AGE,1);
//                    }
//
//                    if(banana_nbr == 0)
//                    {
//                        if (x == -1) facing = Direction.EAST;
//                        else if (x == 1) facing = Direction.WEST;
//                        else if (z == -1) facing = Direction.SOUTH;
//                        else if (z == 1) facing = Direction.NORTH;
//
//                        level.setBlock(bananaPos, AllBlocks.BANANA.getDefaultState().setValue(Banana.FACING, facing), 3);
//                    }
//                }
//            }
//        }
    }
}