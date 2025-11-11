package net.smiech.cryptidologica.entity.goals.bigfootGoals;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.level.pathfinder.Path;
import net.smiech.cryptidologica.entity.custom.BigfootEntity;

public class BigfootMeleeAttackGoal extends MeleeAttackGoal {

    private final BigfootEntity entity;
    private int attackDelay = 40;
    private int ticksUntilNextAttack = 40;
    private boolean shouldCountTillNextAttack = false;


    public BigfootMeleeAttackGoal(PathfinderMob pMob, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {
        super(pMob, pSpeedModifier, pFollowingTargetEvenIfNotSeen);
        entity = ((BigfootEntity) pMob);
    }

    //If the target is in range go for them, if not it should activate the other attack goal.
    //Not everytime tho otherwise the foot will just stand there and throw rocks all the time
    //so give it like 75% of a chance to throw rock and the other to move forward
    // also add this check to canContinueToUse
    @Override
    public boolean canUse() {
        if((entity.getTarget() !=null) && (entity.distanceToSqr(entity.getTarget()) < 36)){
                return super.canUse();
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        if((entity.getTarget() !=null) && (entity.distanceToSqr(entity.getTarget()) < 36)) {
            return super.canContinueToUse();
        }
        return false;

    }

    @Override
    public void start() {
        super.start();
        attackDelay = 40;
        ticksUntilNextAttack = 40;
    }


    //This works well enough for now
    // this.mob.getNavigation().getPath().canReach() seems like a good way to induce the ranged attack;
    @Override
    protected void checkAndPerformAttack(LivingEntity pEnemy, double pDistToEnemySqr) {
        super.checkAndPerformAttack(pEnemy,pDistToEnemySqr);
    }

    @Override
    public void tick() {
        super.tick();
        if (shouldCountTillNextAttack){
            this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
        }
    }

    @Override
    public void stop() {
        entity.setAttacking(false);
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
