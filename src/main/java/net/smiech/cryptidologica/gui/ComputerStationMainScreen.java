package net.smiech.cryptidologica.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.smiech.cryptidologica.CryptidOlogica;
import net.smiech.cryptidologica.block.entity.ComputerStationBlockEntity;

public class ComputerStationMainScreen extends AbstractContainerScreen<ComputerStationMainMenu> {

        private static final Component IMAGE_BUTTON =
                Component.translatable("gui." + CryptidOlogica.MOD_ID + ".image_button");
        private static final ResourceLocation CS_MAIN_MENU_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(CryptidOlogica.MOD_ID,"textures/gui/cs_main_menu_wip.png");

    private ComputerStationBlockEntity csbEntity;
    private int leftPos, rightPos;

    public ComputerStationMainScreen(ComputerStationMainMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    private Button imageButton;
    //the gui image needs to be 256 by 256 I think otherwise it breaks itself?
    // idk how I will work around it, maybe scale the base one and add elements onto it or use an overlay?

    @Override
    protected void init() {
        clearWidgets();
        super.init();
        this.imageHeight = 272;
        this.imageWidth = 496;
        this.inventoryLabelY = 10000;
        this. titleLabelY = 10000;
        this.leftPos = (this.width - imageWidth)/2;
        this.rightPos = (this.height - imageHeight)/2;

        //Use imageButton.builder for the final thing
        this.imageButton = addRenderableWidget(
                Button.builder(
                        IMAGE_BUTTON,
                        this::handleExampleButton)
                        .bounds(this.rightPos + imageWidth/8, this.topPos+imageHeight/8,65,80)
                        .build());
    }

    private void handleExampleButton(Button button){
    System.out.println("x:" + imageButton.getX() + " y:"+ imageButton.getY());
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        renderBackground(pGuiGraphics);
        pGuiGraphics.blit(CS_MAIN_MENU_TEXTURE,this.leftPos, this.rightPos,0,0,this.imageWidth,this.imageHeight,496,272);
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pGuiGraphics,pMouseX,pMouseY);
        pGuiGraphics.drawString(this.font,"Website in progress",this.rightPos + imageWidth/2, this.topPos,0x404040, false);

    }
}
