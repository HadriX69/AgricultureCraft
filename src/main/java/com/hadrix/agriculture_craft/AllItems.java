package com.hadrix.agriculture_craft;

import com.hadrix.agriculture_craft.item.AnemometerItem;
import com.hadrix.agriculture_craft.item.BananaItem;
import com.hadrix.agriculture_craft.item.SeasonometerItem;
import com.hadrix.agriculture_craft.item.ThermometerItem;
import com.tterrag.registrate.util.entry.ItemEntry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ModelFile;

/**
 * Item registration. Each item overrides its model to borrow a vanilla texture, so the
 * template builds with no texture files of its own. Point the model at your own texture,
 * or remove the override and add assets/agriculture_craft/textures/item/name.png.
 */
public class AllItems {

    public static final ItemEntry<Item> EXAMPLE_ITEM = AgricultureCraft.REGISTRATE
            .item("agriculture_craft_item", Item::new)
            .model((c, p) -> p.generated(c::getEntry, ResourceLocation.withDefaultNamespace("item/amethyst_shard")))
            .register();

    /**
     * Output of the sequenced assembly and several other example recipes.
     */
    public static final ItemEntry<Item> EXAMPLE_RESULT = AgricultureCraft.REGISTRATE
            .item("agriculture_craft_result", Item::new)
            .model((c, p) -> p.generated(c::getEntry, ResourceLocation.withDefaultNamespace("item/netherite_ingot")))
            .register();

    /**
     * Transitional item carried between the steps of the sequenced assembly recipe.
     */
    public static final ItemEntry<Item> INCOMPLETE_EXAMPLE = AgricultureCraft.REGISTRATE
            .item("incomplete_example", Item::new)
            .model((c, p) -> p.generated(c::getEntry, ResourceLocation.withDefaultNamespace("item/iron_ingot")))
            .register();

    public static final ItemEntry<AnemometerItem> ANEMOMETER = AgricultureCraft.REGISTRATE
            .item("anemometer", AnemometerItem::new)
            .model((c, p) -> p.generated(c::getEntry, ResourceLocation.withDefaultNamespace("item/brick")))
            .register();

    public static final ItemEntry<ThermometerItem> THERMOMETER = AgricultureCraft.REGISTRATE
            .item("thermometer", ThermometerItem::new)
            .model((c, p) -> p.withExistingParent(c.getName(), ResourceLocation.fromNamespaceAndPath("agriculture_craft", "item/thermometer_3d")))
            .register();

    public static final ItemEntry<SeasonometerItem> SEASONOMETER = AgricultureCraft.REGISTRATE
            .item("seasonometer", SeasonometerItem::new)
            .model((c, p) -> {

                p.generated(c::getEntry, AgricultureCraft.asResource("item/seasonometer_default_t"))

                        .override()
                        .predicate(AgricultureCraft.asResource("season"), 1.0f)
                        .model(new ModelFile.UncheckedModelFile(AgricultureCraft.asResource("item/seasonometer_summer_t")))
                        .end()
                        .override()
                        .predicate(AgricultureCraft.asResource("season"), 2.0f)
                        .model(new ModelFile.UncheckedModelFile(AgricultureCraft.asResource("item/seasonometer_autumn_t")))
                        .end()
                        .override()
                        .predicate(AgricultureCraft.asResource("season"), 3.0f)
                        .model(new ModelFile.UncheckedModelFile(AgricultureCraft.asResource("item/seasonometer_winter_t")))
                        .end()
                        .override()
                        .predicate(AgricultureCraft.asResource("season"), 4.0f)
                        .model(new ModelFile.UncheckedModelFile(AgricultureCraft.asResource("item/seasonometer_spring_t")))
                        .end();
            })
            .register();

    public static final ItemEntry<BananaItem> BANANA_ITEM = AgricultureCraft.REGISTRATE
            .item("banana_item.json", BananaItem::new)
            .model((c, p) -> p.withExistingParent(c.getName(), ResourceLocation.fromNamespaceAndPath("agriculture_craft", "item/banana_item")))
            .register();




    public static void register() {
        // Force class loading to trigger Registrate calls
    }
}
