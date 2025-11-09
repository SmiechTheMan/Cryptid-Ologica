package net.smiech.cryptidologica.entity.client.customProjectiles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.LlamaSpitModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.projectile.LlamaSpit;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.smiech.cryptidologica.entity.custom.RockProjectileEntity;

@OnlyIn(Dist.CLIENT)
public class RockProjectileRenderer extends EntityRenderer<RockProjectileEntity> {
    private static final ResourceLocation ROCK_PROJECTILE_LOCATION = ResourceLocation.withDefaultNamespace("textures/entity/llama/spit.png");
    private final LlamaSpitModel<LlamaSpit> model;

    public RockProjectileRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.model = new LlamaSpitModel(pContext.bakeLayer(ModelLayers.LLAMA_SPIT));
    }

    @Override
    public ResourceLocation getTextureLocation(RockProjectileEntity rockProjectileEntity) {
        return ROCK_PROJECTILE_LOCATION;
    }

    public void render(RockProjectileEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        pPoseStack.pushPose();
        pPoseStack.translate(0.0F, 0.15F, 0.0F);
        pPoseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.yRotO, pEntity.getYRot()) - 90.0F));
        pPoseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.xRotO, pEntity.getXRot())));
        VertexConsumer $$6 = pBuffer.getBuffer(this.model.renderType(ROCK_PROJECTILE_LOCATION));
        this.model.renderToBuffer(pPoseStack, $$6, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        pPoseStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    }

}