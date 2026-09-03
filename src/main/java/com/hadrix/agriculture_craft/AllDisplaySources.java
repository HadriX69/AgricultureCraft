package com.hadrix.agriculture_craft;

import com.hadrix.agriculture_craft.content.display.AgricultureCraftDisplaySource;
import com.simibubi.create.api.behaviour.display.DisplaySource;
import com.tterrag.registrate.util.entry.RegistryEntry;

/**
 * Display source registration. Attach an entry to a block in AllBlocks with
 * transform(DisplaySource.displaySource(entry)).
 */
public class AllDisplaySources {

    public static final RegistryEntry<DisplaySource, AgricultureCraftDisplaySource> EXAMPLE_SOURCE = AgricultureCraft.REGISTRATE
            .displaySource("agriculture_craft_source", AgricultureCraftDisplaySource::new)
            .register();

    public static void register() {
        // Force class loading to trigger Registrate calls
    }
}
