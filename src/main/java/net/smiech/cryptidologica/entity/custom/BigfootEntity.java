package net.smiech.cryptidologica.entity.custom;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.smiech.cryptidologica.entity.goals.bigfootGoals.BigFootLookAtPlayerGoal;
import net.smiech.cryptidologica.entity.goals.bigfootGoals.BigfootHideGoal;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

public class BigfootEntity extends PathfinderMob implements GeoEntity {

    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    public BigfootEntity(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));

        this.goalSelector.addGoal(1, new WaterAvoidingRandomStrollGoal(this,1.1D));
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 0.5));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(2, new BigFootLookAtPlayerGoal(this, Player.class, 25f,1f));
        this.goalSelector.addGoal(0, new BigfootHideGoal(this,20));

    }

    public static AttributeSupplier.Builder createAttributes(){
        return PathfinderMob.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 2D)
                .add(Attributes.MOVEMENT_SPEED, 0.22)
                .add(Attributes.ARMOR_TOUGHNESS, 0.1f)
                .add(Attributes.ATTACK_DAMAGE, 2f)
                .add(Attributes.ATTACK_KNOCKBACK, 0.5f)
                .add(Attributes.FOLLOW_RANGE, 32f);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<GeoAnimatable>(this,"controller",0,this::predicate));
    }

    private PlayState predicate(AnimationState<GeoAnimatable> geoAnimatableAnimationState) {
        if(geoAnimatableAnimationState.isMoving()){
            geoAnimatableAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.bigfoot.walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
    geoAnimatableAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.bigfoot.idle",Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}


