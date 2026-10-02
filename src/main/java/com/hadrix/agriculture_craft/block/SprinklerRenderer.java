package com.hadrix.agriculture_craft.block;

import com.google.common.base.Supplier;
import com.hadrix.agriculture_craft.ModPartialModels;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class SprinklerRenderer extends KineticBlockEntityRenderer<SprinklerBlockEntity> {

    public SprinklerRenderer(BlockEntityRendererProvider.Context context)
    {
        super(context);
    }

    @Override
    public AABB getRenderBoundingBox(SprinklerBlockEntity blockEntity) {
        return new AABB(blockEntity.getBlockPos()).inflate(5.0D);
    }

    @Override
    protected void renderSafe(SprinklerBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int light, int overlay) {

        super.renderSafe(blockEntity, partialTicks, poseStack, buffer, light, overlay);

        BlockState state = blockEntity.getBlockState();
        float angle = getAngleForBe(blockEntity, blockEntity.getBlockPos(), Direction.Axis.Y);

        // 1. DESSINER LE DEMI-AXE (SHAFT) DE CREATE VERS LE HAUT
        SuperByteBuffer shaft = CachedBuffers.partial(AllPartialModels.SHAFT_HALF, state);
        // Cette fonction native de Create applique la lumière et la rotation parfaitement !

        kineticRotationTransform(shaft, blockEntity, Direction.Axis.Y, angle, light)
                .overlay(overlay)
                .rotateXCenteredDegrees(90)
                .translate(0f, 0, -0.55f)
                .renderInto(poseStack, buffer.getBuffer(RenderType.solid()));




        SuperByteBuffer helixBuffer = CachedBuffers.partial(ModPartialModels.HELIX, state);

        kineticRotationTransform(helixBuffer, blockEntity, Direction.Axis.Y, angle, light)
                .scale(2.0f,2.0f,2.0f)
                .translate(-0.25f, -0.150, -0.25f)
                .renderInto(poseStack, buffer.getBuffer(RenderType.cutoutMipped()));

//        helixBuffer
//                .light(light)
//                .overlay(overlay)
//                .scale(2.0f,2.0f,2.0f)
//                .translate(-0.25f, -0.150, -0.25f)
//                .rotateYCenteredDegrees(angle * speed)
//                .renderInto(poseStack, buffer.getBuffer(RenderType.cutoutMipped()));
    }
}
