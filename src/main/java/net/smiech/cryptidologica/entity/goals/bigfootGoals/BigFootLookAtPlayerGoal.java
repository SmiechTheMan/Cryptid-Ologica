package net.smiech.cryptidologica.entity.goals.bigfootGoals;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.player.Player;

public class BigFootLookAtPlayerGoal extends LookAtPlayerGoal {
    public BigFootLookAtPlayerGoal(Mob pMob, Class<? extends LivingEntity> pLookAtType, float pLookDistance, float pProbability) {
        super(pMob, pLookAtType, pLookDistance, pProbability);
    }

    protected boolean isEntityInleaves(LivingEntity pPlayer) {
        if (pPlayer !=null) {
            BlockPos leafCheckPlayerPos = pPlayer.blockPosition();
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    for (int k = 0; k < 3; k++) {
                        BlockPos.MutableBlockPos leafCheckerBlockPos = new BlockPos.MutableBlockPos(leafCheckPlayerPos.getX() - 1 + j,
                                leafCheckPlayerPos.above().getY() + i,
                                leafCheckPlayerPos.getZ() - 1 + k);
                        if (k != 1 && j != 1
                                && (pPlayer.level().getBlockState(leafCheckerBlockPos).is(BlockTags.LEAVES))
                                && pPlayer.isCrouching()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean canUse() {
        if(!isEntityInleaves(this.mob.getTarget())){
            return false;
        }
        if (this.mob.getRandom().nextFloat() >= this.probability) {
            return false;
        } else {
            if (this.mob.getTarget() != null) {
                this.lookAt = this.mob.getTarget();
            }

            if (this.lookAtType == Player.class) {
                this.lookAt = this.mob.level().getNearestPlayer(this.lookAtContext, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
            } else {
                this.lookAt = this.mob.level().getNearestEntity(this.mob.level().getEntitiesOfClass(this.lookAtType, this.mob.getBoundingBox().inflate((double)this.lookDistance, (double)3.0F, (double)this.lookDistance), (p_148124_) -> true), this.lookAtContext, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
            }

            return this.lookAt != null;
        }
    }


    @Override
    public boolean isInterruptable() {
        return true;
    }
}
