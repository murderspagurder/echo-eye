package dev.spagurder.echoeye.fabric;

//? fabric {
import dev.spagurder.echoeye.EchoEyeMod;
import dev.spagurder.echoeye.block.ModBlocks;
import dev.spagurder.echoeye.event.PlayerDeath;
import dev.spagurder.echoeye.item.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class FabricEntrypoint implements ModInitializer {

    @Override
    public void onInitialize() {
        doRegisters();

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS).register((creativeModeTab) -> {
            creativeModeTab.accept(ModItems.ECHO_EYE);
            creativeModeTab.accept(ModItems.RECOVERY_EYE);
        });

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.BUILDING_BLOCKS).register((creativeModeTab) -> {
            creativeModeTab.accept(ModBlocks.ECHO_DEEPSLATE.asItem());
        });

        ServerLifecycleEvents.SERVER_STARTED.register(EchoEyeMod.STATE::load);
        ServerLifecycleEvents.SERVER_STOPPED.register((server) -> EchoEyeMod.STATE.unload());

        ServerLivingEntityEvents.AFTER_DEATH.register((entity, damageSource) -> {
            if (entity instanceof ServerPlayer player) {
                PlayerDeath.onPlayerDeath(player);
            }
        });

        EchoEyeMod.init();
    }

    public static void doRegisters() {
        registerBlock("echo_deepslate", ModBlocks.ECHO_DEEPSLATE, true);
        registerItem("echo_eye", ModItems.ECHO_EYE);
        registerItem("recovery_eye", ModItems.RECOVERY_EYE);
    }

    public static void registerBlock(String name, Block block, boolean registerItem) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(EchoEyeMod.MOD_ID, name);
        if (registerItem) {
            BlockItem blockItem = new BlockItem(block, new Item.Properties());
            Registry.register(BuiltInRegistries.ITEM, id, blockItem);
        }
        Registry.register(BuiltInRegistries.BLOCK, id, block);
    }

    public static void registerItem(String name, Item item) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(EchoEyeMod.MOD_ID, name);
        Registry.register(BuiltInRegistries.ITEM, id, item);
    }

}
//?}
