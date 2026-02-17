package net.smiech.cryptidologica.gui;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.smiech.cryptidologica.CryptidOlogica;
import net.smiech.cryptidologica.block.entity.ComputerStationBlockEntity;

public class ComputerStationMainScreen extends Screen {

    private static final Component TITLE = Component.translatable("gui." + CryptidOlogica.MOD_ID + ".computer_station_main_screen");

    private final BlockPos position;
    private final int imageWidth, imageHeight;

    private ComputerStationBlockEntity csbEntity;
    private int leftPost, rightPost;

    private Button button;

    public ComputerStationMainScreen(BlockPos pPos) {
        super(TITLE);

        this.position = pPos;
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void init() {
         super.init();

         if(this.minecraft == null) return;

         Level level = this.minecraft.level;
         if(level == null) return;

         BlockEntity be = level.getBlockEntity(this.position);
         if(be instanceof ComputerStationBlockEntity csbEntity){
             this.csbEntity = csbEntity;
         }else{
             return;
         }


    }
}
