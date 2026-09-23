package com.hadrix.agriculture_craft.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class BananaTreeBlockEntity extends BlockEntity
{
    public boolean isGolden = false;

    public BananaTreeBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);

        if (Math.random() <= 0.001) {
            this.isGolden = true;
        }
    }

    public boolean isGolden() {
        return this.isGolden;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);

        tag.putBoolean("IsGolden", this.isGolden);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);

        this.isGolden = tag.getBoolean("IsGolden");
    }
}
