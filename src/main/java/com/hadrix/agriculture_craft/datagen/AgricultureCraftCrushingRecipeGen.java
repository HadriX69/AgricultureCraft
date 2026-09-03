package com.hadrix.agriculture_craft.datagen;

import java.util.concurrent.CompletableFuture;

import com.hadrix.agriculture_craft.AgricultureCraft;
import com.simibubi.create.api.data.recipe.BaseRecipeProvider.GeneratedRecipe;
import com.simibubi.create.api.data.recipe.CrushingRecipeGen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;

/**
 * Crushing recipe generator. Shows a guaranteed output alongside a chance-based
 * secondary output.
 */
public class AgricultureCraftCrushingRecipeGen extends CrushingRecipeGen {

    GeneratedRecipe EXAMPLE = create("agriculture_craft_crushing", b -> b
            .require(Items.COBBLESTONE)
            .output(Items.GRAVEL)
            .output(0.25f, Items.FLINT)
            .duration(150));

    public AgricultureCraftCrushingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, AgricultureCraft.ID);
    }
}
