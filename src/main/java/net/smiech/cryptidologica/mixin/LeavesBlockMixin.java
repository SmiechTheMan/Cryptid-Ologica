package net.smiech.cryptidologica.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.smiech.cryptidologica.entity.custom.BigfootEntity;
import net.smiech.cryptidologica.item.ModItems;
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
            //Bigfoot will now traverse the leaves, Will think if he should keep doing that
            if(entity !=null){
                if (entity instanceof BigfootEntity){
                    if (entity.blockPosition().getY() <= pPos.getY()){
                        return Shapes.empty();
                    }
                }
                if (entity instanceof Player && ((Player) entity).getInventory().getArmor(2).getItem().equals(ModItems.BIGFOOT_GHILLIE.get())){
                    if (entity.blockPosition().getY() <= pPos.getY() ){ return Shapes.empty(); }
                    if (entity.isCrouching()){ return Shapes.empty(); }
                }
            }
        }
        return super.overrideForLeavesBlock(pState, pLevel, pPos, pContext, original);
    }
}
