package net.smiech.cryptidologica.item;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.smiech.cryptidologica.CryptidOlogica;
import net.smiech.cryptidologica.entity.ModEntities;
import net.smiech.cryptidologica.entity.custom.BigfootGhillieArmorItem;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CryptidOlogica.MOD_ID);

    public static final RegistryObject<Item> BIGFOOT_SPAWN_EGG = ITEMS.register("bigfoot_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.BIGFOOT, 0x995F40, 0x564434, new Item.Properties()));

    public static final RegistryObject<Item> BIGFOOT_FUR_ITEM = ITEMS.register("bigfoot_fur_item",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BIGFOOT_GHILLIE = ITEMS.register("bigfoot_ghillie",
            ()-> new BigfootGhillieArmorItem(ArmorItem.Type.CHESTPLATE));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
