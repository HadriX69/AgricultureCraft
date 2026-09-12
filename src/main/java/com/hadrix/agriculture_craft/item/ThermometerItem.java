package com.hadrix.agriculture_craft.item;

import com.hadrix.agriculture_craft.ClimateManager;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ThermometerItem extends Item {

    public ThermometerItem(Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (entity instanceof Player player) {

            // 2. Check if the item is in the active hands (Main hand or Offhand)
            boolean isHeld = player.getMainHandItem() == stack || player.getOffhandItem() == stack;

            if (isHeld) {
                // Run server-only logic (like potion effects or logic)
                if (!level.isClientSide())
                {
                    if(ClimateManager.GetTemperature(entity.getCommandSenderWorld(), entity.getOnPos()) >= 30.0f)
                    {
                        player.displayClientMessage(Component.literal("§4 Temperature : " + ClimateManager.GetTemperature(entity.getCommandSenderWorld(), entity.getOnPos())), true);
                    }
                    else if (ClimateManager.GetTemperature(entity.getCommandSenderWorld(), entity.getOnPos()) >= 20.0f && ClimateManager.GetTemperature(entity.getCommandSenderWorld(), entity.getOnPos()) <= 30.0f)
                    {
                        player.displayClientMessage(Component.literal("§c Temperature : " + ClimateManager.GetTemperature(entity.getCommandSenderWorld(), entity.getOnPos())), true);
                    }
                    else if (ClimateManager.GetTemperature(entity.getCommandSenderWorld(), entity.getOnPos()) <= 20.0f && ClimateManager.GetTemperature(entity.getCommandSenderWorld(), entity.getOnPos()) >= 0.0f)
                    {
                        player.displayClientMessage(Component.literal("§3 Temperature : " + ClimateManager.GetTemperature(entity.getCommandSenderWorld(), entity.getOnPos())), true);
                    }
                    else if (ClimateManager.GetTemperature(entity.getCommandSenderWorld(), entity.getOnPos()) <= 0.0f)
                    {
                        player.displayClientMessage(Component.literal("§b Temperature : " + ClimateManager.GetTemperature(entity.getCommandSenderWorld(), entity.getOnPos())), true);
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