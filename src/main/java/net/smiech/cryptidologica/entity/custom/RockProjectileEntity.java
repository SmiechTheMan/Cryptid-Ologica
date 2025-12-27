package net.smiech.cryptidologica.entity.custom;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import net.smiech.cryptidologica.entity.ModEntities;

public class RockProjectileEntity extends Projectile {
    public RockProjectileEntity(EntityType<? extends Projectile> pEntityType, Level pLevel) {super(pEntityType, pLevel);}

    public RockProjectileEntity(Level plevel){
        super(ModEntities.ROCK_PROJECTILE.get(), plevel);
    }

    public RockProjectileEntity(Level pLevel, LivingEntity pBigfoot) {
        this(ModEntities.ROCK_PROJECTILE.get(), pLevel);
        this.setOwner(pBigfoot);
        this.setPos(pBigfoot.getX() - (double)(pBigfoot.getBbWidth() + 1.0F) * (double)0.5F * (double) Mth.sin(pBigfoot.yBodyRot * ((float)Math.PI / 180F)), pBigfoot.getEyeY() - (double)0.1F + 1, pBigfoot.getZ() + (double)(pBigfoot.getBbWidth() + 1.0F) * (double)0.5F * (double)Mth.cos(pBigfoot.yBodyRot * ((float)Math.PI / 180F)));
    }

    public void tick() {
        super.tick();
        Vec3 vec3 = this.getDeltaMovement();
        HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
        if (hitresult.getType() != HitResult.Type.MISS && !ForgeEventFactory.onProjectileImpact(this, hitresult)) {
            this.onHit(hitresult);
        }

        double d0 = this.getX() + vec3.x;
        double d1 = this.getY() + vec3.y;
        double d2 = this.getZ() + vec3.z;
        this.updateRotation();
        float f = 0.99F;
        float f1 = 0.06F;
        if (this.level().getBlockStates(this.getBoundingBox()).noneMatch(BlockBehaviour.BlockStateBase::isAir)) {
            this.discard();
        } else if (this.isInWaterOrBubble()) {
            this.discard();
        } else {
            this.setDeltaMovement(vec3.scale((double)0.99F));
            if (!this.isNoGravity()) {
                //Speed of the projectile? well more like gravity
                this.setDeltaMovement(this.getDeltaMovement().add((double)0.0F, (double)-0.03F, (double)0.0F));
            }

            this.setPos(d0, d1, d2);
        }

    }

    @Override
    protected void onHit(HitResult pResult) {
        super.onHit(pResult);

    }

    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);

        if(pResult.getEntity() instanceof Player pPlayer){
            if(disableShield(pPlayer)){
                pPlayer.hurt(this.damageSources().mobProjectile(this, pPlayer),1.0f);
            }else{
                pPlayer.hurt(this.damageSources().mobProjectile(this, pPlayer),0.0f);

            }
        }
        this.discard();
    }

    private boolean disableShield(Player player){
        if (player.getUseItem().getItem() instanceof ShieldItem){
            player.getCooldowns().addCooldown(player.getUseItem().getItem(),100);
            player.stopUsingItem();
        }
        return player.getUseItem().getItem() instanceof ShieldItem;
    }

    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);
        if (!this.level().isClientSide) {
            this.discard();

        }

    }
    @Override
    protected void defineSynchedData() {

    }


    //creates explosion effect
    public void recreateFromPacket(ClientboundAddEntityPacket pPacket) {
        super.recreateFromPacket(pPacket);
        double d0 = pPacket.getXa();
        double d1 = pPacket.getYa();
        double d2 = pPacket.getZa();

        for(int i = 0; i < 2; ++i) {
            double d3 = 0.4 + 0.1 * (double)i;
            this.level().addParticle(ParticleTypes.EXPLOSION, this.getX(), this.getY(), this.getZ(), d0 * d3, d1, d2 * d3);
        }

        this.setDeltaMovement(d0, d1, d2);
    }

}
