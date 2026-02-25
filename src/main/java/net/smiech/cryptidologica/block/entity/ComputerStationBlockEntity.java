package net.smiech.cryptidologica.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.smiech.cryptidologica.CryptidOlogica;
import net.smiech.cryptidologica.gui.ComputerStationMainMenu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ComputerStationBlockEntity extends BlockEntity implements MenuProvider {
    private static final Component TITLE = Component.translatable("container." + CryptidOlogica.MOD_ID + ".computer_station_block_title");

    public ComputerStationBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.COMPUTER_STATION_BE.get(), pPos, pBlockState);

    }

    //The amount of slots will be 1 for the main menu, so it can unlock

    @Override
    public Component getDisplayName() {
        return TITLE;
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        return new ComputerStationMainMenu(pContainerId, pInventory, this);
    }
}
