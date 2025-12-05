package net.smiech.cryptidologica.event;

import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.smiech.cryptidologica.CryptidOlogica;
import net.smiech.cryptidologica.entity.ModEntities;
import net.smiech.cryptidologica.entity.client.customProjectiles.RockProjectileModel;
import net.smiech.cryptidologica.entity.custom.BigfootEntity;
import net.smiech.cryptidologica.entity.custom.RockProjectileEntity;

@Mod.EventBusSubscriber(modid = CryptidOlogica.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event){
        event.put(ModEntities.BIGFOOT.get(), BigfootEntity.createAttributes().build());
    }
    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event){
        event.registerLayerDefinition(RockProjectileModel.ROCK_PROJECTILE, RockProjectileModel::createBodyLayer);
    }
}
