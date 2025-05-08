package net.smiech.cryptidologica.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.smiech.cryptidologica.entity.client.BigfootClient.BigfootModelLayers;
import net.minecraftforge.fml.common.Mod;
import net.smiech.cryptidologica.CryptidOlogica;
import net.smiech.cryptidologica.entity.client.BigfootClient.BigFootModel;

@Mod.EventBusSubscriber(modid = CryptidOlogica.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {
    @SubscribeEvent
    public static void  registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(BigfootModelLayers.BIGFOOT_LAYER, BigFootModel::createBodyLayer);
    }
}
