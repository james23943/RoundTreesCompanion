package com.roundtrees.roundtreescompanion.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.WallSignBlock;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.SignBlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.block.BlockState;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SignBlockEntityRenderer.class)
public class SignBlockEntityRendererMixin {
    
    @Inject(method = "render", at = @At("HEAD"))
    private void adjustSignRendering(SignBlockEntity signEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, CallbackInfo ci) {
        if (signEntity == null) return;
        BlockState state = signEntity.getCachedState();
        Block thisBlock = state.getBlock();
        
        if (thisBlock instanceof WallSignBlock && state.contains(Properties.HORIZONTAL_FACING)) {
            Direction facing = state.get(Properties.HORIZONTAL_FACING);
            BlockPos signPos = signEntity.getPos();
            BlockPos behindPos = signPos.offset(facing.getOpposite());
            
            if (signEntity.getWorld() != null) {
                BlockState backState = signEntity.getWorld().getBlockState(behindPos);
                if (backState.isIn(BlockTags.LOGS)) {
                    Vec3d shift = Vec3d.of(facing.getVector()).multiply(0.0625);
                    matrices.translate(shift.x, shift.y, shift.z);
                }
            }
        }
    }
}
