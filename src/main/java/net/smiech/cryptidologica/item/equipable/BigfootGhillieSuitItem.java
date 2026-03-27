package net.smiech.cryptidologica.item.equipable;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.smiech.cryptidologica.item.ModItems;

public class BigfootGhillieSuitItem extends Item implements Equipable {
    public BigfootGhillieSuitItem(Properties pProperties) {
        super(pProperties);
        DispenserBlock.registerBehavior(this, ArmorItem.DISPENSE_ITEM_BEHAVIOR);
    }

    @Override
    public EquipmentSlot getEquipmentSlot() {return EquipmentSlot.CHEST; }

    @Override
    public SoundEvent getEquipSound() { return Equipable.super.getEquipSound(); }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        return this.swapWithEquipmentSlot(this, pLevel, pPlayer, pHand);
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 1;
    }

    public boolean isValidRepairItem(ItemStack pToRepair, ItemStack pRepair) {
        return pRepair.is(ModItems.BIGFOOT_FUR_ITEM.get());
    }
}
