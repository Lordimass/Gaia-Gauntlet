package com.gaiagauntlet.gg_server_plugin.lobby.portal.commands;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.jspecify.annotations.NonNull;

import static com.gaiagauntlet.gg_server_plugin.lobby.portal.commands.PortalCommand.placePortalPrefab;
import static com.gaiagauntlet.gg_server_plugin.lobby.portal.commands.PortalCommand.setTriggerVolumesEnabled;

public class CloseCommand extends AbstractPlayerCommand {
    private static final String CLEAR_PORTAL_PREFAB = "Portal/ClearPortal.prefab.json";

    protected CloseCommand() {
        super("close", "Close the portal to the game world");
    }

    @Override
    protected void execute(
        @NonNull CommandContext commandContext,
        @NonNull Store<EntityStore> store,
        @NonNull Ref<EntityStore> ref,
        @NonNull PlayerRef playerRef,
        @NonNull World world
    ) {
        placePortalPrefab(commandContext, CLEAR_PORTAL_PREFAB);
        setTriggerVolumesEnabled(store, false);
    }
}
