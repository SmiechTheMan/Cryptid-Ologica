package net.smiech.cryptidologica.entity.client.BigfootClient;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.smiech.cryptidologica.CryptidOlogica;
import net.smiech.cryptidologica.entity.custom.BigfootEntity;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class BigFootModel  extends GeoModel<BigfootEntity> {
    @Override
    public ResourceLocation getModelResource(BigfootEntity bigfootEntity) {
        return ResourceLocation.fromNamespaceAndPath(CryptidOlogica.MOD_ID, "geo/bigfoot.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BigfootEntity bigfootEntity) {
        return ResourceLocation.fromNamespaceAndPath(CryptidOlogica.MOD_ID, "textures/entity/bigfoot.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BigfootEntity bigfootEntity) {
        return ResourceLocation.fromNamespaceAndPath(CryptidOlogica.MOD_ID, "animations/bigfoot.animation.json");
    }

    @Override
    public void setCustomAnimations(BigfootEntity animatable, long instanceId, AnimationState<BigfootEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("head");

        if(head != null){
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            head.setRotX(entityData.headPitch()/2 * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw()/2 * Mth.DEG_TO_RAD);

        }
    }
}
