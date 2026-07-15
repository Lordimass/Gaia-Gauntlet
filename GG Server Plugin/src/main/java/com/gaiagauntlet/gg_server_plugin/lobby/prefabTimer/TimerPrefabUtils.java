package com.gaiagauntlet.gg_server_plugin.lobby.prefabTimer;

import com.gaiagauntlet.gg_server_plugin.GGConfig;
import com.gaiagauntlet.gg_server_plugin.lobby.portal.commands.OpenCommand;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.math.Axis;
import com.hypixel.hytale.math.vector.Transform;
import com.hypixel.hytale.server.core.console.ConsoleSender;
import com.hypixel.hytale.server.core.prefab.PrefabStore;
import com.hypixel.hytale.server.core.prefab.selection.standard.BlockSelection;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.joml.Vector3i;

import java.nio.file.Path;

public class TimerPrefabUtils {
    private TimerPrefabUtils() {}

    public static void placePrefab(String prefab, Vector3i offset) {
        Path foundPath = PrefabStore.get().findAssetPrefabPath(prefab);
        assert foundPath != null;
        BlockSelection blockSelection = PrefabStore.get().getPrefab(foundPath);
        GGConfig.GGPoi timerPoi = GGConfig.get().getTimerPoi();
        Transform transform = timerPoi.getTransform();
        Vector3i pos = new Vector3i(
            (int) transform.getPosition().x, (int) transform.getPosition().y, (int) transform.getPosition().z
        );

        if (transform.getAxis().equals(Axis.Z)) {
            blockSelection = blockSelection.rotate(Axis.Y, -90);
            pos.add(new Vector3i(-4, -2, -2));
            pos.add(offset);
        } else {
            pos.add(new Vector3i(-2, -2, -4));
            pos.add(new Vector3i(offset.z, offset.y, offset.x));
        }

        placeBlockSelection(timerPoi, blockSelection, pos);
    }

    public static void placeBlockSelection(GGConfig.GGPoi poi, BlockSelection blockSelection, Vector3i pos) {
        World poiWorld = Universe.get().getWorld(poi.getWorldName());
        assert poiWorld != null;
        poiWorld.execute(() -> {
            blockSelection.place(ConsoleSender.INSTANCE, poiWorld, pos, null,
                (Ref<EntityStore> ref) -> {}
            );
        });
    }
}
