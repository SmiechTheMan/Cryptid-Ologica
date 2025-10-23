package net.smiech.cryptidologica.entity.villager;

import com.google.common.collect.ImmutableSet;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.smiech.cryptidologica.CryptidOlogica;

public class ModVillagers {
    public static final DeferredRegister<PoiType> POI_TYPES =
            DeferredRegister.create(ForgeRegistries.POI_TYPES, CryptidOlogica.MOD_ID);

    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS =
            DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, CryptidOlogica.MOD_ID);

    public static final RegistryObject<PoiType> CONSPIRACYTABLE_POI = POI_TYPES.register("conspiracy_table_poi",
            ()-> new PoiType(ImmutableSet.copyOf(Blocks.DIAMOND_BLOCK.getStateDefinition().getPossibleStates()),1,1));

    public static final RegistryObject<VillagerProfession> NUTJOB = VILLAGER_PROFESSIONS.register("nutjob",
            ()-> new VillagerProfession("nutjob",
                    holder -> holder.get() == CONSPIRACYTABLE_POI.get(),
                    holder -> holder.get() == CONSPIRACYTABLE_POI.get(),
                    ImmutableSet.of(), ImmutableSet.of(), SoundEvents.BEEHIVE_WORK));


    public static void  register(IEventBus eventbus){
        POI_TYPES.register(eventbus);
        VILLAGER_PROFESSIONS.register(eventbus);
    }
}
