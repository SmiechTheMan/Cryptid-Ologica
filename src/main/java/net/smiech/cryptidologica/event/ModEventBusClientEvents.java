package net.smiech.cryptidologica.event;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.VillagerModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.smiech.cryptidologica.CryptidOlogica;
import net.smiech.cryptidologica.entity.client.customProjectiles.RockProjectileModel;
import net.smiech.cryptidologica.entity.client.tinfoilhat.TinFoilHatLayer;
import net.smiech.cryptidologica.entity.client.tinfoilhat.TinFoilHatModel;
import net.smiech.cryptidologica.gui.ComputerStationMainScreen;
import net.smiech.cryptidologica.gui.ModMenus;

@Mod.EventBusSubscriber(modid = CryptidOlogica.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {
    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event){
        event.registerLayerDefinition(RockProjectileModel.ROCK_PROJECTILE, RockProjectileModel::createBodyLayer);
        event.registerLayerDefinition(TinFoilHatModel.TINFOIL_HAT, TinFoilHatModel::createBodyLayer);
    }

    @SubscribeEvent
    public static <T extends TinFoilHatModel> void registerLayer(EntityRenderersEvent.AddLayers addLayers){
        LivingEntityRenderer<Villager, VillagerModel<Villager>> renderer =  addLayers.getRenderer(EntityType.VILLAGER);
        renderer.addLayer(new TinFoilHatLayer<>(renderer,addLayers.getEntityModels()));
    }

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event){
        event.enqueueWork(()-> {
            MenuScreens.register(ModMenus.CS_MAIN_MENU.get(), ComputerStationMainScreen::new);
        });
    }
}
