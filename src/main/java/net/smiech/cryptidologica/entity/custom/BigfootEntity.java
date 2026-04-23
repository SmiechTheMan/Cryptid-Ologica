package net.smiech.cryptidologica.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.smiech.cryptidologica.entity.goals.bigfootGoals.BigFootLookAtPlayerGoal;
import net.smiech.cryptidologica.entity.goals.bigfootGoals.BigfootHideGoal;
import net.smiech.cryptidologica.entity.goals.bigfootGoals.BigfootMeleeAttackGoal;
import net.smiech.cryptidologica.entity.goals.bigfootGoals.BigfootRangedAttackGoal;
import net.smiech.cryptidologica.entity.pathfinding.BigfootCustomNavigation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

public class BigfootEntity extends PathfinderMob implements GeoEntity, RangedAttackMob {

    //TODO: Add a timer method to rangedAttack to make it less frequent

    private static final EntityDataAccessor<Boolean> RANGEATTACKING = SynchedEntityData.
            defineId(BigfootEntity.class, EntityDataSerializers.BOOLEAN);
    private static final  EntityDataAccessor<Boolean> FLEEING = SynchedEntityData.
            defineId(BigfootEntity.class,EntityDataSerializers.BOOLEAN);

    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    public BigfootEntity(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new BigfootMeleeAttackGoal(this, 1.7D,true));
        this.goalSelector.addGoal(1, new BigfootRangedAttackGoal(this,1F, 30, 7.0F));
        this.goalSelector.addGoal(1, new BigfootHideGoal(this,20));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this,1.1D));
        this.goalSelector.addGoal(2, new BigFootLookAtPlayerGoal(this, Player.class, 25f,1f));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(4, new RandomStrollGoal(this, 0.5));

        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));

    }

    public static AttributeSupplier.Builder createAttributes(){
        return PathfinderMob.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 7D)
                .add(Attributes.MOVEMENT_SPEED, 0.22)
                .add(Attributes.ARMOR_TOUGHNESS, 0.1f)
                .add(Attributes.ATTACK_DAMAGE, 2f)
                .add(Attributes.ATTACK_KNOCKBACK, 2.0f)
                .add(Attributes.FOLLOW_RANGE, 32f);
    }

    //some weird pathfinding where it can walk through leaves if there's a 45 degree angle otherwise it doesn't go through the leaf?
    @Override
    protected PathNavigation createNavigation(Level pLevel) {
        if (this instanceof BigfootEntity){
            return new BigfootCustomNavigation(this, this.level());
        }else {
            return super.createNavigation(pLevel);
        }
    }

    public static boolean canSpawn(EntityType<BigfootEntity> entityType, ServerLevelAccessor level,
                                   MobSpawnType spawnType, BlockPos position, RandomSource random)
    {
      return checkMobSpawnRules(entityType,level,spawnType,position,random);
    }

//ENTITY DATA

    public void setFleeing(boolean fleeing){
        this.entityData.set(FLEEING, fleeing);
    }

    public boolean isFleeing(){
        return this.entityData.get(FLEEING);
    }

    public void setRangedAttacking(boolean attacking){
        this.entityData.set(RANGEATTACKING, attacking);
    }

    public boolean isRangedAttacking(){
        return this.entityData.get(RANGEATTACKING);
    }


    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(RANGEATTACKING,false);
        this.entityData.define(FLEEING,false);
    }

    //ANIMATIONS AND SFX

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<GeoAnimatable>(this,"controller",0, this::predicate));
        controllerRegistrar.add(new AnimationController<GeoAnimatable>(this,"attackController",0, this::attackPredicate));
    }
    private PlayState attackPredicate(AnimationState<GeoAnimatable> geoAnimatableAnimationState) {
        if(this.swinging && geoAnimatableAnimationState.getController().getAnimationState().equals(AnimationController.State.STOPPED)){
            geoAnimatableAnimationState.resetCurrentAnimation();
            geoAnimatableAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.bigfoot.melee",Animation.LoopType.PLAY_ONCE));
            this.swinging = false;
        }
        if (this.isRangedAttacking() && geoAnimatableAnimationState.getController().getAnimationState().equals(AnimationController.State.STOPPED)){
            geoAnimatableAnimationState.resetCurrentAnimation();
            geoAnimatableAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.bigfoot.ranged",Animation.LoopType.PLAY_ONCE));

        }

        return PlayState.CONTINUE;
    }

    private PlayState predicate(AnimationState<GeoAnimatable> geoAnimatableAnimationState) {
        if(geoAnimatableAnimationState.isMoving() && !this.isFleeing()){
            geoAnimatableAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.bigfoot.walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
        if(geoAnimatableAnimationState.isMoving() && this.isFleeing()){
            geoAnimatableAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.bigfoot.run", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
        geoAnimatableAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.bigfoot.idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return SoundEvents.GRASS_BREAK;
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.RAVAGER_HURT;
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return SoundEvents.DOLPHIN_DEATH;
    }

    @Override
    public void tick() {
        super.tick();
    }

    private void throwRock(LivingEntity pTarget) {
        RockProjectileEntity RockProjectile = new RockProjectileEntity(this.level(), this);
        double targetX = pTarget.getX() - this.getX();
        double targetY = pTarget.getY(0.3333333333333333) - RockProjectile.getY() + 1;
        double targetZ = pTarget.getZ() - this.getZ();
        double sqrtTargetDistance = Math.sqrt(targetX * targetX + targetZ * targetZ) * (double)0.2F;
        RockProjectile.shoot(targetX, targetY + sqrtTargetDistance, targetZ, 0.6F, 5.0F);
        if (!this.isSilent()) {
            this.level().playSound((Player)null, this.getX(), this.getY(), this.getZ(), SoundEvents.STONE_BREAK, this.getSoundSource(), 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
        }

        this.level().addFreshEntity(RockProjectile);
    }

    @Override
    public void performRangedAttack(LivingEntity pTarget, float pDistanceFactor) {this.throwRock(pTarget);}
}
