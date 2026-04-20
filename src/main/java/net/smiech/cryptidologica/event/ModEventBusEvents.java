package net.smiech.cryptidologica.event;

import net.minecraft.client.model.VillagerModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.VillagerRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
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
    public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event){
        event.register(ModEntities.BIGFOOT.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.WORLD_SURFACE,
                BigfootEntity::canSpawn,
                SpawnPlacementRegisterEvent.Operation.OR
        );
    }
}
