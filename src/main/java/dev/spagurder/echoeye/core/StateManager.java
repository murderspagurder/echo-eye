package dev.spagurder.echoeye.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import dev.spagurder.echoeye.EchoEyeMod;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.storage.LevelResource;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StateManager {

    @Nullable
    private Map<UUID, PlayerState> playerStates;
    private Path saveFile;
    private static final Gson GSON = new GsonBuilder()
            .enableComplexMapKeySerialization()
            .setPrettyPrinting()
            .create();

    private Map<UUID, PlayerState> getPlayerStates() {
        if (playerStates == null) {
            throw new IllegalStateException("State Manager is not loaded!");
        }
        return playerStates;
    }

    @Nullable
    public PlayerState getPlayerState(ServerPlayer player) {
        return getPlayerStates().get(player.getUUID());
    }

    public PlayerState getOrCreatePlayerState(ServerPlayer player) {
        return getPlayerStates().computeIfAbsent(player.getUUID(), (uuid) -> new PlayerState());
    }

    public void load(MinecraftServer server) {
        Path worldPath = server.getWorldPath(LevelResource.ROOT);
        saveFile = worldPath.resolve(EchoEyeMod.MOD_ID).resolve("player_state.json");
        if (!Files.exists(saveFile)) {
            playerStates = new HashMap<>();
            return;
        }
        try (Reader reader = Files.newBufferedReader(saveFile)) {
            Type type = new TypeToken<Map<UUID, PlayerState>>() {}.getType();
            playerStates = GSON.fromJson(reader, type);
        } catch (JsonParseException e) {
            EchoEyeMod.LOG.error("Invalid JSON data in player state file: {}", e.getMessage());
        } catch (IOException e) {
            EchoEyeMod.LOG.error("Error loading player states: {}", e.getMessage());
        }
        playerStates = new HashMap<>();
    }

    public void unload() {
        save();
        getPlayerStates().clear();
        playerStates = null;
        saveFile = null;
    }

    public void save() {
        try {
            Files.createDirectories(saveFile.getParent());
            try (Writer writer = Files.newBufferedWriter(saveFile)) {
                GSON.toJson(getPlayerStates(), writer);
            }
        } catch (IOException e) {
            EchoEyeMod.LOG.error("Error saving player states: {}", e.getMessage());
        }
    }

}
