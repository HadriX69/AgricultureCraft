package com.hadrix.agriculture_craft.block;

import com.hadrix.agriculture_craft.AllBlocks;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import com.simibubi.create.foundation.fluid.SmartFluidTank;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

import java.util.List;

public class SprinklerBlockEntity extends SmartBlockEntity
{

    public SprinklerBlockEntity(BlockEntityType<?> type,BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }
    public SmartFluidTankBehaviour tankBehaviour;
    public boolean isWorking = false;
    public float angle = 0;
    public float prevAngle = 0;


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

    @Override
    public void tick() {
        SmartFluidTank primaryTank = tankBehaviour.getPrimaryHandler();
        boolean hasWater = primaryTank.getFluidAmount() > 0
                && primaryTank.getFluid().getFluid().isSame(Fluids.WATER);

        if (level.isClientSide) {
            prevAngle = angle;
            if (isWorking) {
                angle += 15.0f;
            }
        }
        else
        {
            if (hasWater)
            {
                primaryTank.drain(1, IFluidHandler.FluidAction.EXECUTE);
                if(level instanceof ServerLevel serverLevel)
                {
                    serverLevel.sendParticles(ParticleTypes.RAIN,
                            tankBehaviour.getPos().getX() + 0.5, tankBehaviour.getPos().getY() - 0.25, tankBehaviour.getPos().getZ() + 0.5,
                            15,5,0,5,3);
                }
                if (!isWorking)
                {
                    isWorking = true;
                    sendData();
                }
            }
            else
            {
            if (isWorking)
            {
                isWorking = false;
                sendData();
            }
        }
        super.tick();
    }
}
    @Override
    protected void write(net.minecraft.nbt.CompoundTag tag, net.minecraft.core.HolderLookup.Provider registries, boolean clientPacket) {
        super.write(tag, registries, clientPacket);
        tag.putBoolean("IsWorking", this.isWorking);
    }

    @Override
    protected void read(net.minecraft.nbt.CompoundTag tag, net.minecraft.core.HolderLookup.Provider registries, boolean clientPacket) {
        super.read(tag, registries, clientPacket);
        this.isWorking = tag.getBoolean("IsWorking");
    }
}
