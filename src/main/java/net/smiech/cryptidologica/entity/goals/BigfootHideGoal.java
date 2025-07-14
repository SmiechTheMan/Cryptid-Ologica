package net.smiech.cryptidologica.entity.goals;


import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.EnumSet;
import java.util.LinkedList;
import java.util.logging.Logger;


public class BigfootHideGoal extends Goal {

    private int tickingSpeed;
    protected final PathfinderMob mob;
    protected boolean reachedTarget = false;
    protected boolean blockFound = false;
    protected BlockPos blockPos;
    protected Player target;

    public BigfootHideGoal(PathfinderMob mob, int tickingSpeed) {
        this.tickingSpeed = tickingSpeed;
        this.mob = mob;
//        this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP));
    }


    public boolean hasReachedTarget(){ return reachedTarget;}

    public void setReachedTarget(boolean setReach){this.reachedTarget = setReach;}

    public boolean isBlockFound(){return  blockFound;}

    public void hasBlockFound(boolean setFound){this.blockFound = setFound;}





    protected boolean findTreeRoot(PathfinderMob pMob){
       BlockPos mobPosition = pMob.blockPosition();
       int blockVerticalSearch = 7;
       int blockHorizontalSearch = 15;
       BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

       //this devilous loop zigzags from + to -. Like goddamn, that's smart but it might be fucking up

        for(int $$1 = 0; $$1 <= blockVerticalSearch; $$1 = $$1 > 0 ? -$$1 : 1 - $$1) {
            for(int $$2 = 0; $$2 < blockHorizontalSearch; ++$$2) {
                for(int $$3 = 0; $$3 <= $$2; $$3 = $$3 > 0 ? -$$3 : 1 - $$3) {
                    for(int $$4 = $$3 < $$2 && $$3 > -$$2 ? $$2 : 0; $$4 <= $$2; $$4 = $$4 > 0 ? -$$4 : 1 - $$4) {
                        mutableBlockPos.setWithOffset(mobPosition, $$3, $$1 - 1, $$4);
                        if (this.mob.isWithinRestriction(mutableBlockPos) && this.isTree(this.mob.level(), mutableBlockPos)) {
                            this.blockPos = mutableBlockPos;
                            return true;

                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean isTree(Level pLevel, BlockPos pPos) {

        //Algorithm for checking if this block is actually a tree or a freestanding WoodBlock
        //Loop to search in a 3x3 area that also checks if the 3 center blocks in the Y axis are of a certain type
        //adds them into an array to see if it counts as a hideable object

        LinkedList<BlockPos> blockPosLinkedList;
        if (!pLevel.isEmptyBlock(pPos.above())) {
            return false;
        } else {
            BlockState currentBlock = pLevel.getBlockState(pPos);
            if (currentBlock.is(BlockTags.BEACON_BASE_BLOCKS)){
                sendChatMessage("Starting Loop");
//                for (int y = 0; y < 3; y++){
//                    for (int x = 0; x < 3; x++){
//                        for (int z = 0; z < 3; z++){
//                        currentMBlockPos =  new mutableBlockPos(pPos.getX()-1 + x,pPos.getY() + y,pPos.getZ()-1 + z);
//
//                                    sendChatMessage(pLevel.getBlockState(currentBlockPos) + " " + currentBlockPos.toShortString());
//                        }
//                    }
//                }
                return true;
            }
        }
        return false;
    }

    protected void moveMobBehindTree(){
        //Compare the blockpos.above etc to where the player is, and the one furthest away will roughly be the hiding block or look at any run away from player goal
        //since those might show how to get the opposite direction run
        this.mob.getNavigation().moveTo(
            (double)((float)this.blockPos.getX()) -0.5,
            (double)(this.blockPos.getY() - 0.5),
            (double)((float)this.blockPos.getZ()) -0.5, 2);
    }

    protected void sendChatMessage(String msng){
        Player localTarget = returnTarget(this.mob);
            localTarget.sendSystemMessage(Component.literal(msng));
    }


    private Player returnTarget(PathfinderMob pMob) {
        this.target = pMob.level().getNearestPlayer(pMob, 100f);
        return this.target;
    }


    public boolean canUse() {
        if(this.mob.level().getNearestPlayer(this.mob, 200f) != null){
            if(this.mob.level().getNearestPlayer(this.mob, 200f).distanceToSqr(this.mob) < 30f && !isBlockFound()){
            return this.findTreeRoot(this.mob);
            }
        }
        return false;
    }

//    @Override
//    public boolean canContinueToUse() {
//        if (!hasReachedTarget()) {
//            return canUse();
//        }
//        return false;
//    }

    public boolean isInterruptable() {
        return true;
    }

    public void start() {
    sendChatMessage("Starting to move");

    this.moveMobBehindTree();
    sendChatMessage("moved");
    }

    public void stop() {
        sendChatMessage("From Stop beginning, block:" + isBlockFound());
        sendChatMessage("stop method used");
        sendChatMessage("From Stop end, block:" + isBlockFound());

    }

    public boolean requiresUpdateEveryTick() {
        return false;
    }
    int ticker = tickingSpeed;
    public void tick() {
        if (this.mob.position().distanceToSqr(blockPos.getCenter())>1){
            System.out.println("Yuh");
            hasBlockFound(true);
        }
         ticker = ticker+1;
         System.out.println(ticker);
         if (ticker == 20){
             sendChatMessage("distance player to mob " + returnTarget(this.mob).distanceToSqr(this.mob));
             sendChatMessage("distance player to mob " + returnTarget(this.mob).distanceToSqr(blockPos.getX(),blockPos.getY(),blockPos.getZ()));
             ticker = tickingSpeed;
         }
    }

        //change setReachedTarget so that it doesn't reset if it gets to the block, because it will keep repeating itself midwalk, so make it so it changes to
        //false after it has pathfound a bit away from the found block or if the player is further away, probably a combo of both

}

//notes: stops after 1 go, but should find a way to make it run around