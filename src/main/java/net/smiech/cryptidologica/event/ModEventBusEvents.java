package net.smiech.cryptidologica.event;

import net.minecraft.client.model.VillagerModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.VillagerRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.smiech.cryptidologica.CryptidOlogica;
import net.smiech.cryptidologica.entity.ModEntities;
import net.smiech.cryptidologica.entity.client.customProjectiles.RockProjectileModel;
import net.smiech.cryptidologica.entity.client.tinfoilhat.TinFoilHatLayer;
import net.smiech.cryptidologica.entity.client.tinfoilhat.TinFoilHatModel;
import net.smiech.cryptidologica.entity.custom.BigfootEntity;
import net.smiech.cryptidologica.entity.custom.RockProjectileEntity;

import java.util.Map;

@Mod.EventBusSubscriber(modid = CryptidOlogica.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event){
        event.put(ModEntities.BIGFOOT.get(), BigfootEntity.createAttributes().build());
    }
    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event){
        event.registerLayerDefinition(RockProjectileModel.ROCK_PROJECTILE, RockProjectileModel::createBodyLayer);
        event.registerLayerDefinition(TinFoilHatModel.TINFOIL_HAT, TinFoilHatModel::createBodyLayer);
    }
    @SubscribeEvent
    public static <T extends VillagerModel<Villager>> void registerLayer(EntityRenderersEvent.AddLayers addLayers){
        LivingEntityRenderer<Villager, T> renderer =  addLayers.getRenderer(EntityType.VILLAGER);
        renderer.addLayer(new TinFoilHatLayer<>(renderer));
    }
}
