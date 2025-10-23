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

    @Override
    public void start() {
        super.start();
        attackDelay = 40;
        ticksUntilNextAttack = 40;
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity pEnemy, double pDistToEnemySqr) {
        if (isEnemyWithinAttackDistance(pEnemy,pDistToEnemySqr)){
            shouldCountTillNextAttack = true;

            if(isTimeToStartAttackAnimation()){
                entity.setAttacking(true);
            }
            if(isTimeToAttack()){
                this.mob.getLookControl().setLookAt(pEnemy.getX(),pEnemy.getY(),pEnemy.getZ());
                this.mob.swing(InteractionHand.MAIN_HAND);
                this.mob.doHurtTarget(pEnemy);
            }
        }else {
            resetAttackCooldown();
            shouldCountTillNextAttack = false;
            entity.setAttacking(false);
            entity.attackAnimationTimeout = 0;
        }
    }

    private boolean isEnemyWithinAttackDistance(LivingEntity pEnemy, double pDistToEnemySqr){
        return pDistToEnemySqr <=this.getAttackReachSqr(pEnemy);
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
