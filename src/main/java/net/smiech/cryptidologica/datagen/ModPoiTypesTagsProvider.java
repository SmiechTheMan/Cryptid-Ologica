package net.smiech.cryptidologica.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.PoiTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.PoiTypeTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.smiech.cryptidologica.CryptidOlogica;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class ModPoiTypesTagsProvider extends PoiTypeTagsProvider {
    public ModPoiTypesTagsProvider(PackOutput p_256012_, CompletableFuture<HolderLookup.Provider> p_256617_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_256012_, p_256617_, CryptidOlogica.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(PoiTypeTags.ACQUIRABLE_JOB_SITE)
                .addOptional(ResourceLocation.fromNamespaceAndPath(CryptidOlogica.MOD_ID,"conspiracy_table_poi"));
    }
}
