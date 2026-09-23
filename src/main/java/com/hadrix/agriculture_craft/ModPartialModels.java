package com.hadrix.agriculture_craft;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.minecraft.resources.ResourceLocation;

public class ModPartialModels {
    public static final PartialModel HELIX = PartialModel.of(
            ResourceLocation.fromNamespaceAndPath("agriculture_craft", "block/sprinkler_helix")
    );

    public static final PartialModel BANANA_TREE_LEAF = PartialModel.of(
            ResourceLocation.fromNamespaceAndPath("agriculture_craft", "block/banana_tree_leaf")
    );

    public static final PartialModel BANANA_TREE_GOLD_LEAF = PartialModel.of(
            ResourceLocation.fromNamespaceAndPath("agriculture_craft", "block/banana_tree_golden_leaf")
    );

    public static void init() {}
}
