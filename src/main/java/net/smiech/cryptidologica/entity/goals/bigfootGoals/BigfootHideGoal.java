package net.smiech.cryptidologica.entity.goals.bigfootGoals;


import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;


public class BigfootHideGoal extends Goal {

    private int tickingSpeed;
    protected final PathfinderMob mob;
    protected boolean reachedTarget = false;
    protected boolean blockFound = false;
    protected BlockPos blockPos;
    protected Player target;
    protected Vec3 vectorToHide;


    public BigfootHideGoal(PathfinderMob mob, int tickingSpeed) {
        this.tickingSpeed = tickingSpeed;
        this.mob = mob;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP));
    }


    public boolean isReachedTarget(){ return reachedTarget;}

    public void hasReachedTarget(boolean setReach){this.reachedTarget = setReach;}

    public boolean isBlockFound(){return  blockFound;}

    public void hasBlockFound(boolean setFound){this.blockFound = setFound;}



//Repeate moving to goal until reached target is true, do this in can use, with found block and hasn't reached target it will run the move behind
    //and also a check to see if the block hasn't been altered


    protected boolean findTreeRoot(PathfinderMob pMob){
       BlockPos mobPosition = pMob.blockPosition();
       int blockVerticalSearch = 10;
       int blockHorizontalSearch = 10;
       BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

        for(int $$1 = 0; $$1 <= blockVerticalSearch; $$1 = $$1 > 0 ? -$$1 : 1 - $$1) {
            for(int $$2 = 0; $$2 < blockHorizontalSearch; ++$$2) {
                for(int $$3 = 0; $$3 <= $$2; $$3 = $$3 > 0 ? -$$3 : 1 - $$3) {
                    for(int $$4 = $$3 < $$2 && $$3 > -$$2 ? $$2 : 0; $$4 <= $$2; $$4 = $$4 > 0 ? -$$4 : 1 - $$4) {
                        mutableBlockPos.setWithOffset(mobPosition, $$3, $$1 - 1, $$4);
                        if (this.mob.isWithinRestriction(mutableBlockPos)
                                && this.isTree(this.mob.level(), mutableBlockPos))
                        {
                            System.out.println("FindTreeRoot blockpos setter ");
                            hasBlockFound(true);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean isTree(Level pLevel, BlockPos pPos) {
//
            BlockState currentBlock = pLevel.getBlockState(pPos);
            if (currentBlock.is(BlockTags.LOGS) && (returnPlayer().distanceToSqr(pPos.getCenter()) > 17*17)){
                // && (returnPlayer().distanceToSqr(pPos.getCenter()) > 16f)
                //The distance requirement just makes it not work, instead of moving to a different block :(
                BlockPos.MutableBlockPos rootBlockPos = new BlockPos.MutableBlockPos();
                rootBlockPos.set(pPos);
                int distanceForSearch = 5;
                //Algorithim for checking if this block is actually a tree or a freestanding WoodBlock
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
                                                )
                                        ) {
                                        return false;
                                    }else if (k==1 && j==1 && !(pLevel.getBlockState(treeCheckerBlockPos).is(BlockTags.LOGS))){
                                        return false;
                                    }
                                }
                            }
                        }


                        this.blockPos = rootBlockPos.above();
                        return true;
                    }
                }
            }

        return false;
    }

    protected void moveMobBehindTree(){
        Vec3 blockCenter = this.blockPos.above().getCenter();
        Vec3 directionBetween = returnPlayer().position().subtract(blockCenter).normalize();
        this.vectorToHide= blockCenter.subtract(directionBetween.scale(1.2));
        System.out.println(blockPos + " vector ; " + vectorToHide);
        System.out.println("MoveBehindTree start");
        this.mob.getNavigation().moveTo(
                vectorToHide.x, vectorToHide.y, vectorToHide.z, 1.35);

    }
    protected void runToRandomSpot(){
        //this will activate when there's no trees, bigfoot will run around trying to find something and then
        // if nothing is found after a bit will open an interdimensional portal and leave
    }

    protected boolean isPlayerInleaves(Player pPlayer) {
        if (pPlayer !=null) {
            BlockPos leafCheckPlayerPos = pPlayer.blockPosition();
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    for (int k = 0; k < 3; k++) {
                        BlockPos.MutableBlockPos leafCheckerBlockPos = new BlockPos.MutableBlockPos(leafCheckPlayerPos.getX() - 1 + j,
                                leafCheckPlayerPos.above().getY() + i,
                                leafCheckPlayerPos.getZ() - 1 + k);
                        if (k != 1 && j != 1
                                && (pPlayer.level().getBlockState(leafCheckerBlockPos).is(BlockTags.LEAVES))
                                && pPlayer.isCrouching()
                                && (pPlayer.isCreative())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    protected boolean detectPlayerInRange(){
        if (returnPlayer().distanceToSqr(this.mob) < 17*17){
           return !isPlayerInleaves(returnPlayer());
        }
        return false;
    }

    private Player returnPlayer() {
        this.target = this.mob.level().getNearestPlayer(this.mob, 400);
        return this.target;
    }

    protected void sendChatMessage(String msng){
        Player localTarget = returnPlayer();
        localTarget.sendSystemMessage(Component.literal(msng));
    }

    private void resetTick(int change) {
        tickingSpeed=change;
    }

    private void decreaseTickingSpeed(int minus) {
        tickingSpeed = tickingSpeed-minus;
    }

    //Player has to be within a certain distance of the mob, done
    //block can't be within a certain radius of player, Work one
    //the distance in canuse is also connected to Stop, so it doesn't stop until I'm out of the range

    public boolean canUse() {
        if(returnPlayer() != null){
            if(detectPlayerInRange() && !isBlockFound() ){
                return this.findTreeRoot(this.mob);

            }
        }

        return false;
    }

    public boolean canContinueToUse() {
        //            sendChatMessage("ContinuetoUse");
        return !isReachedTarget() && detectPlayerInRange();
//        sendChatMessage("Can't !ContinuetoUse");
//Currently stops, But Doesn't relaunch unless the player exits the minimum range after it does a different goal?
    }

//    public boolean isInterruptable() {
////        System.out.println("IsInterruptable");
//        return true;
//    }

    public void start() {
//        sendChatMessage("Start");
        this.moveMobBehindTree();


    }

    public void stop() {
//        sendChatMessage("stop");
       hasReachedTarget(false);
       hasBlockFound(false);
    }

    public boolean requiresUpdateEveryTick() {
        return true;
    }

    public void tick() {

        decreaseTickingSpeed(1);
        if(tickingSpeed<1){resetTick(20);}



        if(!reachedTarget && (tickingSpeed%4==0) && blockFound){
          this.moveMobBehindTree();
//          sendChatMessage("Tick Movebhindtree");
        }
        if(!reachedTarget && (tickingSpeed%5==0) && detectPlayerInRange()){
            hasBlockFound(false);
            this.findTreeRoot(this.mob);
//            sendChatMessage("Too close!!");
        }

        if(this.mob.blockPosition().closerToCenterThan(vectorToHide,1)){
//            System.out.println("Is above block");
        }
        if(tickingSpeed%10==0){
        System.out.println("BlockFound: " + isBlockFound() + ":: ReachedTarget: " + isReachedTarget());
        }
    }
}