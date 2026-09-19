package com.hadrix.agriculture_craft.item;

import com.hadrix.agriculture_craft.ClimateManager;
import com.hadrix.agriculture_craft.Season;
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
                    if(ClimateManager.GetCurrentSeason(level) == Season.SUMMER)
                    {
                        player.displayClientMessage(Component.literal("Season : §e" + ClimateManager.GetSeasonName(level)), true);
                    }
                    else if (ClimateManager.GetCurrentSeason(level) == Season.WINTER)
                    {
                        player.displayClientMessage(Component.literal("Season : §b" + ClimateManager.GetSeasonName(level)), true);
                    }
                    else if (ClimateManager.GetCurrentSeason(level) == Season.SPRING)
                    {
                        player.displayClientMessage(Component.literal("Season : §d" + ClimateManager.GetSeasonName(level)), true);
                    }
                    else if (ClimateManager.GetCurrentSeason(level) == Season.AUTUMN)
                    {
                        player.displayClientMessage(Component.literal("Season : §6" + ClimateManager.GetSeasonName(level)), true);
                    }
                }

                if (level.isClientSide())
                {
                    // Your client logic here
                }
            }
        }
    }
}
