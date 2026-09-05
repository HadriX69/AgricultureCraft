package com.hadrix.agriculture_craft;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.*;
import net.minecraft.world.level.biome.Biome;

import static java.lang.Math.round;

public class ClimateManager
{
    public ClimateManager()
    {

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

    public static float GetTemperature(Level level, BlockPos blockPos)
    {
        float baseTemp = level.getBiome(blockPos).value().getBaseTemperature();
        float baseCelsius = (baseTemp * 25.0f) - 5.0f;
        double y = blockPos.getY();

        // 1. Calcul des conditions de surface (Soleil et Météo)
        long timeOfDay = level.getDayTime() % 24000;
        float sunCurve = (float) Math.sin((timeOfDay / 24000.0) * 2 * Math.PI);
        float amplitudeThermique = (baseTemp > 1.5f) ? 8.0f : 4.0f;

        float surfaceTemp = baseCelsius + (sunCurve * amplitudeThermique);

        if (level.isRainingAt(blockPos)) {
            surfaceTemp -= 2.0f;
        }

        // 2. Modificateur d'Altitude et de Profondeur
        float finalTemperature;

        if (y >= 63) {
            // En montagne : on perd 1°C tous les 10 blocs d'altitude
            finalTemperature = surfaceTemp - (float) ((y - 63) * 0.1f);
        } else {
            // Sous terre : Transition douce vers 12°C
            float depth = (float) (63 - y);

            // On bloque la profondeur maximum à 127 pour éviter les bugs si le joueur tombe dans le vide
            if (depth > 127.0f) depth = 127.0f;

            // depthFactor vaut 0.0 à la surface (Y=63) et 1.0 à la bedrock (Y=-64)
            float depthFactor = depth / 127.0f;

            // La formule magique du Lerp : TempératureSurface + (Différence * Pourcentage)
            finalTemperature = surfaceTemp + (12.0f - surfaceTemp) * depthFactor;
        }

        // 3. Arrondi propre à un chiffre après la virgule
        return Math.round(finalTemperature * 10.0f) / 10.0f;
    }



    public static void OnTick(Minecraft mc)
    {

    }
}
