package com.gaiagauntlet.gg_server_plugin.lobby.commands;

import com.gaiagauntlet.gg_server_plugin.lobby.prefabTimer.systems.UpdateTimerSystem;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.command.system.*;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.jspecify.annotations.NonNull;

public class GameStartCommand extends AbstractPlayerCommand {
    public GameStartCommand() {
        super("start", "Main command to start the full game flow from the lobby.");
    }


    @Override
    protected void execute(
        @NonNull CommandContext commandContext,
        @NonNull Store<EntityStore> store,
        @NonNull Ref<EntityStore> ref,
        @NonNull PlayerRef playerRef,
        @NonNull World world
    ) {
        CommandManager cm = CommandManager.get();
        cm.handleCommand(playerRef, "gg portal close");
        cm.handleCommand(playerRef, "gg timer start --minutes=10 --seconds=0");
        UpdateTimerSystem.nextCompletionCallback = () -> {
            cm.handleCommand(playerRef, "gg portal open");
            cm.handleCommand(playerRef, "gg timer start --minutes=2 --seconds=0");
            UpdateTimerSystem.nextCompletionCallback = () -> {
                cm.handleCommand(playerRef, "gg timer stop");
            };
        };
    }
}
