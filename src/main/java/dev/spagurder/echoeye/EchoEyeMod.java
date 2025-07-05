package dev.spagurder.echoeye;

import dev.spagurder.echoeye.block.ModBlocks;
import dev.spagurder.echoeye.core.EchoEyeUtil;
import dev.spagurder.echoeye.core.StateManager;
import dev.spagurder.echoeye.item.ModItems;
import net.kyrptonaught.customportalapi.api.CustomPortalBuilder;
import net.kyrptonaught.customportalapi.util.SHOULDTP;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.awt.Color.CYAN;

public class EchoEyeMod {

    public static final String MOD_ID = "echoeye";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_ID);
    public static final StateManager STATE = new StateManager();

    public static void init() {
        LOG.info("Initializing {} on {}", MOD_ID, Platform.INSTANCE.loader());

        CustomPortalBuilder.beginPortal()
                .frameBlock(ModBlocks.ECHO_DEEPSLATE)
                .lightWithItem(ModItems.ECHO_EYE)
                .tintColor(CYAN.getRGB())
                .registerBeforeTPEvent(entity -> {
                    EchoEyeUtil.teleportEntityToSpawn(entity);
                    return SHOULDTP.CANCEL_TP;
                })
                .destDimID(ResourceLocation.fromNamespaceAndPath("minecraft", "overworld"))
                .registerIgniteEvent((player, world, portalPos, framePos, portalIgnitionSource) -> {
                    if (!player.getAbilities().instabuild) {
                        if (player.getMainHandItem().is(ModItems.ECHO_EYE)) {
                            player.getMainHandItem().shrink(1);
                        }
                        else if (player.getOffhandItem().is(ModItems.ECHO_EYE)) {
                            player.getOffhandItem().shrink(1);
                        }
                    }
                })
                .registerPortal();
    }

}
