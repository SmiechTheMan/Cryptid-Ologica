package net.smiech.cryptidologica.block.entity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.smiech.cryptidologica.CryptidOlogica;
import net.smiech.cryptidologica.block.ModBlocks;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, CryptidOlogica.MOD_ID);

    public static final RegistryObject<BlockEntityType<ComputerStationBlockEntity>> COMPUTER_STATION_BE =
            BLOCK_ENTITIES.register("computer_station_be",() -> BlockEntityType.Builder.of(ComputerStationBlockEntity::new,
                            ModBlocks.COMPUTER_STATION.get()).build(null));

    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }
}
