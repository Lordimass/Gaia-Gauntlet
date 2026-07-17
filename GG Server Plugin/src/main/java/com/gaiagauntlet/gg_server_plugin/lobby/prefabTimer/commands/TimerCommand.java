package com.gaiagauntlet.gg_server_plugin.lobby.prefabTimer.commands;

import com.gaiagauntlet.gg_server_plugin.GGConfig;
import com.gaiagauntlet.gg_server_plugin.commands.SetPoiCommand;
import com.hypixel.hytale.server.core.command.system.AbstractCommand;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class TimerCommand extends AbstractCommand {
    public TimerCommand() {
        super("timer", "Commands related to the timer for game start");

        addSubCommand(new TimerStartCommand());
        addSubCommand(new TimerStopCommand());
        addSubCommand(new SetPoiCommand(
            "setorigin",
            "Set the point origin of the timer prefabs",
            () -> GGConfig.get().getTimerPoi(),
            (GGConfig.GGPoi poi) -> GGConfig.get().setTimerPoi(poi),
            "Timer Origin"
        ));
    }

    @Override
    protected @Nullable CompletableFuture<Void> execute(@NonNull CommandContext commandContext) {
        return null;
    }
}
