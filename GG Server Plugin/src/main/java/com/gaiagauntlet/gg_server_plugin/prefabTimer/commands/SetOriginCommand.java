package com.gaiagauntlet.gg_server_plugin.prefabTimer.commands;

import com.gaiagauntlet.gg_server_plugin.GGConfig;
import com.gaiagauntlet.gg_server_plugin.prefabTimer.systems.UpdateTimerSystem;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.math.vector.Transform;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.jspecify.annotations.NonNull;

public class SetOriginCommand extends AbstractPlayerCommand {
    private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();

    public SetOriginCommand() {
        super("setorigin", "Set the point origin of the timer prefabs");
    }

    @Override
    protected void execute(
        @NonNull CommandContext commandContext,
        @NonNull Store<EntityStore> store,
        @NonNull Ref<EntityStore> ref,
        @NonNull PlayerRef playerRef,
        @NonNull World world) {

        Transform playerTransform = playerRef.getTransform().clone();
        playerTransform.setRotation(playerRef.getHeadRotation());
        playerTransform.getRotation().setPitch(0);
        GGConfig.GGPoi prev = GGConfig.get().getTimerPoi();
        GGConfig.GGPoi poi = new GGConfig.GGPoi(playerTransform, world.getName());
        GGConfig.get().setTimerPoi(poi);
        GGConfig.getConfig().save();

        LOGGER.atInfo().log(playerTransform.getAxis().toString());

        UpdateTimerSystem.updateTimerPoi();

        playerRef.sendMessage(
            Message.raw("Changed timer origin from " + prev + " to " + poi)
        );
    }
}
