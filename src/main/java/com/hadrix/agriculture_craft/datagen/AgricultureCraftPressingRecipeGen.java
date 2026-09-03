package com.hadrix.agriculture_craft.datagen;

import java.util.concurrent.CompletableFuture;

import com.hadrix.agriculture_craft.AllItems;
import com.hadrix.agriculture_craft.AgricultureCraft;
import com.simibubi.create.api.data.recipe.BaseRecipeProvider.GeneratedRecipe;
import com.simibubi.create.api.data.recipe.PressingRecipeGen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;

/**
 * Pressing recipe generator.
 */
public class AgricultureCraftPressingRecipeGen extends PressingRecipeGen {

    GeneratedRecipe EXAMPLE = create("agriculture_craft_pressing", b -> b
            .require(Items.IRON_INGOT)
            .output(AllItems.EXAMPLE_ITEM.get()));

    public AgricultureCraftPressingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, AgricultureCraft.ID);
    }
}
