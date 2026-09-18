package com.hadrix.agriculture_craft.item;

import com.hadrix.agriculture_craft.ClimateManager;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SeasonometerItem extends Item
{

    public SeasonometerItem(Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (entity instanceof Player player)
        {
            boolean isHeld = player.getMainHandItem() == stack || player.getOffhandItem() == stack;
            if (isHeld)
            {
                if (!level.isClientSide())
                {
                    player.displayClientMessage(Component.literal("§e Season : " + ClimateManager.GetSeasonName(level)), true);
                }

                if (level.isClientSide())
                {
                    // Your client logic here
                }
            }
        }
    }
}
