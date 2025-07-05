package dev.spagurder.echoeye.core;

import dev.spagurder.echoeye.EchoEyeMod;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.RelativeMovement;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class EchoEyeUtil {

    public static void teleportEntityToSpawn(Entity entity) {
        if (entity instanceof ServerPlayer player) {
            DimensionTransition spawn = player.findRespawnPositionAndUseSpawnBlock(true, DimensionTransition.DO_NOTHING);
            player.teleportTo(
                    spawn.newLevel(),
                    spawn.pos().x,
                    spawn.pos().y,
                    spawn.pos().z,
                    spawn.yRot(),
                    spawn.xRot()
            );
            return;
        }
        ServerLevel spawnDimension = entity.getServer().overworld();
        BlockPos spawnPos = spawnDimension.getSharedSpawnPos();
        entity.teleportTo(
                spawnDimension,
                spawnPos.getX(),
                spawnPos.getY(),
                spawnPos.getZ(),
                EnumSet.noneOf(RelativeMovement.class),
                0f, 0f
        );
        entity.setDeltaMovement(Vec3.ZERO);
        entity.setOnGround(true);
    }

    public static boolean teleportPlayerToLastDeath(ServerPlayer player) {
        PlayerState state = EchoEyeMod.STATE.getPlayerState(player);
        if (state == null || state.lastDeathDimension == null) {
            sendError(player, "No previously recorded death found.");
            return false;
        }

        MinecraftServer server = player.getServer();
        ResourceLocation dimensionId = ResourceLocation.tryParse(state.lastDeathDimension);
        if (dimensionId == null) {
            sendError(player, "Invalid death dimension: " + state.lastDeathDimension);
            return false;
        }

        ResourceKey<Level> dimensionKey = ResourceKey.create(Registries.DIMENSION, dimensionId);
        ServerLevel level = server.getLevel(dimensionKey);
        if (level == null) {
            sendError(player, "Dimension no longer exists: " + state.lastDeathDimension);
            return false;
        }

        BlockPos deathPos = new BlockPos(state.lastDeathX, state.lastDeathY, state.lastDeathZ);
        ChunkPos chunkPos = new ChunkPos(deathPos);
        if (!level.hasChunk(chunkPos.x, chunkPos.z)) {
            level.getChunk(chunkPos.x, chunkPos.z);
        }

        BlockPos safePos = findSafeTeleportPosition(level, deathPos);
        if (safePos == null) {
            sendError(player, "Could not find a safe teleport location.");
            return false;
        }

        double dx = state.lastDeathX + 0.5 - safePos.getX();
        double dz = state.lastDeathZ + 0.5 - safePos.getZ();
        float yaw = (float)(Math.toDegrees(Math.atan2(dz, dx))) - 90f;

        player.teleportTo(level,
                safePos.getX() + 0.5, safePos.getY(), safePos.getZ() + 0.5,
                yaw, 0f);
        return true;
    }

    public static BlockPos findSafeTeleportPosition(ServerLevel level, BlockPos targetXZ) {
        int maxRadius = 16;
        BlockPos bestPos = null;
        double closestSqDist = Double.MAX_VALUE;

        for (int r = 0; r <= maxRadius; r++) {
            for (int dx = -r; dx <= r; dx++) {
                for (int dz = -r; dz <= r; dz++) {
                    if (Math.abs(dx) != r && Math.abs(dz) != r) continue;

                    int x = targetXZ.getX() + dx;
                    int z = targetXZ.getZ() + dz;
                    ChunkPos chunkPos = new ChunkPos(x >> 4, z >> 4);

                    if (!level.hasChunk(chunkPos.x, chunkPos.z)) {
                        level.getChunk(chunkPos.x, chunkPos.z);
                    }

                    for (int y = level.getMaxBuildHeight() - 1; y >= level.getMinBuildHeight(); y--) {
                        BlockPos pos = new BlockPos(x, y, z);
                        if (isSafeTeleportTarget(level, pos)) {
                            double distSq = dx * dx + dz * dz;
                            if (distSq < closestSqDist) {
                                bestPos = pos;
                                closestSqDist = distSq;
                            }
                            break;
                        }
                    }
                }
            }
        }
        return bestPos;
    }

    public static boolean isSafeTeleportTarget(Level level, BlockPos pos) {
        BlockPos headPos = pos.above();
        BlockPos groundPos = pos.below();

        BlockState feet = level.getBlockState(pos);
        BlockState head = level.getBlockState(headPos);
        BlockState ground = level.getBlockState(groundPos);

        boolean feetAir = isPassableFluid(feet);
        boolean headAir = isPassableFluid(head);
        boolean groundSolid = !ground.getCollisionShape(level, groundPos).isEmpty();

        boolean feetNonBlocking = feet.getCollisionShape(level, pos).isEmpty();
        boolean headNonBlocking = head.getCollisionShape(level, headPos).isEmpty();

        return feetAir && headAir && groundSolid && feetNonBlocking && headNonBlocking;
    }

    public static boolean isPassableFluid(BlockState state) {
        return state.isAir() ||
                state.getFluidState().is(FluidTags.WATER);
    }

    public static void sendError(ServerPlayer player, String message) {
        player.displayClientMessage(Component.literal(message).withStyle(ChatFormatting.RED), false);
    }

}
