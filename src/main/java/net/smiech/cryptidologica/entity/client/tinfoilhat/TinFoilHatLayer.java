package net.smiech.cryptidologica.entity.client.tinfoilhat;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerDataHolder;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.smiech.cryptidologica.CryptidOlogica;
import net.smiech.cryptidologica.entity.villager.ModVillagers;


public class TinFoilHatLayer<T extends LivingEntity & VillagerDataHolder, M extends EntityModel<T> & VillagerHeadModel> extends RenderLayer<T, M>{
    ResourceLocation TINFOIL_HAT_TEXTURE = ResourceLocation.fromNamespaceAndPath(CryptidOlogica.MOD_ID,"textures/armor/tinfoil_hat_texture.png");
    private final TinFoilHatModel model;

    public TinFoilHatLayer(RenderLayerParent<T, M> pRenderer, EntityModelSet pModelSet) {
        super(pRenderer);
        this.model = new TinFoilHatModel(pModelSet.bakeLayer(TinFoilHatModel.TINFOIL_HAT));
    }

    @Override
    public void render(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, T pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        if (!pLivingEntity.isInvisible()) {
            VillagerProfession villagerProfession = (pLivingEntity).getVillagerData().getProfession();
                if(villagerProfession == ModVillagers.NUTJOB.get()){
                    ((HeadedModel)this.getParentModel()).getHead().translateAndRotate(pPoseStack);
                    pPoseStack.pushPose();
                    pPoseStack.translate(0F,-1.95F,0F);
                    VertexConsumer textureBuffer = pBuffer.getBuffer(RenderType.entityCutoutNoCull(TINFOIL_HAT_TEXTURE));
                    this.model.renderToBuffer(pPoseStack, textureBuffer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
                    pPoseStack.popPose();
                }
        }
    }

    @Override
    protected ResourceLocation getTextureLocation(T pEntity) {
        return ResourceLocation.fromNamespaceAndPath(CryptidOlogica.MOD_ID,"textures/armors/tinfoil_hat_texture.png");
    }
}
