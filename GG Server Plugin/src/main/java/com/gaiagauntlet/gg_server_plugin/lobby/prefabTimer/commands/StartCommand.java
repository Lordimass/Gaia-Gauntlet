package com.gaiagauntlet.gg_server_plugin.lobby.prefabTimer.commands;

import com.gaiagauntlet.gg_server_plugin.lobby.prefabTimer.systems.UpdateTimerSystem;
import com.gaiagauntlet.gg_server_plugin.lobby.prefabTimer.TimerPrefabUtils;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.command.system.AbstractCommand;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.DefaultArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import org.joml.Vector3i;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class StartCommand extends AbstractCommand {
    private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
    private static final String TIMER_BASE = "Timer/Timer_Base.prefab.json";

    private final DefaultArg<Integer> minutesArg;
    private final DefaultArg<Integer> secondsArg;

    protected StartCommand() {
        super("start", "Start the countdown timer for game start");

        minutesArg = withDefaultArg("minutes", "The number of minutes the timer should run for", ArgTypes.INTEGER, 20, "20 minutes");
        secondsArg = withDefaultArg("seconds", "The number of seconds the timer should run for", ArgTypes.INTEGER, 0, "0 seconds");
    }

    @Override
    protected @Nullable CompletableFuture<Void> execute(@NonNull CommandContext commandContext) {
        Integer minutes = minutesArg.get(commandContext);
        Integer seconds = secondsArg.get(commandContext);

        UpdateTimerSystem.secondsLeft = minutes * 60 + seconds;
        UpdateTimerSystem.paused = false;

        TimerPrefabUtils.placePrefab(TIMER_BASE, new Vector3i(0, 0, 0));
        return null;
    }
}
