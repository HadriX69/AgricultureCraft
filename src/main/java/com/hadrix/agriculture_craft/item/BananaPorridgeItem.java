package com.hadrix.agriculture_craft.item;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import org.jetbrains.annotations.Nullable;

public class BananaPorridgeItem extends Item
{
    public BananaPorridgeItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        return super.useOn(context);
    }
}
