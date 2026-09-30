package com.hadrix.agriculture_craft.tree;

import net.minecraft.core.BlockPos;
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

    public PottedBananaTree(Supplier<FlowerPotBlock> emptyPot, Supplier<? extends Block> pPlant, Properties properties) {
        super(emptyPot, pPlant, properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(POTTED_AGE, 0));
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
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (state.getValue(POTTED_AGE) < 1 && random.nextInt(5) == 0) {
            DoParticle(level, pos);
            level.setBlock(pos, state.setValue(POTTED_AGE, 1), 3);
        }
    }
}