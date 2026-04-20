package net.smiech.cryptidologica.entity.goals.bigfootGoals;


import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.smiech.cryptidologica.entity.custom.BigfootEntity;

import java.util.EnumSet;


public class BigfootHideGoal extends Goal {
//todo:
// -make tick() check if block is found and bigfoot is near it's location if not move to it, if a block is found and a player is nearby flush it
// and then the searching process can restart
// -Change the leaf detection to make it more reliable and to allow for some leeway for placement (like blocks missing or have 1 trapdoor
    private final int playerDetectRange;
    protected final PathfinderMob mob;
    protected boolean reachedTarget = false;
    protected boolean blockFound = false;
    protected BlockPos blockPos;
    protected Player target;
    protected Vec3 vectorToHide;
    protected static int timeToRun = 0;

    public BigfootHideGoal(PathfinderMob mob, int pPlayerDetectRange) {
        this.playerDetectRange = pPlayerDetectRange;
        this.mob = mob;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP));
    }


    public void hasReachedTarget(boolean setReach){this.reachedTarget = setReach;}

    public boolean isBlockFound(){return  blockFound;}

    public void hasBlockFound(boolean setFound){this.blockFound = setFound;}



//Repeate moving to goal until reached target is true, do this in can use, with found block and hasn't reached target it will run the move behind
    //and also a check to see if the block hasn't been altered

    //looks for wanted block searching for it in a "growing patter, 1 to each side of the last searched block
    // then checks if the block is a tree, sets hasBlockFound to true and then returns true otherwise false
    protected boolean findTreeRoot(PathfinderMob pMob, int hDistance, int vDistance, int playerToBlockDistance){
       BlockPos mobPosition = pMob.blockPosition();
       int blockVerticalSearch = hDistance;
       int blockHorizontalSearch = vDistance;
       BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

        for(int $$1 = 0; $$1 <= blockVerticalSearch; $$1 = $$1 > 0 ? -$$1 : 1 - $$1) {
            for(int $$2 = 0; $$2 < blockHorizontalSearch; ++$$2) {
                for(int $$3 = 0; $$3 <= $$2; $$3 = $$3 > 0 ? -$$3 : 1 - $$3) {
                    for(int $$4 = $$3 < $$2 && $$3 > -$$2 ? $$2 : 0; $$4 <= $$2; $$4 = $$4 > 0 ? -$$4 : 1 - $$4) {
                        mutableBlockPos.setWithOffset(mobPosition, $$3, $$1 - 1, $$4);
                        if (this.mob.isWithinRestriction(mutableBlockPos)
                                && this.isTree(this.mob.level(), mutableBlockPos, playerToBlockDistance))
                        {
                            System.out.println("FindTreeRoot blockpos setter ");
                            hasBlockFound(true);
                            timeToRun = 0;
                            ((BigfootEntity) pMob).setFleeing(false);
                            return true;
                        }
                    }
                }
            }
        }
        runToRandomSpot(5,5);
        return false;
    }
    //change the player detection, so bigfoot doesn't freeze if people are too close
    //scans for blocks around the found wanted block. Looking if there's empty space the entity could hide behind it
    private boolean isTree(Level pLevel, BlockPos pPos, int playerToBlockDistance) {
            BlockState currentBlock = pLevel.getBlockState(pPos);
            if (currentBlock.is(BlockTags.LOGS) && (returnPlayer(playerToBlockDistance).distanceToSqr(pPos.getCenter()) > 12*12)){
                BlockPos.MutableBlockPos rootBlockPos = new BlockPos.MutableBlockPos();
                rootBlockPos.set(pPos);
                int distanceForSearch = 5;
                //Algorithm for checking if this block is actually a tree or a freestanding WoodBlock
                //Goes down from the found block down until it reaches Dirt
                while(pLevel.getBlockState(rootBlockPos).is(BlockTags.LOGS) && distanceForSearch > 0){
                    --distanceForSearch;
                    rootBlockPos.set(rootBlockPos.getX(), rootBlockPos.getY()-1,rootBlockPos.getZ());
                    if (pLevel.getBlockState(rootBlockPos).is(BlockTags.DIRT)){
                        //Then back up a 3x3. the middle is already known to be Preffered block
                        for (int i = 0 ; i < 3 ;i++){
                            for (int j = 0 ; j < 3 ;j++){
                                for (int k = 0 ; k < 3 ;k++){
                                    BlockPos.MutableBlockPos treeCheckerBlockPos = new BlockPos.MutableBlockPos(rootBlockPos.getX()-1 + j,rootBlockPos.above().getY()+ i,rootBlockPos.getZ()-1 + k);
                                    //Checking if the surrounding area is Air or blocks that can be walked through
                                    if(k !=1 && j != 1
                                            && !(pLevel.getBlockState(treeCheckerBlockPos).isAir()
                                                ||
                                                (pLevel.getBlockState(treeCheckerBlockPos).is(BlockTags.SWORD_EFFICIENT))
                                                ||
                                                (pLevel.getBlockState(treeCheckerBlockPos).is(BlockTags.LEAVES))
                                                )) {
                                        return false;
                                    }else if (k==1 && j==1 && !(pLevel.getBlockState(treeCheckerBlockPos).is(BlockTags.LOGS))){
                                        return false;
                                    }
                                }
                            }
                        }
                        System.out.println("IsTree true");
                        this.blockPos = rootBlockPos.above();
                        return true;
                    }
                }
            }
        return false;
    }

    //Add this next update
    protected void runToRandomSpot(int lookForPlayerRange, int playerToRandomSpot){
        Vec3 randomSpot = DefaultRandomPos.getPos(this.mob, 20, 7);
        if(randomSpot != null && returnPlayer(lookForPlayerRange).distanceToSqr(randomSpot)>playerToRandomSpot) {
            this.mob.getNavigation().moveTo(randomSpot.x,randomSpot.y, randomSpot.z, 1.7);
        }
        //this will activate when there's no trees, bigfoot will run around trying to find something and then
        // if nothing is found after a bit will open an interdimensional portal and leave
    }

    //When the player walks out of the leaves, it's stuck in a loop until the goal ends

    //checks if the player is surrounded by a 3x3 leaf "coffin".
    //Returns false if the player isn't in the leaf coffin, is in creative or is crouching
    //It has to return a false to work because I'm bad and it hasn't been implemented in a clear way
    protected boolean isPlayerInleaves(Player pPlayer) {
        if (pPlayer !=null) {
            BlockPos leafCheckPlayerPos = pPlayer.blockPosition();
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    for (int k = 0; k < 3; k++) {
                        BlockPos.MutableBlockPos leafCheckerBlockPos = new BlockPos.MutableBlockPos(leafCheckPlayerPos.getX() - 1 + j,
                                leafCheckPlayerPos.above().getY()-1 + i,
                                leafCheckPlayerPos.getZ() - 1 + k);
                        System.out.println(leafCheckerBlockPos.toShortString() + " = " + pPlayer.level().getBlockState(leafCheckerBlockPos) +  " = Player Coords " + pPlayer.blockPosition());
                        if (k != 1 && j != 1
                                && !((pPlayer.level().getBlockState(leafCheckerBlockPos).is(BlockTags.LEAVES))
                              && pPlayer.isCrouching())
                               && !(pPlayer.isCreative())
                        ) {
                            return false;
                        }
                    }
                }
            }System.out.println("Hopefully this is after 9 blocks");
            return true;
        }
        return false;
    }

    //detects if there is a player in specified range that isn't inside of the leaves
    protected boolean detectPlayerInRange(int distToPlayer, int distFromMob){
        if (returnPlayer(distToPlayer).distanceToSqr(this.mob) < distFromMob*distFromMob){
           return !isPlayerInleaves(returnPlayer(distToPlayer));
        }
        return false;
    }

    //increase speed incrementally
    //Moves bigfoot behind a tree based on player position so he is (most of the time) hidding behind a tree
    // then have him move there
    protected void moveMobBehindTree(){
        Player targetPlayer = returnPlayer(playerDetectRange);
        Vec3 blockCenter = this.blockPos.above().getCenter();
        Vec3 directionBetween = targetPlayer.position().subtract(blockCenter).normalize();

        this.vectorToHide= blockCenter.subtract(directionBetween.scale(1.2));
        System.out.println(blockPos + " vector ; " + vectorToHide);
        System.out.println("MoveBehindTree start");
        this.mob.getNavigation().moveTo(
                vectorToHide.x, vectorToHide.y, vectorToHide.z, 1.5);
        if (Math.random() > 0.7){
            this.mob.getLookControl().setLookAt(targetPlayer.getX(), targetPlayer.getY()+2, targetPlayer.getZ());
        }

    }

    private Player returnPlayer(int pDistance) {
    this.target = this.mob.level().getNearestPlayer(this.mob, pDistance*pDistance);
        return this.target;
    }

    //Player has to be within a certain distance of the mob, done
    //block can't be within a certain radius of player, Work one
    //the distance in canuse is also connected to Stop, so it doesn't stop until I'm out of the range

    public boolean canUse() {
        if(returnPlayer(playerDetectRange) != null){
            if(detectPlayerInRange(35,20) && !isBlockFound() ){
                return this.findTreeRoot(this.mob,20,20,5);
            }

        }
        timeToRun = 0;
        return false;
    }

    public boolean canContinueToUse() {
        return canUse();
    }


    public void start() {
        this.moveMobBehindTree();
    }

    public void stop() {
            timeToRun = 0;
       hasReachedTarget(false);
       hasBlockFound(false);
    }

    public boolean requiresUpdateEveryTick() {
        return true;
    }

    public void tick() {
    if (detectPlayerInRange(10,10) && this.mob instanceof BigfootEntity){
        if (this.mob.getTarget()==null){
            this.mob.setSpeed(1.75F);
            ((BigfootEntity) this.mob).setFleeing(true);
            System.out.println("XD");
            }
        }
    }
}