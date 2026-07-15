package com.gaiagauntlet.gg_server_plugin.lobby.portal.commands;

import com.hypixel.hytale.server.core.command.system.AbstractCommand;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import java.util.concurrent.CompletableFuture;

import static com.gaiagauntlet.gg_server_plugin.lobby.portal.commands.OpenCommand.placePortalPrefab;

public class CloseCommand extends AbstractCommand {
    private static final String CLEAR_PORTAL_PREFAB = "Portal/ClearPortal.prefab.json";

    protected CloseCommand() {
        super("close", "Close the portal to the game world");
    }

    @Override
    protected @Nullable CompletableFuture<Void> execute(@NonNull CommandContext commandContext) {
        placePortalPrefab(commandContext, CLEAR_PORTAL_PREFAB);
        return null;
    }
}
