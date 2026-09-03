package com.hadrix.agriculture_craft.datagen;

import java.util.concurrent.CompletableFuture;

import com.hadrix.agriculture_craft.AgricultureCraft;
import com.simibubi.create.api.data.recipe.BaseRecipeProvider.GeneratedRecipe;
import com.simibubi.create.api.data.recipe.MillingRecipeGen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;

/**
 * Milling recipe generator.
 */
public class AgricultureCraftMillingRecipeGen extends MillingRecipeGen {

    GeneratedRecipe EXAMPLE = create("agriculture_craft_milling", b -> b
            .require(Items.COBBLESTONE)
            .output(Items.SAND)
            .duration(100));

    public AgricultureCraftMillingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, AgricultureCraft.ID);
    }
}
