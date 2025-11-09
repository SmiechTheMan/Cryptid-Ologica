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

    @Override
    public boolean canUse() {
        if((entity.getTarget() !=null) && (entity.distanceToSqr(entity.getTarget()) > 49)) {
            return super.canUse();
        }
        return false;
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public boolean canContinueToUse() {
        if((entity.getTarget() !=null) && (entity.distanceToSqr(entity.getTarget()) > 49)) {
            return super.canUse();
        }
        return false;
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void stop() {
        super.stop();
    }

}
