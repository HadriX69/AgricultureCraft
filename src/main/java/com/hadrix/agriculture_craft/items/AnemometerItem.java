package com.hadrix.agriculture_craft.items;

import com.hadrix.agriculture_craft.AgricultureCraft;
import com.hadrix.agriculture_craft.ClimateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class AnemometerItem extends Item
{
    boolean IsActivate = false;

    public AnemometerItem(Properties properties) {
        super(properties);

    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        BlockPos ClickedPos = context.getClickedPos();
        Minecraft McInstance = Minecraft.getInstance();

        if(!level.isClientSide) {
            if (!IsActivate) {
                player.displayClientMessage(Component.literal("§aActivate"), true);
                IsActivate = true;
            } else {
                context.getPlayer().displayClientMessage(Component.literal("§4Disable"), true);
                IsActivate = false;
            }
        }

        return super.useOn(context);
    }
}
