package net.smiech.cryptidologica.entity.client.tinfoilhat;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.VillagerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.npc.Villager;
import net.smiech.cryptidologica.entity.ModEntities;
import net.smiech.cryptidologica.entity.villager.ModVillagers;


public class TinFoilHatLayer extends RenderLayer<Villager, VillagerModel<Villager>>{
    private final VillagerModel<Villager> model;

    public TinFoilHatLayer(RenderLayerParent<Villager, VillagerModel<Villager>> pRenderer, EntityModelSet pModelSet) {
        super(pRenderer);
        this.model = new VillagerModel<>(pModelSet.bakeLayer(TinFoilHatModel.TINFOIL_HAT));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, Villager villager, float v, float v1, float v2, float v3, float v4, float v5) {
        if (villager.getVillagerData().getProfession() == ModVillagers.NUTJOB.get()){

        }
    }
}
