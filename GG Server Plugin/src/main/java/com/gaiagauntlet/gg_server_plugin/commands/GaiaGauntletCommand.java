package com.gaiagauntlet.gg_server_plugin.commands;

import com.gaiagauntlet.gg_server_plugin.prefabTimer.commands.TimerCommand;
import com.hypixel.hytale.server.core.command.system.AbstractCommand;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.permissions.HytalePermissions;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class GaiaGauntletCommand extends AbstractCommand {

    public GaiaGauntletCommand() {
        super("gaiagauntlet", "Gaia Gauntlet admin commands");

        this.addAliases("gg");
        this.addSubCommand(new TimerCommand());

        requirePermission(HytalePermissions.fromCommand("gaiagauntlet"));
    }

    @Override
    protected @Nullable CompletableFuture<Void> execute(@NonNull CommandContext commandContext) {
        return null;
    }
}
