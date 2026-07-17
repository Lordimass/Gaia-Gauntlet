package com.gaiagauntlet.gg_server_plugin.lobby.portal.commands;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.jspecify.annotations.NonNull;

import static com.gaiagauntlet.gg_server_plugin.lobby.portal.commands.PortalCommand.toggleTriggerVolumesEnabled;

public class PortalOpenCommand extends AbstractPlayerCommand {
    private static final String PORTAL_PREFAB = "Portal/Portal.prefab.json";

    protected PortalOpenCommand() {
        super("open", "Opens the portal to the game world");
    }

    @Override
    protected void execute(
        @NonNull CommandContext commandContext,
        @NonNull Store<EntityStore> store,
        @NonNull Ref<EntityStore> ref,
        @NonNull PlayerRef playerRef,
        @NonNull World world
    ) {
        PortalCommand.placePortalPrefab(commandContext, PORTAL_PREFAB);
        toggleTriggerVolumesEnabled(store);
    }

}
