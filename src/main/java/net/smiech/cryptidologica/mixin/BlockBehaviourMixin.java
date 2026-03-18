package net.smiech.cryptidologica.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(
        value = {BlockBehaviour.class}
)
abstract class BlockBehaviourMixin {
    @WrapMethod(method = "getCollisionShape")
    protected VoxelShape overrideForLeavesBlock(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext, Operation<VoxelShape> original){
        return original.call(pState,pLevel,pPos,pContext);
    }
}
