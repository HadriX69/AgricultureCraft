package com.hadrix.agriculture_craft.mixin;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = KineticBlockEntity.class, remap = false)
public abstract class KineticBlockEntityMixin extends SmartBlockEntity {

    // Champ personnalisé injecté dans chaque KineticBlockEntity
    @Unique
    private int agriculture_craft$lubricatedTimer = 0;

    public KineticBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    // 1. MODIFICATION DU STRESS : Réduit l'impact de stress de 50% si lubrifié
    @Inject(method = "calculateStressApplied", at = @At("RETURN"), cancellable = true)
    private void agriculture_craft$reduceStressWhenLubricated(CallbackInfoReturnable<Float> cir) {
        if (this.agriculture_craft$lubricatedTimer > 0) {
            float originalValue = cir.getReturnValue();
            // On divise par 2 l'impact de stress
            cir.setReturnValue(originalValue * 0.5f);
        }
    }

    // 2. TIMING DE LA LUBRIFICATION : Décompte des ticks
    @Inject(method = "tick", at = @At("HEAD"))
    private void agriculture_craft$tickLubrication(CallbackInfo ci) {
        if (this.agriculture_craft$lubricatedTimer > 0) {
            this.agriculture_craft$lubricatedTimer--;

            // Quand le timer expire, on force le réseau Create à re-calculer le stress !
            if (this.agriculture_craft$lubricatedTimer == 0 && this.level != null && !this.level.isClientSide()) {
                KineticBlockEntity self = (KineticBlockEntity) (Object) this;
                if (self.getOrCreateNetwork() != null) {
                    self.getOrCreateNetwork().updateStress();
                }
            }
        }
    }

    // 3. SAUVEGARDE NBT : On sauvegarde le timer quand le monde est sauvegardé
    @Inject(method = "write", at = @At("TAIL"))
    private void agriculture_craft$writeLubricationNbt(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket, CallbackInfo ci) {
        tag.putInt("LubricatedTimer", this.agriculture_craft$lubricatedTimer);
    }

    @Inject(method = "read", at = @At("TAIL"))
    private void agriculture_craft$readLubricationNbt(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket, CallbackInfo ci) {
        this.agriculture_craft$lubricatedTimer = tag.getInt("LubricatedTimer");
    }
}