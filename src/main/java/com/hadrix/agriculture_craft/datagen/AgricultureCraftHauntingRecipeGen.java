package com.hadrix.agriculture_craft.datagen;

import java.util.concurrent.CompletableFuture;

import com.hadrix.agriculture_craft.AllItems;
import com.hadrix.agriculture_craft.AgricultureCraft;
import com.simibubi.create.api.data.recipe.BaseRecipeProvider.GeneratedRecipe;
import com.simibubi.create.api.data.recipe.HauntingRecipeGen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

/**
 * Haunting recipe generator. The convert helper is shorthand for a single-input,
 * single-output recipe.
 */
public class AgricultureCraftHauntingRecipeGen extends HauntingRecipeGen {

    GeneratedRecipe EXAMPLE = convert(AllItems.EXAMPLE_ITEM.get(), AllItems.EXAMPLE_RESULT.get());

    public AgricultureCraftHauntingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, AgricultureCraft.ID);
    }
}
