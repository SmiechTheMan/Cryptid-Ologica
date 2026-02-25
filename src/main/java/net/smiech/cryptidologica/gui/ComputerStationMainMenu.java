package net.smiech.cryptidologica.gui;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.smiech.cryptidologica.block.ModBlocks;
import net.smiech.cryptidologica.block.entity.ComputerStationBlockEntity;

public class ComputerStationMainMenu extends AbstractContainerMenu {
    private final ComputerStationBlockEntity blockEntity;
    private final ContainerLevelAccess levelAccess;

    //client constructor
    public ComputerStationMainMenu(int containerID, Inventory playerInv, FriendlyByteBuf additionalData){
        this(containerID,playerInv, playerInv.player.level().getBlockEntity(additionalData.readBlockPos()));
    }

    //server constructor
    public  ComputerStationMainMenu(int containerID, Inventory playerInv, BlockEntity blockEntity){
        super(ModMenus.CS_MAIN_MENU.get(),containerID);
        if(blockEntity instanceof ComputerStationBlockEntity be){
            this.blockEntity = be;
        }else{
            throw new IllegalStateException("Incorrect block entity class (%s) passed into Main Menu Screen"
                    .formatted(blockEntity.getClass().getCanonicalName()));
        }
        this.levelAccess = ContainerLevelAccess.create(blockEntity.getLevel(),blockEntity.getBlockPos());

    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return null;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(this.levelAccess, pPlayer, ModBlocks.COMPUTER_STATION.get());
    }
}
