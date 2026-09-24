package com.hadrix.agriculture_craft.block;

import com.hadrix.agriculture_craft.AllBlocks;
import com.hadrix.agriculture_craft.tree.BananaTree;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Quaternionf;

public class BananaTreeRenderer implements BlockEntityRenderer<BananaTreeBlockEntity> {

    public BananaTreeRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(BananaTreeBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        BlockState state = blockEntity.getBlockState();
        if (!state.hasProperty(BananaTree.AGE)) return;

        int age = state.getValue(BananaTree.AGE);

        if (age < 2) {
            return;
        }

        boolean isGolden = blockEntity.isGolden();

        poseStack.pushPose();

        float high = 0.0f;
        float size = 1.0f;
        float z_offset = 1.0f;

        switch (age) {
            case 2:
                high = 2.0f; // Les feuilles sont 2 blocs plus haut que la base
                size = 0.75f;  // Les feuilles sont à 50% de leur size
                z_offset = 0.99f;
                break;
            case 3:
                high = 3.0f;
                size = 0.65f;
                z_offset = 0.95f;
                break;
            case 4:
                high = 4.0f;
                size = 0.8f;
                z_offset = 1.15f;
                break;
            case 5:
                high = 5.0f;
                size = 0.9f;
                z_offset = 1.26f;
                break;
            case 6: // Adulte
            case 7: // Adulte + bananes
                high = 6.0f;
                size = 1.0f;  // Taille max !
                z_offset = 1.37f;
                break;
        }

        // 6. On déplace le pinceau virtuel
        // On se met au centre du bloc (0.5 sur X et Z) et on monte selon la 'high' calculée
        poseStack.translate(0.5D, high, z_offset);

        // On redimensionne le modèle selon la 'size'
        poseStack.scale(size, size, size);

        // On recentre le modèle pour qu'il s'étire bien depuis son centre
        poseStack.translate(-0.5D, 0.0D, -0.5D);

        //Quaternionf rotation = Axis.YP.rotationDegrees(45.0F);
        //poseStack.rotateAround(rotation,0,-1,0);

        // 7. On choisit quel modèle fantôme dessiner (Or ou Normal)
        BlockState leavesState = isGolden ?
                AllBlocks.GOLDEN_BANANA_LEAVES.get().defaultBlockState() :
                AllBlocks.BANANA_LEAVES.get().defaultBlockState();

        // 8. On dessine !
        BlockRenderDispatcher dispatcher = Minecraft.getInstance().getBlockRenderer();
        dispatcher.renderSingleBlock(leavesState, poseStack, bufferSource, packedLight, packedOverlay, net.neoforged.neoforge.client.model.data.ModelData.EMPTY, null);

        poseStack.popPose();
    }
}
