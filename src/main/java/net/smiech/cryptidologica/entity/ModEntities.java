package net.smiech.cryptidologica.entity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.smiech.cryptidologica.CryptidOlogica;
import net.smiech.cryptidologica.entity.custom.BigfootEntity;
import net.smiech.cryptidologica.entity.custom.RockProjectileEntity;

public class ModEntities {
    public  static  final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, CryptidOlogica.MOD_ID);


    public static final RegistryObject<EntityType<BigfootEntity>> BIGFOOT =
            ENTITY_TYPES.register("bigfoot", ()-> EntityType.Builder.of(BigfootEntity::new, MobCategory.CREATURE)
                    .sized(0.6F, 2.2F).build(ResourceLocation.fromNamespaceAndPath(CryptidOlogica.MOD_ID,"bigfoot").toString()));

    public static final RegistryObject<EntityType<RockProjectileEntity>> ROCK_PROJECTILE =
            ENTITY_TYPES.register("rock_projectile", ()-> EntityType.Builder.<RockProjectileEntity>of(RockProjectileEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F).build("rock_projectile"));


    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }
}
