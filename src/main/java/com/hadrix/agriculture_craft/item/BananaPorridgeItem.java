package com.hadrix.agriculture_craft.item;

import com.hadrix.agriculture_craft.AllItems;
import com.hadrix.agriculture_craft.ILubricatable;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;

public class BananaPorridgeItem extends Item {
    public BananaPorridgeItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        ItemStack itemStack = context.getItemInHand();

        if (itemStack.is(AllItems.BANANA_PORRIDGE.get())) {
            BlockEntity blockEntity = context.getLevel().getBlockEntity(context.getClickedPos());

            if (blockEntity instanceof KineticBlockEntity kineticBlock && blockEntity instanceof ILubricatable lubricatable) {
                Player player = context.getPlayer();

                if (!level.isClientSide()) {
                    lubricatable.agriculture_craft$setLubricated(3600);

                    if (kineticBlock.getOrCreateNetwork() != null) {
                        kineticBlock.getOrCreateNetwork().updateCapacity();
                        kineticBlock.getOrCreateNetwork().updateStress();
                    }

                    kineticBlock.sendData();
                    kineticBlock.setChanged();

                    if (!player.isCreative()) {
                        itemStack.shrink(1);
                    }

                    level.playSound(null, context.getClickedPos(), SoundEvents.SLIME_BLOCK_PLACE, SoundSource.BLOCKS, 1.0F, 1.2F);
                } else {
                    // Particules côté client lors du clic
                    for (int i = 0; i < 5; i++) {
                        level.addParticle(ParticleTypes.ITEM_SLIME,
                                context.getClickedPos().getX() + 0.5, context.getClickedPos().getY() + 0.5, context.getClickedPos().getZ() + 0.5,
                                0, 0, 0);
                    }
                }
            }
            else
            {
                return InteractionResult.FAIL;
            }
        }
        return InteractionResult.SUCCESS;
    }
}
