package dev.spagurder.echoeye.event;

import dev.spagurder.echoeye.EchoEyeMod;
import dev.spagurder.echoeye.core.PlayerState;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;

public class PlayerDeath {

    public static void onPlayerDeath(ServerPlayer player) {
        BlockPos deathPos = player.blockPosition();
        PlayerState playerState = EchoEyeMod.STATE.getOrCreatePlayerState(player);
        playerState.lastDeathDimension = player.serverLevel().dimension().location().toString();
        playerState.lastDeathX = deathPos.getX();
        playerState.lastDeathY = deathPos.getY();
        playerState.lastDeathZ = deathPos.getZ();
        EchoEyeMod.STATE.save();
    }

}
