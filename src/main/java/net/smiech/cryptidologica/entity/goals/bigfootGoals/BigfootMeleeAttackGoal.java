package net.smiech.cryptidologica.entity.goals.bigfootGoals;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
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
        if((entity.getTarget() !=null) && (entity.distanceToSqr(entity.getTarget()) < 49)) {
            return super.canUse();
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        if((entity.getTarget() !=null) && (entity.distanceToSqr(entity.getTarget()) < 49)) {
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

    @Override
    protected void checkAndPerformAttack(LivingEntity pEnemy, double pDistToEnemySqr) {
//        if (isEnemyWithinAttackDistance(pEnemy,pDistToEnemySqr)){
//            shouldCountTillNextAttack = true;
//
//            if(isTimeToStartAttackAnimation()){
//                entity.setAttacking(true);
//            }
//            if(isTimeToAttack()){
//                this.mob.getLookControl().setLookAt(pEnemy.getX(),pEnemy.getY(),pEnemy.getZ());
//                this.mob.swing(InteractionHand.MAIN_HAND);
//                this.mob.doHurtTarget(pEnemy);
//            }
//        }else {
//            resetAttackCooldown();
//            shouldCountTillNextAttack = false;
//            entity.setAttacking(false);
//            entity.attackAnimationTimeout = 0;
//        }
        super.checkAndPerformAttack(pEnemy,pDistToEnemySqr);
    }

    private boolean isEnemyWithinAttackDistance(LivingEntity pEnemy, double pDistToEnemySqr){
        return pDistToEnemySqr <= this.getAttackReachSqr(pEnemy);
    }

    protected boolean isTimeToStartAttackAnimation() {
        return this.ticksUntilNextAttack <= attackDelay;
    }

    protected void resetAttackCooldown() {
        this.ticksUntilNextAttack = this.adjustedTickDelay(attackDelay*2);
    }

    protected boolean isTimeToAttack() {
        return this.ticksUntilNextAttack <= 0;
    }

    protected int getTicksUntilNextAttack() {
        return this.ticksUntilNextAttack;
    }

    protected int getAttackInterval() {
        return this.adjustedTickDelay(20);
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
}
