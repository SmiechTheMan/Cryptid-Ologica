package net.smiech.cryptidologica.entity.client.customProjectiles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.smiech.cryptidologica.CryptidOlogica;
import net.smiech.cryptidologica.entity.custom.RockProjectileEntity;

@OnlyIn(Dist.CLIENT)
public class RockProjectileRenderer extends EntityRenderer<RockProjectileEntity> {
    private static final ResourceLocation ROCK_PROJECTILE_LOCATION = ResourceLocation.fromNamespaceAndPath(CryptidOlogica.MOD_ID,"textures/entity/rock_projectile_texture.png");
    private final RockProjectileModel<RockProjectileEntity> model;

    public RockProjectileRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.model = new RockProjectileModel<>(pContext.bakeLayer(RockProjectileModel.ROCK_PROJECTILE));
    }



    public void render(RockProjectileEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        pPoseStack.pushPose();

        pPoseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.yRotO, pEntity.getYRot()) - 90.0F));
        pPoseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.xRotO, pEntity.getXRot())));
        pPoseStack.translate(0.0F, -1F, 0.0F);
        this.model.setupAnim(pEntity, pPartialTicks, 0.0F, -0.1F, 0.0F, 0.0F);
        VertexConsumer textureBuffer = pBuffer.getBuffer(this.model.renderType(ROCK_PROJECTILE_LOCATION));
        this.model.renderToBuffer(pPoseStack, textureBuffer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        pPoseStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    }
    @Override
    public ResourceLocation getTextureLocation(RockProjectileEntity rockProjectileEntity) {
        return ROCK_PROJECTILE_LOCATION;
    }
}