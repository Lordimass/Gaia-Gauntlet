package com.gaiagauntlet.gg_server_plugin.lobby.prefabTimer.commands;

import com.gaiagauntlet.gg_server_plugin.lobby.prefabTimer.systems.UpdateTimerSystem;
import com.gaiagauntlet.gg_server_plugin.lobby.prefabTimer.TimerPrefabUtils;
import com.hypixel.hytale.server.core.command.system.AbstractCommand;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import org.joml.Vector3i;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import java.util.concurrent.CompletableFuture;

public class StopCommand extends AbstractCommand {
    private static final String TIMER_CLEAR = "Timer/ClearTimer.prefab.json";

    protected StopCommand() {
        super("stop", "Stop the countdown timer for game start");
    }

    @Override
    protected @Nullable CompletableFuture<Void> execute(@NonNull CommandContext commandContext) {
        UpdateTimerSystem.paused = true;

        TimerPrefabUtils.placePrefab(TIMER_CLEAR, new Vector3i(0,0,0));

        return null;
    }
}
