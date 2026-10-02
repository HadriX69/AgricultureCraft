package com.hadrix.agriculture_craft.item;

import com.simibubi.create.AllBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class SprinklerItem extends BlockItem {

    public SprinklerItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public BlockPlaceContext updatePlacementContext(BlockPlaceContext context) {
        Level level = context.getLevel();

        if (context.getPlayer() != null && context.getPlayer().isSecondaryUseActive()) {

            BlockPos clickedBlockPos = context.getClickedPos().relative(context.getClickedFace().getOpposite());
            BlockState clickedState = level.getBlockState(clickedBlockPos);

            if (clickedState.is(AllBlocks.DEPOT.get())) {
                BlockPos targetPos = context.getClickedPos();
                BlockPos offsetPos = targetPos.above();

                if (level.getBlockState(offsetPos).canBeReplaced(context)) {
                    level.getBlockEntity(context.getClickedPos());
                    return BlockPlaceContext.at(context, offsetPos, context.getClickedFace());
                }
                else
                {
                    return null;
                }
            }
        }

        return super.updatePlacementContext(context);
    }
}