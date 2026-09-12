package com.hadrix.agriculture_craft.block;

import com.hadrix.agriculture_craft.ModPartialModels;
import com.mojang.blaze3d.vertex.PoseStack;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;

public class SprinklerRenderer implements BlockEntityRenderer<SprinklerBlockEntity> {

    public SprinklerRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(SprinklerBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int light, int overlay) {
        BlockState state = blockEntity.getBlockState();

        SuperByteBuffer helixBuffer = CachedBuffers.partial(ModPartialModels.HELIX, state);
        float animatedAngle = Mth.lerp(partialTick, blockEntity.prevAngle, blockEntity.angle);

        helixBuffer
                .light(light)
                .overlay(overlay)
                .scale(2.0f,2.0f,2.0f)
                .translate(-0.25f, -0.150, -0.25f)
                .rotateYCenteredDegrees(animatedAngle)
                .renderInto(poseStack, buffer.getBuffer(RenderType.cutoutMipped()));
    }


}
