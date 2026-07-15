package com.gaiagauntlet.gg_server_plugin.lobby.commands;

import com.hypixel.hytale.server.core.command.system.AbstractCommand;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class StartCommand extends AbstractCommand {
    public StartCommand() {
        super("start", "Main command to start the full game flow from the lobby.");
    }

    @Override
    protected @Nullable CompletableFuture<Void> execute(@NonNull CommandContext commandContext) {
        return null;
    }
}
