package com.hadrix.agriculture_craft.block;

import com.hadrix.agriculture_craft.AllBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class SprinklerBlock extends BaseEntityBlock
{
    public static final MapCodec<SprinklerBlock> CODEC = SprinklerBlock.simpleCodec(SprinklerBlock::new);

    public SprinklerBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SprinklerBlockEntity(AllBlocks.SPRINKLER_BLOCK_ENTITY.get() ,pos, state);
    }

    @Override
    public net.minecraft.world.level.block.RenderShape getRenderShape(BlockState state) {
        return net.minecraft.world.level.block.RenderShape.MODEL;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        // On vérifie que le type correspond bien à notre SprinklerBlockEntity
        return createTickerHelper(blockEntityType, AllBlocks.SPRINKLER_BLOCK_ENTITY.get(),
                // Si oui, on appelle la méthode tick() de notre BlockEntity
                (lvl, pos, blockState, blockEntity) -> blockEntity.tick()
        );
    }
}
