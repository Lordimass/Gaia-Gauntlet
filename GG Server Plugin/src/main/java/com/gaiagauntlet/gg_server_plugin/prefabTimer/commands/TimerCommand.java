package com.gaiagauntlet.gg_server_plugin.prefabTimer.commands;

import com.hypixel.hytale.server.core.command.system.AbstractCommand;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class TimerCommand extends AbstractCommand {
    public TimerCommand() {
        super("timer", "Commands related to the timer for game start");

        addSubCommand(new StartCommand());
        addSubCommand(new SetOriginCommand());
        addSubCommand(new StopCommand());
    }

    @Override
    protected @Nullable CompletableFuture<Void> execute(@NonNull CommandContext commandContext) {
        return null;
    }
}
