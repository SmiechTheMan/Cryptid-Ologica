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

public class ModEntities {
    public  static  final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, CryptidOlogica.MOD_ID);


    public static final RegistryObject<EntityType<BigfootEntity>> BIGFOOT =
            ENTITY_TYPES.register("bigfoot", ()-> EntityType.Builder.of(BigfootEntity::new, MobCategory.CREATURE)
                    .sized(0.6F, 2.9F).build(ResourceLocation.fromNamespaceAndPath(CryptidOlogica.MOD_ID,"bigfoot").toString()));

    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }
}
