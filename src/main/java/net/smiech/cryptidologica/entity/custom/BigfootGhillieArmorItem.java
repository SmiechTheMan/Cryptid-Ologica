package net.smiech.cryptidologica.entity.custom;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.smiech.cryptidologica.CryptidOlogica;
import net.smiech.cryptidologica.entity.client.armor.model.ArmorModelProvider;
import net.smiech.cryptidologica.entity.client.armor.model.BigfootGhillieModel;
import net.smiech.cryptidologica.entity.client.armor.model.SimpleModelProvider;
import org.jetbrains.annotations.Nullable;

public class BigfootGhillieArmorItem extends AbstractArmorItem{

    private static final String TEXTURE_LOCATION = makeCustomTextureLocation(CryptidOlogica.MOD_ID,"bigfoot_ghillie_texture");

    public BigfootGhillieArmorItem( Type pType) {
        super(ArmorMaterials.LEATHER, pType, new Properties().rarity(Rarity.RARE));
    }

    @Override
    protected boolean withCustomModel() {
        return true;
    }

    @Override
    protected ArmorModelProvider createModelProvider() {
        return new SimpleModelProvider(BigfootGhillieModel::createBodyLayer, BigfootGhillieModel::new);
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return TEXTURE_LOCATION;
    }
}
