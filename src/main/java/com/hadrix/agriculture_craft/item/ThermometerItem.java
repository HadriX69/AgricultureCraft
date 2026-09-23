package com.hadrix.agriculture_craft.item;

import com.hadrix.agriculture_craft.ClimateManager;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ThermometerItem extends Item {

    public ThermometerItem(Properties properties) {
        super(properties);
    }

    MutableComponent Temperature_Translation = Component.translatable("item.agriculture_craft.thermometer.temperature");

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (entity instanceof Player player) {

            // 2. Check if the item is in the active hands (Main hand or Offhand)
            boolean isHeld = player.getMainHandItem() == stack || player.getOffhandItem() == stack;

            if (isHeld) {
                // Run server-only logic (like potion effects or logic)
                if (!level.isClientSide())
                {
                    float temp = ClimateManager.GetTemperature(entity.getCommandSenderWorld(), entity.getOnPos());

                    if (temp >= 30.0f) {
                        player.displayClientMessage(
                                Component.translatable("item.agriculture_craft.thermometer.temperature")
                                        .append(Component.literal(" : " + temp))
                                        .withStyle(ChatFormatting.DARK_RED),
                                true
                        );
                    }
                    else if (temp >= 20.0f && temp <= 30.0f) {
                        player.displayClientMessage(
                                Component.translatable("item.agriculture_craft.thermometer.temperature")
                                        .append(Component.literal(" : " + temp))
                                        .withStyle(ChatFormatting.RED),
                                true
                        );
                    }
                    else if (temp <= 20.0f && temp >= 0.0f)
                    {
                        player.displayClientMessage(
                                Component.translatable("item.agriculture_craft.thermometer.temperature")
                                        .append(Component.literal(" : " + temp))
                                        .withStyle(ChatFormatting.DARK_AQUA),
                                true
                        );
                    }
                    else if (temp <= 0.0f)
                    {
                        player.displayClientMessage(
                                Component.translatable("item.agriculture_craft.thermometer.temperature")
                                        .append(Component.literal(" : " + temp))
                                        .withStyle(ChatFormatting.AQUA),
                                true
                        );
                    }
                }

                // Run client-only logic (like rendering particles)
                if (level.isClientSide())
                {
                    // Your client logic here
                }
            }
            super.inventoryTick(stack, level, entity, slotId, isSelected);
        }
    }
}