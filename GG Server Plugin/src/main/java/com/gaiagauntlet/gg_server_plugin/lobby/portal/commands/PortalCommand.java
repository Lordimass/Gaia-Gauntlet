package com.gaiagauntlet.gg_server_plugin.lobby.portal.commands;

import com.gaiagauntlet.gg_server_plugin.GGConfig;
import com.gaiagauntlet.gg_server_plugin.commands.SetPoiCommand;
import com.hypixel.hytale.server.core.command.system.AbstractCommand;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class PortalCommand extends AbstractCommand {
    public PortalCommand() {
        super("portal", "Commands related to the portal from the lobby to the game world");

        addSubCommand(new OpenCommand());
        addSubCommand(new CloseCommand());
        addSubCommand(new SetPoiCommand(
            "setorigin",
            "Set the origin location for the portal block to your current location",
            () -> GGConfig.get().getPortalPoi(),
            (GGConfig.GGPoi poi) -> GGConfig.get().setPortalPoi(poi),
            "Portal Origin"
        ));
    }

    @Override
    protected @Nullable CompletableFuture<Void> execute(@NonNull CommandContext var1) {
        return null;
    }
}
