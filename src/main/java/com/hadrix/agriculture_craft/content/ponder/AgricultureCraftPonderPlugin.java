package com.hadrix.agriculture_craft.content.ponder;

import com.hadrix.agriculture_craft.AllBlocks;
import com.hadrix.agriculture_craft.AgricultureCraft;

import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.minecraft.resources.ResourceLocation;

/**
 * Ponder plugin for the addon, registered client-side in AgricultureCraft. registerScenes
 * associates a storyboard with one or more items. Each scene has two parts: a schematic
 * saved as an nbt file under assets/agriculture_craft/ponder, whose name matches the id passed
 * to addStoryBoard, and the storyboard code in AgricultureCraftPonderScenes.
 */
public class AgricultureCraftPonderPlugin implements PonderPlugin {

    @Override
    public String getModId() {
        return AgricultureCraft.ID;
    }

    @Override
    public void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        helper.forComponents(AllBlocks.EXAMPLE_KINETIC_BLOCK.getId())
                .addStoryBoard("agriculture_craft_ponder", AgricultureCraftPonderScenes::examplePonder);

        helper.forComponents(com.simibubi.create.AllBlocks.DESK_BELL.getId())
                .addStoryBoard("desk_bell", DeskbellScenes::intro);
    }
}
