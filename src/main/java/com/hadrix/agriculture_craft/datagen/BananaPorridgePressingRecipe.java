package com.hadrix.agriculture_craft.datagen;

import java.util.concurrent.CompletableFuture;

import com.hadrix.agriculture_craft.AllItems;
import com.hadrix.agriculture_craft.AgricultureCraft;
import com.simibubi.create.api.data.recipe.BaseRecipeProvider;
import com.simibubi.create.api.data.recipe.PressingRecipeGen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

public class BananaPorridgePressingRecipe extends PressingRecipeGen{

        BaseRecipeProvider.GeneratedRecipe BANANA_PRESSING = create("banana_pressing", b -> b
                .require(AllItems.BANANA_ITEM.get())
                .output(AllItems.BANANA_PORRIDGE.get()));

        public BananaPorridgePressingRecipe(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
            super(output, registries, AgricultureCraft.ID);
        }
}
