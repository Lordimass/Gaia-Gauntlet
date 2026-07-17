package com.gaiagauntlet.gg_server_plugin.commands;

import com.gaiagauntlet.gg_server_plugin.GGConfig;
import com.gaiagauntlet.gg_server_plugin.lobby.prefabTimer.systems.UpdateTimerSystem;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.math.vector.Transform;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.jspecify.annotations.NonNull;

import java.util.UUID;

public class SetPoiCommand extends AbstractPlayerCommand {
    private final GetPoiFunction getPoiFunc;
    private final SetPoiFunction setPoiFunc;
    private final String poiName;

    public SetPoiCommand(@NonNull String name, @NonNull String description, GetPoiFunction getPoiFunc, SetPoiFunction setPoiFunc, String poiName) {
        super(name, description);
        this.getPoiFunc = getPoiFunc;
        this.setPoiFunc = setPoiFunc;
        this.poiName = poiName;
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
        GGConfig.GGPoi prev = getPoiFunc.apply();
        GGConfig.GGPoi poi = new GGConfig.GGPoi(playerTransform, world.getName());
        setPoiFunc.apply(poi);
        GGConfig.getConfig().save().thenRun(() -> {
            LOGGER.atInfo().log(playerTransform.getAxis().toString());

            playerRef.sendMessage(
                Message.raw("Changed " + poiName + " from " + prev + " to " + poi)
            );
        });
    }

    @FunctionalInterface
    public interface GetPoiFunction {
        GGConfig.GGPoi apply();
    }

    @FunctionalInterface
    public interface SetPoiFunction {
        void apply(GGConfig.GGPoi poi);
    }
}
