package net.smiech.cryptidologica.entity.pathfinding;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;

public class BigfootCustomNavigation extends GroundPathNavigation {
    public BigfootCustomNavigation(Mob pMob, Level pLevel) {
        super(pMob, pLevel);
    }
    protected PathFinder createPathFinder(int maxVisitNode){
        this.nodeEvaluator = new CustomNodeEvaluator();
        return new PathFinder(this.nodeEvaluator, maxVisitNode);
    }
}
class CustomNodeEvaluator extends WalkNodeEvaluator{
    protected BlockPathTypes evaluateBlockPathType(BlockGetter pBlockGetter, BlockPos pBlockPos, BlockPathTypes pPathTypes){
        return pPathTypes == BlockPathTypes.LEAVES ? BlockPathTypes.OPEN : super.evaluateBlockPathType(pBlockGetter,pBlockPos, pPathTypes);
    }
}
