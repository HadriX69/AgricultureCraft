package com.hadrix.agriculture_craft.tree;

import com.hadrix.agriculture_craft.AllBlocks;
import com.hadrix.agriculture_craft.block.Banana;
import com.hadrix.agriculture_craft.block.BananaTreeBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BananaTree extends Block implements EntityBlock {

    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 8);
    public static final IntegerProperty POTTED_AGE = IntegerProperty.create("potted", 0,1);


    public static final VoxelShape SHAPE_AGE_0 = Block.box(6.0, 0.0, 6.0, 10.0, 16.0, 10.0);

    public static final VoxelShape SHAPE_AGE_1 = Block.box(5.0, 0.0, 5.0, 10.0, 20.0, 10.0);

    public static final VoxelShape SHAPE_AGE_2_3 = Block.box(5.0, 0.0, 5.0, 11.0, 16.0, 11.0);

    public static final VoxelShape SHAPE_AGE_4 = Block.box(2.0, 0.0, 2.0, 14.0, 16.0, 14.0);

    public static final VoxelShape SHAPE_AGE_5 = Block.box(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);

    public static final VoxelShape SHAPE_AGE_ADULT = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0);

    public BananaTree(Properties properties) {
        super(properties);

        this.registerDefaultState(this.getStateDefinition().any().setValue(AGE, 0));
        this.registerDefaultState(this.getStateDefinition().any().setValue(POTTED_AGE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE, POTTED_AGE);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag)
    {
        tooltipComponents.add(Component.translatable("tooltip.agriculture_craft.banana_tree.season"));
        tooltipComponents.add(Component.translatable("tooltip.agriculture_craft.banana_tree.temperature"));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        int currentAge = state.getValue(AGE);

        return switch (currentAge) {
            case 0 -> SHAPE_AGE_0;
            case 1 -> SHAPE_AGE_1;
            case 2,3 -> SHAPE_AGE_2_3;
            case 4 -> SHAPE_AGE_4;
            case 5 -> SHAPE_AGE_5;
            case 6 -> SHAPE_AGE_ADULT;
            case 7 -> SHAPE_AGE_ADULT;
            default -> SHAPE_AGE_ADULT;
        };
    }

    @Override
    public SoundType getSoundType(BlockState state, LevelReader level, BlockPos pos, @Nullable Entity entity) {
        int currentAge = state.getValue(AGE);

        return switch (currentAge) {
            case 0,1 -> SoundType.GRASS;
            case 2,3,4,5,6,7 -> SoundType.WOOD;
            default -> SoundType.WOOD;
        };
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        BlockPos bottomBlockPos = new BlockPos(pos.getX(), pos.getY() - 1,pos.getZ());
        if(level.getBlockState(bottomBlockPos).is(Blocks.DIRT) || level.getBlockState(bottomBlockPos).is(Blocks.GRASS_BLOCK))
        {

        }
    }

    public void DoParticle(ServerLevel level, BlockPos pos)
    {
        level.sendParticles(ParticleTypes.HAPPY_VILLAGER,
                pos.getX() + 0.5,
                pos.getY() + 0.5,
                pos.getZ() + 0.5,
                5,
                0.5,  // X
                0.5,  // Y
                0.5,  // Z
                0.0   // Speed
        );
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        int TreeAge = 6;
        if(level.getBlockEntity(pos) instanceof BananaTreeBlockEntity bananaTreeBlockEntity && stack.is(Items.BONE_MEAL))
        {
            if(state != null)
            {
            int light = level.getMaxLocalRawBrightness(pos.above());

            int currentAge = state.getValue(AGE);

                if (light >= 9 && currentAge < 8) {

                    BlockPos highestPos = pos.above(currentAge + 1);
                    TreeAge = currentAge;

                    if (level.isEmptyBlock(highestPos)) {
                        if (currentAge == 0) {
                            //level.setBlock(pos, AllBlocks.BANANA_TREE_STATE_2.getDefaultState(), 3);

                            level.setBlock(pos, level.getBlockState(pos).setValue(AGE, 1), 3);


                            if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
                                DoParticle(serverLevel, pos);
                            }

                        } else if (currentAge == 1) {
                            //level.setBlock(pos, AllBlocks.BANANA_TRUNK_STATE_6.getDefaultState(), 3);


                            level.setBlock(pos.above(1), AllBlocks.BANANA_TRUNK_STATE_7.getDefaultState(), 3);

                            level.setBlock(pos, level.getBlockState(pos).setValue(AGE, 2), 3);

                            if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
                                DoParticle(serverLevel, pos);
                            }

                        } else if (currentAge == 2) {
                            //level.setBlock(pos, AllBlocks.BANANA_TRUNK_STATE_6.getDefaultState(), 3);

                            level.setBlock(pos.above(1), AllBlocks.BANANA_TRUNK_STATE_6.getDefaultState(), 3);
                            level.setBlock(pos.above(2), AllBlocks.BANANA_TRUNK_STATE_7.getDefaultState(), 3);

                            level.setBlock(pos, level.getBlockState(pos).setValue(AGE, 3), 3);

                            if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
                                DoParticle(serverLevel, pos);
                            }

                        } else if (currentAge == 3) {

                            //level.setBlock(pos,AllBlocks.BANANA_TRUNK_STATE_3.getDefaultState(),3);

                            level.setBlock(pos.above(1), AllBlocks.BANANA_TRUNK_STATE_4.getDefaultState(), 3);

                            level.setBlock(pos.above(2), AllBlocks.BANANA_TRUNK_STATE_4.getDefaultState(), 3);

                            level.setBlock(pos.above(3), AllBlocks.BANANA_TRUNK_STATE_5.getDefaultState(), 3);

                            level.setBlock(pos, level.getBlockState(pos).setValue(AGE, 4), 3);

                            if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
                                DoParticle(serverLevel, pos);
                            }

                        } else if (currentAge == 4) {

                            //level.setBlock(pos,AllBlocks.BANANA_TRUNK_STATE_2.getDefaultState(),3);

                            level.setBlock(pos.above(1), AllBlocks.BANANA_TRUNK_STATE_3.getDefaultState(), 3);

                            level.setBlock(pos.above(2), AllBlocks.BANANA_TRUNK_STATE_3.getDefaultState(), 3);

                            level.setBlock(pos.above(3), AllBlocks.BANANA_TRUNK_STATE_3.getDefaultState(), 3);

                            level.setBlock(pos.above(4), AllBlocks.BANANA_TRUNK_STATE_4.getDefaultState(), 3);

                            level.setBlock(pos, level.getBlockState(pos).setValue(AGE, 5), 3);

                            if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
                                DoParticle(serverLevel, pos);
                            }

                        } else if (currentAge == 5) {

                            //level.setBlock(pos,AllBlocks.BANANA_TRUNK_STATE_1.getDefaultState(),3);

                            level.setBlock(pos.above(1), AllBlocks.BANANA_TRUNK_STATE_2.getDefaultState(), 3);

                            level.setBlock(pos.above(2), AllBlocks.BANANA_TRUNK_STATE_2.getDefaultState(), 3);

                            level.setBlock(pos.above(3), AllBlocks.BANANA_TRUNK_STATE_2.getDefaultState(), 3);

                            level.setBlock(pos.above(4), AllBlocks.BANANA_TRUNK_STATE_2.getDefaultState(), 3);

                            level.setBlock(pos.above(5), AllBlocks.BANANA_TRUNK_STATE_3.getDefaultState(), 3);

                            level.setBlock(pos, level.getBlockState(pos).setValue(AGE, 6), 3);

                            if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
                                DoParticle(serverLevel, pos);
                            }
                        }
                    }
                    }
                }
        }
        if(TreeAge <= 5)
        {
            return ItemInteractionResult.SUCCESS;
        }
        else
        {
            return ItemInteractionResult.FAIL;
        }
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int light = level.getMaxLocalRawBrightness(pos.above());

        BananaTreeBlockEntity blockEntity = (BananaTreeBlockEntity) level.getBlockEntity(pos);

        int currentAge = state.getValue(AGE);
        BlockPos highestPos = pos.above(currentAge + 1);

        if (random.nextInt(10) == 0) {
            if (light >= 9 && currentAge < 8) {

                //BlockPos highestPos = pos.above(currentAge + 1);

                if (level.isEmptyBlock(highestPos)) {
                    if (currentAge == 0) {
                        //level.setBlock(pos, AllBlocks.BANANA_TREE_STATE_2.getDefaultState(), 3);

                        level.setBlock(pos, level.getBlockState(pos).setValue(AGE, 1), 3);

                        DoParticle(level, pos);
                    }
                    else if (currentAge == 1) {
                        //level.setBlock(pos, AllBlocks.BANANA_TRUNK_STATE_6.getDefaultState(), 3);


                        level.setBlock(pos.above(1), AllBlocks.BANANA_TRUNK_STATE_7.getDefaultState(), 3);

                        level.setBlock(pos, level.getBlockState(pos).setValue(AGE, 2), 3);

                        DoParticle(level, pos);
                    }
                    else if (currentAge == 2)
                    {
                        //level.setBlock(pos, AllBlocks.BANANA_TRUNK_STATE_6.getDefaultState(), 3);

                        level.setBlock(pos.above(1), AllBlocks.BANANA_TRUNK_STATE_6.getDefaultState(), 3);
                        level.setBlock(pos.above(2), AllBlocks.BANANA_TRUNK_STATE_7.getDefaultState(), 3);

                        level.setBlock(pos, level.getBlockState(pos).setValue(AGE, 3), 3);

                        DoParticle(level, pos);
                    }
                    else if (currentAge == 3)
                    {

                        //level.setBlock(pos,AllBlocks.BANANA_TRUNK_STATE_3.getDefaultState(),3);

                        level.setBlock(pos.above(1),AllBlocks.BANANA_TRUNK_STATE_4.getDefaultState(),3);

                        level.setBlock(pos.above(2),AllBlocks.BANANA_TRUNK_STATE_4.getDefaultState(),3);

                        level.setBlock(pos.above(3),AllBlocks.BANANA_TRUNK_STATE_5.getDefaultState(),3);

                        level.setBlock(pos, level.getBlockState(pos).setValue(AGE, 4), 3);

                        DoParticle(level, pos);
                    }

                    else if (currentAge == 4)

                    {

                        //level.setBlock(pos,AllBlocks.BANANA_TRUNK_STATE_2.getDefaultState(),3);

                        level.setBlock(pos.above(1),AllBlocks.BANANA_TRUNK_STATE_3.getDefaultState(),3);

                        level.setBlock(pos.above(2),AllBlocks.BANANA_TRUNK_STATE_3.getDefaultState(),3);

                        level.setBlock(pos.above(3),AllBlocks.BANANA_TRUNK_STATE_3.getDefaultState(),3);

                        level.setBlock(pos.above(4),AllBlocks.BANANA_TRUNK_STATE_4.getDefaultState(),3);

                        level.setBlock(pos, level.getBlockState(pos).setValue(AGE, 5), 3);

                        DoParticle(level, pos);
                    }

                    else if (currentAge == 5)

                    {

                        //level.setBlock(pos,AllBlocks.BANANA_TRUNK_STATE_1.getDefaultState(),3);

                        level.setBlock(pos.above(1),AllBlocks.BANANA_TRUNK_STATE_2.getDefaultState(),3);

                        level.setBlock(pos.above(2),AllBlocks.BANANA_TRUNK_STATE_2.getDefaultState(),3);

                        level.setBlock(pos.above(3),AllBlocks.BANANA_TRUNK_STATE_2.getDefaultState(),3);

                        level.setBlock(pos.above(4),AllBlocks.BANANA_TRUNK_STATE_2.getDefaultState(),3);

                        level.setBlock(pos.above(5),AllBlocks.BANANA_TRUNK_STATE_3.getDefaultState(),3);

                        level.setBlock(pos, level.getBlockState(pos).setValue(AGE, 6), 3);

                        DoParticle(level, pos);
                    }
                    else if (currentAge == 6)
                    {
                      level.setBlock(pos, level.getBlockState(pos).setValue(AGE, 7), 3);
                    }
                }
            }
        }
        if (random.nextInt(3) == 0 && blockEntity.isGolden())
        {
//            level.sendParticles(ParticleTypes.GLOW,
//                    pos.getX() + 0.5,
//                    highestPos.getY(),
//                    pos.getZ() + 0.5,
//                    5,
//                    3,  // X
//                    0.5,  // Y
//                    3,  // Z
//                    0.0   // Speed
//            );
        }
        if(random.nextInt(10) == 0 && currentAge == 7)
        {
            boolean hasGeneratedBananas = false; // Pour savoir si on a bien fait pousser au moins une banane
            Direction facing = Direction.NORTH;

            // 1. On utilise <= 1 pour bien faire -1, 0 et 1 (carré de 3x3)
            for(int x = -1; x <= 1; x++)
            {
                for(int z = -1; z <= 1; z++)
                {
                    // 2. Le trou au milieu ! Si X et Z sont à 0, c'est le tronc, on passe à la suite.
                    if (x == 0 && z == 0) {
                        continue; // Le mot-clé 'continue' dit à la boucle de passer au tour suivant
                    }

                    BlockPos bananaPos = new BlockPos(highestPos.getX() + x, highestPos.getY() - 3, highestPos.getZ() + z);

                    if(level.getBlockState(bananaPos).canBeReplaced())
                    {
                        if (x == -1) facing = Direction.EAST;
                        else if (x == 1) facing = Direction.WEST;
                        else if (z == -1) facing = Direction.SOUTH;
                        else if (z == 1) facing = Direction.NORTH;

                        level.setBlock(bananaPos, AllBlocks.BANANA.getDefaultState().setValue(Banana.FACING, facing), 3);
                        hasGeneratedBananas = true;
                    }
                }
            }
            if (hasGeneratedBananas) {
                level.setBlock(pos, level.getBlockState(pos).setValue(AGE, 6), 3);
            }
        }
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return AllBlocks.BANANA_TREE_LEAF_ENTITY.create(pos, state);
    }

}