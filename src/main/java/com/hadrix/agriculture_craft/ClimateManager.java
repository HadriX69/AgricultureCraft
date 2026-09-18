package com.hadrix.agriculture_craft;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.*;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class ClimateManager
{

    private static final int DAYS_PER_SEASON = 7;
    private static final int DAYS_PER_YEAR = DAYS_PER_SEASON * 4;

    public ClimateManager()
    {

    }

    public static float calculateLocalHeatSources(Level level, BlockPos blockPos, int radius) {
        float totalHeat = 0.0f;

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    //BlockPos pos = blockPos.offset(x, y, z);
                    BlockState state = level.getBlockState(blockPos);

                    if (isHeatSource(level, blockPos,state)) {
                        double distance = blockPos.distSqr(blockPos);
                        if (distance <= radius * radius) {
                            totalHeat += (1.0f - (float)(Math.sqrt(distance) / radius));
                        }
                    }
                }
            }
        }
        return totalHeat;
    }

    private static boolean isHeatSource(Level level, BlockPos pos, BlockState state) {
        // Personnalisez selon vos blocs (Feu, Lave, Torche, etc.)
        return state.getLightEmission(level, pos) > 10 ||
                state.is(Blocks.FIRE) ||
                state.is(Blocks.LAVA);
    }

    public static Season getCurrentSeason(Level level)
    {
        long day = level.getDayTime() / 24000;
        int dayInYear = (int) (day % DAYS_PER_YEAR);
        int seasonIndex = dayInYear / DAYS_PER_SEASON;

        return Season.values()[seasonIndex];
    }

    public static String GetSeasonName(Level level)
    {
        long day = level.getDayTime() / 24000;
        int dayInYear = (int) (day % DAYS_PER_YEAR);
        int seasonIndex = dayInYear / DAYS_PER_SEASON;

        return Season.values()[seasonIndex].name();
    }

    public static String GetBiomeName(Level level, BlockPos blockPos)
    {
        Holder<Biome> Biome = level.getBiome(blockPos);
        return Biome.getRegisteredName();
    }

    public static Holder<Biome> GetBiome(Level level, BlockPos blockPos)
    {
        Holder<Biome> Biome = level.getBiome(blockPos);
        return Biome;
    }

    public static float GetTemperature(Level level, BlockPos blockPos) {
        float baseTemp = level.getBiome(blockPos).value().getBaseTemperature();
        float baseCelsius = (baseTemp * 25.0f) - 5.0f;
        double y = blockPos.getY();
        Season season = getCurrentSeason(level);
        float seasonOffset = season.getTempModifier();
        float localOffset = calculateLocalHeatSources(level, blockPos, 10);

        // 1. Conditions de surface avec un léger déphasage horaire
        long timeOfDay = level.getDayTime() % 24000;
        // Décalage pour que la température maximale arrive après midi
        float sunCurve = (float) Math.sin(((timeOfDay - 2000) / 24000.0) * 2 * Math.PI);
        float amplitudeThermique = (baseTemp > 1.5f) ? 6.0f : 3.0f;

        float surfaceTemp = baseCelsius + (sunCurve * amplitudeThermique);

        if (level.isRainingAt(blockPos)) {
            surfaceTemp -= 3.0f;
        }

        // 2. Modificateur d'Altitude et de Profondeur réaliste
        float finalTemperature = surfaceTemp;

        if (y >= 63) {
            // En montagne : gradient adiabatique réel (~0.0065°C par bloc)
            finalTemperature -= (float) ((y - 63) * 0.0065f);
        } else {
            // Sous terre : Gradient géothermique (la température augmente avec la profondeur)
            float depth = (float) (63 - y);
            finalTemperature += depth * 0.02f; // +0.02°C par bloc de profondeur
        }

        finalTemperature += seasonOffset + localOffset;

        // 3. Arrondi propre à un chiffre après la virgule
        return Math.round(finalTemperature * 10.0f) / 10.0f;
    }


    public static void OnTick(Minecraft mc)
    {

    }
}
