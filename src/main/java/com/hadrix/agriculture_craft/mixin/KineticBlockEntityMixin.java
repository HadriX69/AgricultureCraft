package com.hadrix.agriculture_craft.mixin;

import com.hadrix.agriculture_craft.ILubricatable;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(value = KineticBlockEntity.class)
public abstract class KineticBlockEntityMixin extends SmartBlockEntity implements ILubricatable {

    @Unique
    private int agriculture_craft$lubricatedTimer = 0;

    public KineticBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void agriculture_craft$setLubricated(int ticks) {
        this.agriculture_craft$lubricatedTimer = ticks;
    }

    @Override
    public int agriculture_craft$getLubricatedTimer() {
        return this.agriculture_craft$lubricatedTimer;
    }

    // --- 1. LES LUNETTES (GOGGLES) ---
    @Inject(method = "addToGoggleTooltip", at = @At("RETURN"), remap = false)
    private void agriculture_craft$addGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking, CallbackInfoReturnable<Boolean> cir) {
        if (this.agriculture_craft$lubricatedTimer > 0) {
            int seconds = this.agriculture_craft$lubricatedTimer / 20;
            int minutes = seconds / 60;
            int remainingSeconds = seconds % 60;

            // Ajoute une ligne vide pour aérer
            tooltip.add(Component.empty());

            // Format à la "Create" : Espaces au début, texte gris, valeurs en jaune/or
            tooltip.add(Component.translatable("item.agriculture_craft.lubrication")
                    .withStyle(ChatFormatting.GRAY)
                    .append(Component.literal(String.format("%02d:%02d", minutes, remainingSeconds))
                            .withStyle(ChatFormatting.GOLD)));
        }
    }

    // --- 2. RÉDUIRE LE STRESS CONSOMMÉ (ex: Presse, Mixeur) ---
    @Inject(method = "calculateStressApplied", at = @At("RETURN"), cancellable = true, remap = false)
    private void agriculture_craft$reduceStressApplied(CallbackInfoReturnable<Float> cir) {
        if (this.agriculture_craft$lubricatedTimer > 0) {
            cir.setReturnValue(cir.getReturnValue() * 0.25f); // Divise par 4
        }
    }

    // --- 3. AUGMENTER LA CAPACITÉ PRODUITE (ex: Roue à eau, Moteur) ---
    @Inject(method = "calculateAddedStressCapacity", at = @At("RETURN"), cancellable = true, remap = false)
    private void agriculture_craft$boostStressCapacity(CallbackInfoReturnable<Float> cir) {
        if (this.agriculture_craft$lubricatedTimer > 0) {
            cir.setReturnValue(cir.getReturnValue() * 1.5f); // +50% de production
        }
    }

    // --- 4. GESTION DU TEMPS ET MISE À JOUR ---
    @Inject(method = "tick", at = @At("HEAD"))
    private void agriculture_craft$tickLubrication(CallbackInfo ci) {
        if (this.agriculture_craft$lubricatedTimer > 0) {
            this.agriculture_craft$lubricatedTimer--;

            // Si le timer expire, on force Create à se mettre à jour
            if (this.agriculture_craft$lubricatedTimer == 0 && this.level != null && !this.level.isClientSide()) {
                KineticBlockEntity self = (KineticBlockEntity) (Object) this;

                if (self.getOrCreateNetwork() != null) {
                    self.getOrCreateNetwork().updateCapacity();
                    self.getOrCreateNetwork().updateStress();
                }
                // Met à jour les clients (pour que les Goggles s'éteignent)
                self.sendData();
            }
        }
    }

    // --- 5. SAUVEGARDES ---
    @Inject(method = "write", at = @At("TAIL"))
    private void agriculture_craft$writeLubrication(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket, CallbackInfo ci) {
        tag.putInt("LubricatedTimer", this.agriculture_craft$lubricatedTimer);
    }

    @Inject(method = "read", at = @At("TAIL"))
    private void agriculture_craft$readLubrication(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket, CallbackInfo ci) {
        this.agriculture_craft$lubricatedTimer = tag.getInt("LubricatedTimer");
    }
}