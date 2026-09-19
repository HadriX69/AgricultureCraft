package com.hadrix.agriculture_craft.util;

import com.hadrix.agriculture_craft.AgricultureCraft;
import com.hadrix.agriculture_craft.AllItems;
import com.hadrix.agriculture_craft.ClimateManager;
import com.hadrix.agriculture_craft.Season;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;

public class ModItemProprieties
{
    public static void addCustomItemProprieties()
    {
        ItemProperties.register(AllItems.SEASONOMETER.get(), ResourceLocation.fromNamespaceAndPath(AgricultureCraft.ID, "season")
        , (stack, level, entity, seed) ->
                {
                    if (level == null && entity != null) {
                        level = (net.minecraft.client.multiplayer.ClientLevel) entity.level();
                    }
                    if (level == null) {
                        return 0f;
                    }

                    Season season = ClimateManager.GetCurrentSeason(level);

                    return switch (season) {
                        case SUMMER -> 1.0f;
                        case AUTUMN -> 2.0f;
                        case WINTER -> 3.0f;
                        case SPRING -> 4.0f;
                    };
                });
    }
}
