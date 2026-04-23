package net.smiech.cryptidologica.entity.goals.bigfootGoals;

import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.smiech.cryptidologica.entity.custom.BigfootEntity;

public class BigfootRangedAttackGoal extends RangedAttackGoal {


    private final BigfootEntity entity;
    private int attackDelay = 0;
    private int ticksUntilNextAttack;
    private boolean shouldAttack = true;

    public BigfootRangedAttackGoal(RangedAttackMob pMob, double pSpeedModifier, int pAttackInterval, float pAttackRadius) {
        super(pMob, pSpeedModifier, pAttackInterval, pAttackRadius);
        entity = ((BigfootEntity) pMob);
        this.ticksUntilNextAttack = pAttackInterval;
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
        return this.canUse() || (entity.getTarget() !=null) && this.entity.getTarget().isAlive() && !this.entity.getNavigation().isDone();
    }

    @Override
    public void tick() {
            //todo: Ogarnąć jak zrobić żeby TickUntilNextAttack szło w dół bez attack delay pójścia w dół, I wice vers
            // Myśle że się udało? wiecej testów trzeba...
            if(this.attackDelay==0 && this.ticksUntilNextAttack>-1){
                super.tick();
            }else{
                this.attackDelay= Math.max(this.attackDelay -1 ,0);
            }
            if (this.attackDelay<=0){
                this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack -1,-1);
            }

            if (this.ticksUntilNextAttack<0){
                this.attackDelay = 10;
            }
            if(this.attackDelay >0 && ticksUntilNextAttack<0){
            this.ticksUntilNextAttack=30;
            }
            System.out.println("AttackDelay " + this.attackDelay);
            System.out.println("TicksUntilNextAttack "  + this.ticksUntilNextAttack);
    }

    @Override
    public void start() {
        this.ticksUntilNextAttack=30;
        this.attackDelay=0;
        entity.setRangedAttacking(true);
        super.start();
    }


    @Override
    public void stop() {
        entity.setRangedAttacking(false);
        super.stop();
    }

}
