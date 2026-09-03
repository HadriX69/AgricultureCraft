package com.hadrix.agriculture_craft.datagen;

import java.util.concurrent.CompletableFuture;

import com.hadrix.agriculture_craft.AllItems;
import com.hadrix.agriculture_craft.AgricultureCraft;
import com.simibubi.create.api.data.recipe.BaseRecipeProvider.GeneratedRecipe;
import com.simibubi.create.api.data.recipe.DeployingRecipeGen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;

/**
 * Deploying recipe generator. A single standalone step, unlike the multi-step
 * sequenced assembly example.
 */
public class AgricultureCraftDeployingRecipeGen extends DeployingRecipeGen {

    GeneratedRecipe EXAMPLE = create("agriculture_craft_deploying", b -> b
            .require(Items.IRON_INGOT)
            .require(Items.COPPER_INGOT)
            .output(AllItems.EXAMPLE_RESULT.get()));

    public AgricultureCraftDeployingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, AgricultureCraft.ID);
    }
}
