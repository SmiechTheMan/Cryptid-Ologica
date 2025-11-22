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

    //If the target is in range go for them, if not it should activate the other attack goal.
    //Not everytime tho otherwise the foot will just stand there and throw rocks all the time
    //so give it like 75% of a chance to throw rock and the other to move forward
    // also add this check to canContinueToUse
    @Override
    public boolean canUse() {
        if((entity.getTarget() !=null) && this.entity.getNavigation().createPath(entity.getTarget(),
                1)!=null && this.entity.getNavigation().createPath(entity.getTarget(),1).canReach()){
                    System.out.println("CanUse ReachVar Status:" + this.entity.getNavigation().createPath(entity.getTarget(),3).canReach());
                    return super.canUse();
        }
        return false;
    }
    //CanUse but with a check to see if the entity can reach (PathFind) it's target
    //(entity.getTarget() !=null) && (entity.distanceToSqr(entity.getTarget()) < 36)
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


    //This works well enough for now
    // this.mob.getNavigation().getPath().canReach() seems like a good way to induce the ranged attack;
    @Override
    protected void checkAndPerformAttack(LivingEntity pEnemy, double pDistToEnemySqr) {
        super.checkAndPerformAttack(pEnemy,pDistToEnemySqr);
    }

    @Override
    public void tick() {
        if ((entity.getTarget() !=null) && this.entity.getNavigation().createPath(entity.getTarget(),1)!=null){
           System.out.println("Tick creating path: "+ this.entity.getNavigation().createPath(entity.getTarget(),3).canReach());
            System.out.println("Tick path Length: "+ this.entity.getNavigation().createPath(entity.getTarget(),3));
        }
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
