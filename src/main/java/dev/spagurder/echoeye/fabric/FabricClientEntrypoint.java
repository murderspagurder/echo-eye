package dev.spagurder.echoeye.fabric;

//? fabric {
import dev.spagurder.echoeye.EchoEyeMod;
import net.fabricmc.api.ClientModInitializer;

public class FabricClientEntrypoint implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EchoEyeMod.LOG.info("Initializing {} Client", EchoEyeMod.MOD_ID);
    }

}
//?}