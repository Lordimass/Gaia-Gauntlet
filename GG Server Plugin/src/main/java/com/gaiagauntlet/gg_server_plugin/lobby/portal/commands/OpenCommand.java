package com.gaiagauntlet.gg_server_plugin.lobby.portal.commands;

import com.gaiagauntlet.gg_server_plugin.GGConfig;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.math.Axis;
import com.hypixel.hytale.math.vector.Transform;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.AbstractCommand;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.console.ConsoleSender;
import com.hypixel.hytale.server.core.prefab.PrefabStore;
import com.hypixel.hytale.server.core.prefab.selection.standard.BlockSelection;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.joml.Vector3i;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.awt.*;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

import static com.gaiagauntlet.gg_server_plugin.lobby.prefabTimer.TimerPrefabUtils.placeBlockSelection;

public class OpenCommand extends AbstractCommand {
    private static final String PORTAL_PREFAB = "Portal/Portal.prefab.json";

    protected OpenCommand() {
        super("open", "Opens the portal to the game world");
    }

    @Override
    protected @Nullable CompletableFuture<Void> execute(@NonNull CommandContext commandContext) {
        placePortalPrefab(commandContext, PORTAL_PREFAB);
        return null;
    }

    protected static void placePortalPrefab(@NonNull CommandContext commandContext, String portalPrefab) {
        GGConfig.GGPoi poi = GGConfig.get().getPortalPoi();
        if (poi == null) {
            commandContext.sender().sendMessage(
                Message.raw("No portal origin set.").color(Color.RED)
            );
            return;
        }

        Path foundPath = PrefabStore.get().findAssetPrefabPath(portalPrefab);
        assert foundPath != null;
        BlockSelection blockSelection = PrefabStore.get().getPrefab(foundPath);

        Transform transform = poi.getTransform();
        Vector3i pos = new Vector3i(
            (int) transform.getPosition().x, (int) transform.getPosition().y, (int) transform.getPosition().z
        );

        if (transform.getAxis().equals(Axis.X)) {
            blockSelection = blockSelection.rotate(Axis.Y, -90);
        }

        placeBlockSelection(poi, blockSelection, pos);
    }
}
