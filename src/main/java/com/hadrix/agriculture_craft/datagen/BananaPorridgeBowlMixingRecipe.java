package com.hadrix.agriculture_craft.datagen;

import java.util.concurrent.CompletableFuture;

import com.hadrix.agriculture_craft.AllItems;
import com.hadrix.agriculture_craft.AgricultureCraft;
import com.simibubi.create.api.data.recipe.BaseRecipeProvider.GeneratedRecipe;
import com.simibubi.create.api.data.recipe.MixingRecipeGen;
import com.simibubi.create.content.processing.recipe.HeatCondition;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;

/**
 * Mixing recipe generator. This one requires heat; drop the requiresHeat call for a
 * recipe with no heat requirement.
 */
public class BananaPorridgeBowlMixingRecipe extends MixingRecipeGen {

    GeneratedRecipe EXAMPLE = create("banana_porridge_mixing", b -> b
            .require(AllItems.BANANA_PORRIDGE.get())
            .require(Items.BOWL)
            .output(AllItems.BANANA_PORRIDGE_BOWL.get())
            .requiresHeat(HeatCondition.NONE));

    public BananaPorridgeBowlMixingRecipe(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, AgricultureCraft.ID);
    }
}
