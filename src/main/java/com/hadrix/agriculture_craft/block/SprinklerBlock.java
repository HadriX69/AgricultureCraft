package com.hadrix.agriculture_craft.block;

import com.google.common.base.Ticker;
import com.hadrix.agriculture_craft.AllBlockEntityTypes;
import com.hadrix.agriculture_craft.AllBlocks;
import com.mojang.serialization.MapCodec;
import com.simibubi.create.content.kinetics.base.RotatedPillarKineticBlock;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class SprinklerBlock extends RotatedPillarKineticBlock implements IBE<SprinklerBlockEntity>
{
    public static final MapCodec<SprinklerBlock> CODEC = SprinklerBlock.simpleCodec(SprinklerBlock::new);

    public SprinklerBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    protected MapCodec<? extends Block> codec() {
        return CODEC;
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return face == Direction.UP;
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return Direction.Axis.Y;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SprinklerBlockEntity(AllBlocks.SPRINKLER_BLOCK_ENTITY.get() ,pos, state);
    }

    @Override
    public net.minecraft.world.level.block.RenderShape getRenderShape(BlockState state) {
        return net.minecraft.world.level.block.RenderShape.MODEL;
    }

    @Override
    public Class<SprinklerBlockEntity> getBlockEntityClass() {
        return SprinklerBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends SprinklerBlockEntity> getBlockEntityType() {
        return AllBlocks.SPRINKLER_BLOCK_ENTITY.get();
    }

    //    @Nullable
//    @Override
//    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
//
//        return createTickerHelper(blockEntityType, AllBlocks.SPRINKLER_BLOCK_ENTITY.get(),
//                (lvl, pos, blockState, blockEntity) -> blockEntity.tick()
//        );
//    }
}
