package com.hadrix.agriculture_craft.block;

import com.hadrix.agriculture_craft.ModPartialModels;
import com.hadrix.agriculture_craft.tree.BananaTree;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.createmod.catnip.render.CachedBuffers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class BananaTreeRenderer implements BlockEntityRenderer<BananaTreeBlockEntity> {

    public BananaTreeRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public AABB getRenderBoundingBox(BananaTreeBlockEntity blockEntity) {
        return new AABB(blockEntity.getBlockPos()).inflate(15.0D);
    }

    @Override
    public void render(BananaTreeBlockEntity blockEntity, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        BlockState state = blockEntity.getBlockState();
        if (!state.hasProperty(BananaTree.AGE)) return;
        boolean isGolden = blockEntity.isGolden();
        PartialModel Leaf;
        if(isGolden) {
            Leaf = ModPartialModels.BANANA_TREE_GOLD_LEAF;
        }
        else
        {
            Leaf = ModPartialModels.BANANA_TREE_LEAF;
        }

        CachedBuffers.partial(Leaf, state);

        int age = state.getValue(BananaTree.AGE);
        float high = 0.0f;
        float size = 1.0f;
        float z_offset = 1.0f;
        float x_offset = 1.0f;

        if(age < 2)
        {
            return;
        }
        switch (age) {
            case 2:
                high = 2.0f;
                size = 0.75f;
                z_offset = -0.25f;
                break;
            case 3:
                high = 3.0f;
                size = 1.0f;
                z_offset = -0.5f;
                break;
            case 4:
                high = 4.0f;
                size = 1.66f;
                z_offset = -0.5f;
                break;
            case 5:
                high = 5.0f;
                size = 2.0f;
                z_offset = -0.5f;
                break;
            case 6:
            case 7:
                high = 5.9f;
                size = 2.75f;
                z_offset = -0.6f;
                break;
        }

        poseStack.pushPose();

        poseStack.translate(0.5D, high, 0.5D);

        int leavesCount = 8;
        float angleStep = 360.0f / leavesCount;

        // ---  DOWN LEAVES
        for (int i = 0; i < leavesCount; i++) {
            poseStack.pushPose();

            poseStack.mulPose(Axis.YP.rotationDegrees(i * angleStep));

            poseStack.mulPose(Axis.XP.rotationDegrees(30.0f));

            poseStack.scale(size, size, size);
            poseStack.translate(0.0D, 0.0D, z_offset);

            CachedBuffers.partial(Leaf, state)
                    .light(packedLight)
                    .overlay(packedOverlay)
                    .renderInto(poseStack, bufferSource.getBuffer(RenderType.cutoutMipped()));

            poseStack.popPose();
        }

        // --- TOP LEAVES
        for (int i = 0; i < leavesCount; i++) {
            poseStack.pushPose();

            poseStack.mulPose(Axis.YP.rotationDegrees((i * angleStep) + (angleStep / 2.0f)));

            poseStack.mulPose(Axis.XP.rotationDegrees(10.0f));

            poseStack.scale(size * 0.85f, size * 0.85f, size * 0.85f);
            poseStack.translate(0.0D, 0.15D, z_offset * 0.8D);

            CachedBuffers.partial(Leaf, state)
                    .light(packedLight)
                    .overlay(packedOverlay)
                    .renderInto(poseStack, bufferSource.getBuffer(RenderType.cutoutMipped()));

            poseStack.popPose();
        }

        poseStack.popPose();
    }
}
