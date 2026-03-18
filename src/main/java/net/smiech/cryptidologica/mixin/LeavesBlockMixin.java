package net.smiech.cryptidologica.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.smiech.cryptidologica.entity.custom.BigfootEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(
        value = {LeavesBlock.class}
)
abstract class LeavesBlockMixin extends BlockBehaviourMixin{
    @Override
    protected VoxelShape overrideForLeavesBlock(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext, Operation<VoxelShape> original) {
        if (pContext instanceof EntityCollisionContext entityCollisionContext){
            Entity entity = entityCollisionContext.getEntity();
            //if I jump it doesn't count as being above the pPos so that means I can't jump through it and ontop of another block
            //(also kinda stumps you from fully jumping if too close?)
            if (entity !=null && (entity instanceof Player || entity instanceof BigfootEntity)){
                if (entity.blockPosition().getY() <= pPos.getY()){
                    System.out.println(pPos + " Entity:" + entity.blockPosition());
                    return Shapes.empty();
                }
            }
        }
        return super.overrideForLeavesBlock(pState, pLevel, pPos, pContext, original);
    }
}
