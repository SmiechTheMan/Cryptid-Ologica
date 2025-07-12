package net.smiech.cryptidologica.entity.goals;


import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderSet;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fml.ISystemReportExtender;
import net.smiech.cryptidologica.entity.custom.BigfootEntity;

import java.util.EnumSet;
import java.util.LinkedList;


public class BigfootHideGoal extends Goal {

    private int tickingSpeed;
    protected final PathfinderMob mob;
    protected boolean reachedTarget = false;
    protected boolean blockFound = false;
    protected BlockPos blockPos;
    protected Player target;
    protected final Block blockToFind;

    public BigfootHideGoal(PathfinderMob mob, int tickingSpeed, Block pBlock) {
        this.tickingSpeed = tickingSpeed;
        this.mob = mob;
        this.blockToFind = pBlock;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP));
    }


    public boolean hasReachedTarget(){ return reachedTarget;}

    public void setReachedTarget(boolean setReach){this.reachedTarget = setReach;}

    public boolean isBlockFound(){return  blockFound;}

    public void hasBlockFound(boolean setFound){this.blockFound = setFound;}





    protected boolean findTreeRoot(PathfinderMob pMob,Block pBlocks){
       BlockPos mobPosition = pMob.blockPosition();
       int blockVerticalSearch = 7;
       int blockHorizontalSearch = 7;
       BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

        for(int $$1 = 0; $$1 <= blockVerticalSearch; $$1 = $$1 > 0 ? -$$1 : 1 - $$1) {
            for(int $$2 = 0; $$2 < blockHorizontalSearch; ++$$2) {
                for(int $$3 = 0; $$3 <= $$2; $$3 = $$3 > 0 ? -$$3 : 1 - $$3) {
                    for(int $$4 = $$3 < $$2 && $$3 > -$$2 ? $$2 : 0; $$4 <= $$2; $$4 = $$4 > 0 ? -$$4 : 1 - $$4) {
                        mutableBlockPos.setWithOffset(mobPosition, $$3, $$1 - 1, $$4);
                        if (this.mob.isWithinRestriction(mutableBlockPos) && this.isTree(this.mob.level(), mutableBlockPos, pBlocks)) {
                            this.blockPos = mutableBlockPos;
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean isTree(Level pLevel, BlockPos pPos, Block pBlocks) {
        LinkedList<BlockPos> scannedBlocks;
        if (!pLevel.isEmptyBlock(pPos.above())) {
            return false;
        } else {
            BlockState currentBlock = pLevel.getBlockState(pPos);
            if (currentBlock.is(Blocks.DIAMOND_BLOCK)){
                //Algorithim for checking if this block is actually a tree or a freestanding WoodBlock
                return true;
            }
        }
        return false;
    }

    protected void moveMobBehindTree(){
    this.mob.getNavigation().moveTo(
            (double)((float)this.blockPos.getX()) + 0.5,
            (double)(this.blockPos.getY() + 1),
            (double)((float)this.blockPos.getZ()) + 0.5, 1);
    }

    protected void projectPlayerCords( PathfinderMob pMob){
        Player localTarget = returnTarget(pMob);
        if (pMob.level().getNearestPlayer(pMob, 100f).distanceToSqr(pMob) < 50f){
        localTarget.sendSystemMessage(Component.literal("CurrentPlayerCords: " + localTarget.blockPosition().getX() +"x " +localTarget.blockPosition().getZ()+ "z"));
            localTarget.sendSystemMessage(Component.literal("CurrentFootCords: " + pMob.blockPosition().getX() +"x " +pMob.blockPosition().getZ()+ "z"));
        }
    }




    public boolean canUse() {
        if(this.mob.level().getNearestPlayer(this.mob, 100f) != null){
        if(this.mob.level().getNearestPlayer(this.mob, 100f).distanceToSqr(this.mob) < 10f && !reachedTarget){
            return this.findTreeRoot(this.mob, this.blockToFind);
            }
        }
        return false;
    }

    public boolean canContinueToUse() {
        return this.canUse();
    }

    public boolean isInterruptable() {
        return true;
    }

    public void start() {
    returnTarget(this.mob).sendSystemMessage(Component.literal("It has Started"));
    this.moveMobBehindTree();
    }

    public void stop() {
    }

    public boolean requiresUpdateEveryTick() {
        return false;
    }

    public void tick() {
        if(this.blockPos.above().closerToCenterThan(this.mob.position(),1)){
            setReachedTarget(true);
        }else{
            setReachedTarget(false);
        }
        if(tickingSpeed==20){projectPlayerCords(this.mob);}
        decreaseTickingSpeed(1);
        if(tickingSpeed<1){resetTick(20);}

    }

    private Player returnTarget(PathfinderMob pMob) {
        this.target = pMob.level().getNearestPlayer(pMob, 100f);
        return this.target;
    }

    private void resetTick(int change) {
        tickingSpeed=change;
    }

    private void decreaseTickingSpeed(int minus) {
        tickingSpeed = tickingSpeed-minus;
    }
}