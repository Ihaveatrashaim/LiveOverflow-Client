package net.trashaim.client.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SideShapeType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.trashaim.client.Client;
import net.trashaim.client.mods.impl.render.Xray;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.AbstractBlockState.class)
public abstract class MixinBlockRenderer {

    @Shadow public abstract Block getBlock();

    @Inject(at = @At("HEAD"), method = "getLuminance", cancellable = true)
    public void getLuminance(CallbackInfoReturnable<Integer> info) {
        Xray xray = (Xray) Client.instance.moduleManager.getModule(Xray.class);
        if(xray.isToggle() && xray.blocks.contains(this.getBlock())) {
            info.setReturnValue(12);
            return;
        }
    }

    @Inject(at = @At("HEAD"), method = "getAmbientOcclusionLightLevel", cancellable = true)
    public void getAmbientOcclusionLightLevel(BlockView world, BlockPos pos, CallbackInfoReturnable<Float> info) {
        Xray xray = (Xray) Client.instance.moduleManager.getModule(Xray.class);
        if(xray.isToggle() && xray.blocks.contains(this.getBlock())) {
            info.setReturnValue(1.0f);
            return;
        }
    }

    @Inject(at = @At("HEAD"), method = "isSideInvisible", cancellable = true)
    public void isSideInvisible(BlockState state, Direction direction, CallbackInfoReturnable<Boolean> info) {
        Xray xray = (Xray) Client.instance.moduleManager.getModule(Xray.class);
        if(xray.isToggle()) {
            if(xray.blocks.contains(this.getBlock())) {
                info.setReturnValue(false);
                return;
            }
            
            info.setReturnValue(true);
            return;
        }
    }

    @Inject(at = @At("HEAD"), method = "isSideSolid", cancellable = true)
    public void isSideSolid(BlockView world, BlockPos pos, Direction direction, SideShapeType shapeType, CallbackInfoReturnable<Boolean> info) {
        Xray xray = (Xray) Client.instance.moduleManager.getModule(Xray.class);
        if(xray.isToggle()) {
            if(xray.blocks.contains(this.getBlock())) {
                info.setReturnValue(true);
                return;
            }
            
            info.setReturnValue(false);
            return;
        }
    }

    @Inject(at = @At("HEAD"), method = "getCullingFace", cancellable = true)
    public void getCullingFace(BlockView world, BlockPos pos, Direction direction, CallbackInfoReturnable<VoxelShape> info) {
        Xray xray = (Xray) Client.instance.moduleManager.getModule(Xray.class);
        if (xray.isToggle()) {
            if (xray.blocks.contains(this.getBlock())) {
                info.setReturnValue(VoxelShapes.fullCube());
                return;
            }
        }
        
        info.setReturnValue(VoxelShapes.empty());
        return;
    }

}
