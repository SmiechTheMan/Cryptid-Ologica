package net.smiech.cryptidologica.entity.client.BigfootClient;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.smiech.cryptidologica.CryptidOlogica;
import net.smiech.cryptidologica.entity.custom.BigfootCustom.BigfootEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BigfootRenderer extends GeoEntityRenderer<BigfootEntity> {
    public BigfootRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new BigFootModel());
    }

    @Override
    public ResourceLocation getTextureLocation(BigfootEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(CryptidOlogica.MOD_ID,"textures/entity/bigfoot.png");
    }

}
