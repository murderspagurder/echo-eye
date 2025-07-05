package dev.spagurder.echoeye.neoforge;

//? neoforge {
/*import dev.spagurder.echoeye.EchoEyeMod;
import dev.spagurder.echoeye.block.ModBlocks;
import dev.spagurder.echoeye.event.PlayerDeath;
import dev.spagurder.echoeye.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod(EchoEyeMod.MOD_ID)
@EventBusSubscriber
public class NeoforgeEntrypoint {

    @EventBusSubscriber(modid = EchoEyeMod.MOD_ID, value = Dist.CLIENT)
    public static class ClientEvents {
        @SubscribeEvent
        public static void onClientSetup(final FMLClientSetupEvent event) {
            EchoEyeMod.LOG.info("Initializing {} Client", EchoEyeMod.MOD_ID);
        }
    }

    @SubscribeEvent
    public static void commonSetup(final FMLCommonSetupEvent event) {
        EchoEyeMod.init();
    }

    @SubscribeEvent
    public static void register(RegisterEvent event) {
        event.register(
                Registries.BLOCK,
                registry -> {
                    registry.register(ResourceLocation.fromNamespaceAndPath(EchoEyeMod.MOD_ID, "echo_deepslate"),
                            ModBlocks.ECHO_DEEPSLATE);
                }
        );
        event.register(
                Registries.ITEM,
                registry -> {
                    registry.register(ResourceLocation.fromNamespaceAndPath(EchoEyeMod.MOD_ID, "echo_eye"),
                            ModItems.ECHO_EYE);
                    registry.register(ResourceLocation.fromNamespaceAndPath(EchoEyeMod.MOD_ID, "recovery_eye"),
                            ModItems.RECOVERY_EYE);
                    registry.register(ResourceLocation.fromNamespaceAndPath(EchoEyeMod.MOD_ID, "echo_deepslate"),
                            new BlockItem(ModBlocks.ECHO_DEEPSLATE, new Item.Properties()));
                }
        );
    }

    @SubscribeEvent
    public static void onBuildCreativeTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
            event.accept(ModItems.ECHO_EYE);
            event.accept(ModItems.RECOVERY_EYE);
        }
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(ModBlocks.ECHO_DEEPSLATE.asItem());
        }
    }

    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {
        EchoEyeMod.STATE.load(event.getServer());
    }

    @SubscribeEvent
    public static void onServerStopped(ServerStoppedEvent event) {
        EchoEyeMod.STATE.unload();
    }

    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            PlayerDeath.onPlayerDeath(player);
        }
    }

}
*///?}
