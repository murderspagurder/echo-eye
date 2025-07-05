package dev.spagurder.echoeye.fabric.datagen;

//? fabric {
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class FabricDataGeneratorEntrypoint implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        final FabricDataGenerator.Pack pack = generator.createPack();
        pack.addProvider(ModBlockLootTableProvider::new);
        pack.addProvider(ModBlockTagProvider::new);
        pack.addProvider(ModRecipeProvider::new);
    }

}
//?}
