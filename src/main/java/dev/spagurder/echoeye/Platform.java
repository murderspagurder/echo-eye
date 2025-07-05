package dev.spagurder.echoeye;

//? fabric {
import dev.spagurder.echoeye.fabric.FabricPlatformImpl;
//?}
//? neoforge {
/*import dev.spagurder.echoeye.neoforge.NeoforgePlatformImpl;
*///?}

public interface Platform {

    //? fabric {
    Platform INSTANCE = new FabricPlatformImpl();
    //?}
    //? neoforge {
    /*Platform INSTANCE = new NeoforgePlatformImpl();
    *///?}

    boolean isModLoaded(String modid);
    String loader();

}
