package com.hadrix.agriculture_craft.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import org.jetbrains.annotations.Nullable;

public class BananaPorridgeBowlItem extends Item
{

    public BananaPorridgeBowlItem(Properties properties)
    {
        super(properties);
    }

    @Override
    public @Nullable FoodProperties getFoodProperties(ItemStack stack, @Nullable LivingEntity entity)
    {
        return new FoodProperties.Builder().nutrition(3).saturationModifier(0.5f).usingConvertsTo(Items.BOWL).build();
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.EAT;
    }
}
