package com.hadrix.agriculture_craft.block;

import com.hadrix.agriculture_craft.AllBlocks;
import com.hadrix.agriculture_craft.AllItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class Banana extends Block {

    public static final IntegerProperty MATURITY = IntegerProperty.create("maturity", 0, 1);
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public int SearchRadius = 5;

    public static final MapCodec<Banana> CODEC = simpleCodec(Banana::new);

    public Banana(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any()
                .setValue(MATURITY, 0)
                .setValue(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends Block> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(MATURITY, FACING);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (state.getValue(MATURITY) == 0 && random.nextInt(3) == 0) {
            level.setBlock(pos, state.setValue(MATURITY, 1), 3);
        }
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (state.getValue(MATURITY) == 1) {
            if (!level.isClientSide()) {
                ItemStack itemStack = new ItemStack(AllItems.BANANA_ITEM.get(), 1);
                ItemEntity itemEntity = new ItemEntity(
                        level,
                        pos.getX() + 0.5,
                        pos.getY() + 0.5,
                        pos.getZ() + 0.5,
                        itemStack
                );
                itemEntity.setDeltaMovement(0.0, 0.2, 0.0);

                level.removeBlock(pos, false);
                level.addFreshEntity(itemEntity);
            }

            return InteractionResult.sidedSuccess(level.isClientSide());
        }

        return InteractionResult.PASS;
    }
}