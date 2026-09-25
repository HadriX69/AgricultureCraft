package com.hadrix.agriculture_craft.block;

import com.hadrix.agriculture_craft.AllBlocks;
import com.hadrix.agriculture_craft.ModPartialModels;
import com.hadrix.agriculture_craft.tree.BananaTree;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.joml.Quaternionf;

public class BananaTreeRenderer implements BlockEntityRenderer<BananaTreeBlockEntity> {

    public BananaTreeRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public AABB getRenderBoundingBox(BananaTreeBlockEntity blockEntity) {
        return new AABB(blockEntity.getBlockPos()).inflate(15.0D);
    }

    @Override
    public void render(BananaTreeBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
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

        //SuperByteBuffer LeafBuffer = CachedBuffers.partial(Leaf, state);
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
                z_offset = 0.0f; // On colle au maximum
                break;
            case 3:
                high = 3.0f;
                size = 1.0f;
                z_offset = -0.1f; // On rentre légèrement dans le tronc
                break;
            case 4:
                high = 4.0f;
                size = 1.66f;
                z_offset = -0.25f; // Plus la feuille est grande, plus on la ramène vers le centre
                break;
            case 5:
                high = 5.0f;
                size = 2.0f;
                z_offset = -0.4f;
                break;
            case 6:
            case 7:
                high = 5.9f;
                size = 2.75f;
                z_offset = -0.6f; // On enfonce bien la base de la feuille dans l'arbre
                break;
        }

//        LeafBuffer
//                .light(packedLight)
//                .overlay(packedOverlay)
//                .scale(size,size,size)
//                .translate(x_offset, high, z_offset)
//                .renderInto(poseStack, bufferSource.getBuffer(RenderType.cutoutMipped()));

        poseStack.pushPose();

        // 1. On se place exactement au centre du sommet du tronc
        poseStack.translate(0.5D, high, 0.5D);

        int leavesCount = 8; // Nombre de feuilles par couronne
        float angleStep = 360.0f / leavesCount;

        // --- COURONNE DU BAS (Feuilles plus inclinées) ---
        for (int i = 0; i < leavesCount; i++) {
            poseStack.pushPose();

            // Rotation en cercle autour du tronc
            poseStack.mulPose(Axis.YP.rotationDegrees(i * angleStep));

            // Inclinaison vers le bas
            poseStack.mulPose(Axis.XP.rotationDegrees(30.0f));

            // Ajustement de la taille et de l'éloignement du tronc
            poseStack.scale(size, size, size);
            poseStack.translate(0.0D, 0.0D, z_offset);

            // Rendu de la feuille
            CachedBuffers.partial(Leaf, state)
                    .light(packedLight)
                    .overlay(packedOverlay)
                    .renderInto(poseStack, bufferSource.getBuffer(RenderType.cutoutMipped()));

            poseStack.popPose();
        }

        // --- COURONNE DU HAUT (Feuilles redressées et entre-mêlées) ---
        for (int i = 0; i < leavesCount; i++) {
            poseStack.pushPose();

            // On décale la rotation de la moitié d'un angle (ex: +22.5°) pour boucher les trous !
            poseStack.mulPose(Axis.YP.rotationDegrees((i * angleStep) + (angleStep / 2.0f)));

            // Inclinaison plus faible (plus vers le haut)
            poseStack.mulPose(Axis.XP.rotationDegrees(10.0f));

            // Légèrement plus haut et plus petit
            poseStack.scale(size * 0.85f, size * 0.85f, size * 0.85f);
            poseStack.translate(0.0D, 0.15D, z_offset * 0.8D);

            CachedBuffers.partial(Leaf, state)
                    .light(packedLight)
                    .overlay(packedOverlay)
                    .renderInto(poseStack, bufferSource.getBuffer(RenderType.cutoutMipped()));

            poseStack.popPose();
        }

        poseStack.popPose();


//        if (age < 2) {
//            return;
//        }
//
//        boolean isGolden = blockEntity.isGolden();
//
//        poseStack.pushPose();
//
//        float high = 0.0f;
//        float size = 1.0f;
//        float z_offset = 1.0f;
//
//        switch (age) {
//            case 2:
//                high = 2.0f;
//                size = 0.75f;
//                z_offset = 0.99f;
//                break;
//            case 3:
//                high = 3.0f;
//                size = 0.65f;
//                z_offset = 0.95f;
//                break;
//            case 4:
//                high = 4.0f;
//                size = 0.8f;
//                z_offset = 1.15f;
//                break;
//            case 5:
//                high = 5.0f;
//                size = 0.9f;
//                z_offset = 1.26f;
//                break;
//            case 6: // Adulte
//            case 7: // Adulte + bananes
//                high = 6.0f;
//                size = 1.0f;  // Taille max !
//                z_offset = 1.37f;
//                break;
//        }
//
//        // 6. On déplace le pinceau virtuel
//        // On se met au centre du bloc (0.5 sur X et Z) et on monte selon la 'high' calculée
//        poseStack.translate(0.5D, high, z_offset);
//
//        poseStack.scale(size, size, size);
//
//        // center the model
//        poseStack.translate(-0.5D, 0.0D, -0.5D);
//
//        //Quaternionf rotation = Axis.YP.rotationDegrees(45.0F);
//        //poseStack.rotateAround(rotation,0,-1,0);
//
//        BlockState leavesState = isGolden ?
//                AllBlocks.GOLDEN_BANANA_LEAVES.get().defaultBlockState() :
//                AllBlocks.BANANA_LEAVES.get().defaultBlockState();
//
//        // 8. On dessine !
//        BlockRenderDispatcher dispatcher = Minecraft.getInstance().getBlockRenderer();
//        dispatcher.renderSingleBlock(leavesState, poseStack, bufferSource, packedLight, packedOverlay, net.neoforged.neoforge.client.model.data.ModelData.EMPTY, null);
//
//        poseStack.popPose();
    }
}
