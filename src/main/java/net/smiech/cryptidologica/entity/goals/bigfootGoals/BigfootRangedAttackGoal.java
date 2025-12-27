package net.smiech.cryptidologica.entity.goals.bigfootGoals;

import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.smiech.cryptidologica.entity.custom.BigfootEntity;

public class BigfootRangedAttackGoal extends RangedAttackGoal {

    private final BigfootEntity entity;

    public BigfootRangedAttackGoal(RangedAttackMob pMob, double pSpeedModifier, int pAttackInterval, float pAttackRadius) {
        super(pMob, pSpeedModifier, pAttackInterval, pAttackRadius);
        entity = ((BigfootEntity) pMob);
    }
    //Checks if target is over the required range or if a (created) path to the target doesn't allow the entity to reach it
    @Override
    public boolean canUse() {
        if((entity.getTarget() !=null) && (entity.distanceToSqr(entity.getTarget()) > 36)
                || ((entity.getTarget() !=null) && this.entity.getNavigation().createPath(entity.getTarget(), 1)!=null
                && !this.entity.getNavigation().createPath(entity.getTarget(),1).canReach())){
            return super.canUse();
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return this.canUse() || this.entity.getTarget().isAlive() && !this.entity.getNavigation().isDone();
    }

    @Override
    public void tick() { super.tick(); }

    @Override
    public void start() { super.start(); }

    @Override
    public void stop() {
        super.stop();
    }

}
