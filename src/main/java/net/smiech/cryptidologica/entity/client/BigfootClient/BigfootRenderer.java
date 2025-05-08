package net.smiech.cryptidologica.entity.client.BigfootClient;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.smiech.cryptidologica.CryptidOlogica;
import net.smiech.cryptidologica.entity.custom.BigfootCustom.BigfootEntity;

public class BigfootRenderer extends MobRenderer<BigfootEntity, BigFootModel<BigfootEntity>> {
    public BigfootRenderer(EntityRendererProvider.Context pContext) {
        super(pContext,new BigFootModel<>(pContext.bakeLayer(BigfootModelLayers.BIGFOOT_LAYER)),2f);
    }

    @Override
    public ResourceLocation getTextureLocation(BigfootEntity bigfootEntity) {
        return ResourceLocation.fromNamespaceAndPath(CryptidOlogica.MOD_ID,"textures/bigfoot/BigFoot.png");
    }
}
