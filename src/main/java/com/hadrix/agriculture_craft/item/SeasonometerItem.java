package com.hadrix.agriculture_craft.item;

import com.hadrix.agriculture_craft.ClimateManager;
import com.hadrix.agriculture_craft.Season;
import net.minecraft.ChatFormatting;
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
                    Enum<Season> ActualSeason = ClimateManager.GetCurrentSeason(level);

                    if(ActualSeason == Season.SUMMER)
                    {
                        player.displayClientMessage(
                                Component.translatable("item.agriculture_craft.seasonometer.season")
                                        .append(Component.literal(" : " + ClimateManager.GetSeasonName(level)))
                                        .withStyle(ChatFormatting.YELLOW),
                                true
                        );
                    }
                    else if (ActualSeason == Season.WINTER)
                    {
                        player.displayClientMessage(
                                Component.translatable("item.agriculture_craft.seasonometer.season")
                                        .append(Component.literal(" : " + ClimateManager.GetSeasonName(level)))
                                        .withStyle(ChatFormatting.AQUA),
                                true
                        );
                    }
                    else if (ActualSeason == Season.SPRING)
                    {
                        player.displayClientMessage(
                                Component.translatable("item.agriculture_craft.seasonometer.season")
                                        .append(Component.literal(" : " + ClimateManager.GetSeasonName(level)))
                                        .withStyle(ChatFormatting.LIGHT_PURPLE),
                                true
                        );
                    }
                    else if (ActualSeason == Season.AUTUMN)
                    {
                        player.displayClientMessage(
                                Component.translatable("item.agriculture_craft.seasonometer.season")
                                        .append(Component.literal(" : " + ClimateManager.GetSeasonName(level)))
                                        .withStyle(ChatFormatting.GOLD),
                                true
                        );
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
