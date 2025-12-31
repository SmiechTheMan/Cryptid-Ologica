package net.smiech.cryptidologica.entity.client.tinfoilhat;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.VillagerHeadModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.VillagerDataHolder;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.smiech.cryptidologica.CryptidOlogica;
import net.smiech.cryptidologica.entity.villager.ModVillagers;


public class TinFoilHatLayer<T extends LivingEntity & VillagerDataHolder, M extends EntityModel<T> & VillagerHeadModel> extends RenderLayer<T, M>{

    ResourceLocation tinFoilHatTexture = ResourceLocation.fromNamespaceAndPath(CryptidOlogica.MOD_ID,"textures/entity/armor/tinfoilhattexture.png");
    public TinFoilHatLayer(RenderLayerParent<T, M> pRenderer) {
        super(pRenderer);
    }

    @Override
    public void render(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, T pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        if (!pLivingEntity.isInvisible()) {
            VillagerProfession villagerProfession = (pLivingEntity).getVillagerData().getProfession();
                if(villagerProfession == ModVillagers.NUTJOB.get()){
                    pPoseStack.pushPose();

                    pPoseStack.popPose();
                }
        }
    }
}
