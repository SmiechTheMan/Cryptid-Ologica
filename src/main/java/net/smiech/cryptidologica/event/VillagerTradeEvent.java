package net.smiech.cryptidologica.event;


import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.smiech.cryptidologica.CryptidOlogica;
import net.smiech.cryptidologica.entity.villager.ModVillagers;

import java.util.List;

@Mod.EventBusSubscriber(modid = CryptidOlogica.MOD_ID)
public class VillagerTradeEvent {

    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event){
        if(event.getType() == ModVillagers.NUTJOB.get()){
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            //Currently thinking about:
            // Bear traps,
            // Red string to connect the dots(decorative blocks work like fairylights? if I can figure it out)
            // TinFoil hat that will prevent nausea maybe?
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 3),
                    new ItemStack(Items.STRING, 30),
                    10,6,0.02f));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 12),
                    new ItemStack(Items.STRING, 30),
                    10,6,0.02f));

        }
    }
}
