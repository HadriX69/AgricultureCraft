package com.hadrix.agriculture_craft.block;

import com.hadrix.agriculture_craft.AllBlocks;
import com.hadrix.agriculture_craft.AllItems;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import com.simibubi.create.foundation.fluid.SmartFluidTank;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Position;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

import java.util.List;

public class SprinklerBlockEntity extends KineticBlockEntity
{

    public SprinklerBlockEntity(BlockEntityType<?> type,BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }
    public SmartFluidTankBehaviour tankBehaviour;
    public boolean isWorking = false;



    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours)
    {
        tankBehaviour = new SmartFluidTankBehaviour(
                SmartFluidTankBehaviour.TYPE,
                this,
                1,      // Nbr of reservoir
                1,   // (1000 mB = 1 water bucket)
                true    // NBT save
        );

        behaviours.add(tankBehaviour);
    }

    public int getWateringRadius() {
        float speed = Math.abs(getSpeed());

        if (speed == 0) {
            return 1; // 1x1
        }

        // Calcule un rayon proportionnel de 1 à 20 blocs (256 RPM = rayon max de 20)
        int calculatedRadius = 1 + (int) ((speed / 256.0f) * 19);
        return Math.min(20, calculatedRadius);
    }

    @Override
    public void tick() {
        super.tick();

        if (level == null) return;

        SmartFluidTank primaryTank = tankBehaviour.getPrimaryHandler();
        boolean hasWater = primaryTank.getFluidAmount() > 0
                && primaryTank.getFluid().getFluid().isSame(Fluids.WATER);

        int radius = getWateringRadius();

        if (level.isClientSide) {
            if (isWorking) {

                RandomSource random = level.getRandom();
                double px = this.getBlockPos().getX() + 0.5 + (random.nextDouble() - 0.5) * radius;
                double py = this.getBlockPos().getY() - 0.5;
                double pz = this.getBlockPos().getZ() + 0.5 + (random.nextDouble() - 0.5) * radius;

                level.addParticle(ParticleTypes.RAIN, px, py, pz, 0, 3, 0);
            }
        }
        else
        {
            if (hasWater) {
                primaryTank.drain(1, IFluidHandler.FluidAction.EXECUTE);

                if (!isWorking) {
                    isWorking = true;
                    sendData();
                }

                ServerLevel serverLevel = (ServerLevel) level;
                boolean timer = level.getGameTime() % 80 == 0;

                if (timer) {
                    for (int x = -radius; x <= radius; x++) {
                        for (int z = -radius; z <= radius; z++) {
                            for (int y = -1; y >= -15; y--) {
                                BlockPos currentPos = this.getBlockPos().offset(x, y, z);
                                BlockState state = level.getBlockState(currentPos);

                                if (state.is(Blocks.FARMLAND)) {
                                    if (state.getValue(BlockStateProperties.MOISTURE) < 7) {
                                        BlockState newState = state.setValue(BlockStateProperties.MOISTURE, 7);
                                        level.setBlock(currentPos, newState, 3);
                                    }
                                    break;
                                }
                                else if (state.is(Blocks.MUD)) {
                                    if(level.getRandom().nextInt(5) == 0)
                                    {
                                    int random = level.getRandom().nextInt(50);
                                    level.removeBlock(currentPos, false);
                                    level.playSound(null, currentPos, SoundType.MUD.getBreakSound(), SoundSource.BLOCKS);

                                    serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, state),
                                            x,
                                            y,
                                            z,
                                            5,
                                            0.5,  // X
                                            0.5,  // Y
                                            0.5,  // Z
                                            0.0   // Speed
                                    );

                                        if(random == 0)
                                        {
                                            ItemStack itemStack = new ItemStack(Items.BONE, 1);
                                            ItemEntity itemEntity = new ItemEntity(
                                                    level,
                                                    currentPos.getX() + 0.5,
                                                    currentPos.getY() + 1,
                                                    currentPos.getZ() + 0.5,
                                                    itemStack
                                            );
                                            itemEntity.setDeltaMovement(0.0, 0.2, 0.0);

                                            level.addFreshEntity(itemEntity);
                                        }
                                        else if (random == 1)
                                        {
                                            ItemStack itemStack = new ItemStack(Items.WHEAT_SEEDS, 1);
                                            ItemEntity itemEntity = new ItemEntity(
                                                    level,
                                                    currentPos.getX() + 0.5,
                                                    currentPos.getY() + 1,
                                                    currentPos.getZ() + 0.5,
                                                    itemStack
                                            );
                                            itemEntity.setDeltaMovement(0.0, 0.2, 0.0);

                                            level.addFreshEntity(itemEntity);
                                        }
                                        else if (random == 2)
                                        {
                                            ItemStack itemStack = new ItemStack(Items.CARROT, 1);
                                            ItemEntity itemEntity = new ItemEntity(
                                                    level,
                                                    currentPos.getX() + 0.5,
                                                    currentPos.getY() + 1,
                                                    currentPos.getZ() + 0.5,
                                                    itemStack
                                            );
                                            itemEntity.setDeltaMovement(0.0, 0.2, 0.0);

                                            level.addFreshEntity(itemEntity);
                                        }
                                        else if (random == 3)
                                        {
                                            ItemStack itemStack = new ItemStack(AllBlocks.BANANA_TREE.get(), 1);
                                            ItemEntity itemEntity = new ItemEntity(
                                                    level,
                                                    currentPos.getX() + 0.5,
                                                    currentPos.getY() + 1,
                                                    currentPos.getZ() + 0.5,
                                                    itemStack
                                            );
                                            itemEntity.setDeltaMovement(0.0, 0.2, 0.0);

                                            level.addFreshEntity(itemEntity);
//                                        }
                                        }
                                        else
                                        {
                                            ItemStack itemStack = new ItemStack(Items.AIR, 1);
                                            ItemEntity itemEntity = new ItemEntity(
                                                    level,
                                                    currentPos.getX() + 0.5,
                                                    currentPos.getY() + 1,
                                                    currentPos.getZ() + 0.5,
                                                    itemStack
                                            );
                                            itemEntity.setDeltaMovement(0.0, 0.2, 0.0);

                                            level.addFreshEntity(itemEntity);
                                        }
                                    }
                                }

                                if (!state.getCollisionShape(level, currentPos).isEmpty()) {
                                    break;
                                }

                            }
                        }
                    }
                }
            }
            else
            {
                if (isWorking) {
                    isWorking = false;
                    sendData();
                }
            }
        }
    }

    @Override
    protected void write(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.write(tag, registries, clientPacket);
        tag.putBoolean("IsWorking", this.isWorking);
    }

    @Override
    protected void read(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(tag, registries, clientPacket);
        this.isWorking = tag.getBoolean("IsWorking");
    }
}
