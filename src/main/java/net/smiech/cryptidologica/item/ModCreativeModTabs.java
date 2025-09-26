package net.smiech.cryptidologica.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.smiech.cryptidologica.CryptidOlogica;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
        DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CryptidOlogica.MOD_ID);

public static final RegistryObject<CreativeModeTab> CRYPTIDOLOGICA_TAB = CREATIVE_MODE_TABS.register("cryptidologica_tab",
        ()-> CreativeModeTab.builder().icon(()->new ItemStack(ModItems.BIGFOOT_SPAWN_EGG.get()))
                .title(Component.translatable("creativetab.cryptidologica_tab")).displayItems((pParameters, pOutput) -> {

                    pOutput.accept(ModItems.BIGFOOT_SPAWN_EGG.get());

                }).build());

public static void register(IEventBus eventBus){
    CREATIVE_MODE_TABS.register(eventBus);
}

}

