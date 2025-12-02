package net.smiech.cryptidologica.entity.goals.bigfootGoals;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.smiech.cryptidologica.entity.custom.BigfootEntity;

public class BigfootMeleeAttackGoal extends MeleeAttackGoal {

    private final BigfootEntity entity;

    public BigfootMeleeAttackGoal(PathfinderMob pMob, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {
        super(pMob, pSpeedModifier, pFollowingTargetEvenIfNotSeen);
        entity = ((BigfootEntity) pMob);
    }

    //Checks if target is under the required range or if a (created) path to the target allows the entity to reach it
    @Override
    public boolean canUse() {
        if((entity.getTarget() !=null) && this.entity.getNavigation().createPath(entity.getTarget(),
                1)!=null && this.entity.getNavigation().createPath(entity.getTarget(),1).canReach()){
                    System.out.println("CanUse ReachVar Status:" + this.entity.getNavigation().createPath(entity.getTarget(),3).canReach());
                    return super.canUse();
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        if((entity.getTarget() !=null) && this.entity.getNavigation().createPath(entity.getTarget(),
                1)!=null && this.entity.getNavigation().createPath(entity.getTarget(),1).canReach()) {
            System.out.println("cCtU Reach Status: " + this.entity.getNavigation().createPath(entity.getTarget(),1).canReach());
            return super.canContinueToUse();
        }
        return false;

    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity pEnemy, double pDistToEnemySqr) {
        super.checkAndPerformAttack(pEnemy,pDistToEnemySqr);
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void stop() {
        super.stop();
    }

    @Override
    protected void resetAttackCooldown() {
        super.resetAttackCooldown();
    }

    @Override
    protected double getAttackReachSqr(LivingEntity pAttackTarget) {
        return (this.mob.getBbWidth() * 3.0F * this.mob.getBbWidth() * 3.0F + pAttackTarget.getBbWidth());
    }
}
