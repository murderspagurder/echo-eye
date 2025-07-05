package dev.spagurder.echoeye.item;

import net.minecraft.world.item.Item;

public class ModItems {

    public static final EchoEye ECHO_EYE = new EchoEye(
            new Item.Properties().stacksTo(1)
    );

    public static final RecoveryEye RECOVERY_EYE = new RecoveryEye(
            new Item.Properties().stacksTo(1)
    );

}
