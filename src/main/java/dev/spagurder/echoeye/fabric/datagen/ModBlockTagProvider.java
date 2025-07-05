package dev.spagurder.echoeye.fabric.datagen;

//? fabric {
import dev.spagurder.echoeye.EchoEyeMod;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {

    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        getOrCreateRawBuilder(BlockTags.MINEABLE_WITH_PICKAXE)
                .addElement(ResourceLocation.fromNamespaceAndPath(EchoEyeMod.MOD_ID, "echo_deepslate"));
    }

}
//?}