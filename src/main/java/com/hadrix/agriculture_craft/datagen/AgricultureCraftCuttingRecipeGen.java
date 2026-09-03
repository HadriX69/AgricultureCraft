package com.hadrix.agriculture_craft.datagen;

import java.util.concurrent.CompletableFuture;

import com.hadrix.agriculture_craft.AgricultureCraft;
import com.simibubi.create.api.data.recipe.BaseRecipeProvider.GeneratedRecipe;
import com.simibubi.create.api.data.recipe.CuttingRecipeGen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;

/**
 * Cutting recipe generator.
 */
public class AgricultureCraftCuttingRecipeGen extends CuttingRecipeGen {

    GeneratedRecipe EXAMPLE = create("agriculture_craft_cutting", b -> b
            .require(Items.OAK_LOG)
            .output(Items.OAK_PLANKS, 6)
            .duration(50));

    public AgricultureCraftCuttingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, AgricultureCraft.ID);
    }
}
