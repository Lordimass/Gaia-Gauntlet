package com.gaiagauntlet.gg_server_plugin.prefabTimer.systems;

import com.gaiagauntlet.gg_server_plugin.GGConfig;
import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.tick.DelayedEntitySystem;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.math.Axis;
import com.hypixel.hytale.math.vector.Transform;
import com.hypixel.hytale.server.core.console.ConsoleSender;
import com.hypixel.hytale.server.core.entity.entities.Player;
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

public class UpdateTimerSystem extends DelayedEntitySystem<EntityStore> {
    private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();

    public static float secondsLeft = 0;
    public static boolean paused = true;
    private static GGConfig.GGPoi timerPoi;
    private static World poiWorld;

    public UpdateTimerSystem() {
        super(1.0F);
    }

    @Override
    public void tick(
        float dt,
        int index,
        @NonNull ArchetypeChunk archetypeChunk,
        @NonNull Store store,
        @NonNull CommandBuffer commandBuffer
    ) {
        if (paused) return;
        paused = secondsLeft <= 0;
        int secondsCeil = (int) Math.ceil(secondsLeft);

        int minutes = secondsCeil / 60;
        int seconds = secondsCeil % 60;

        if (timerPoi == null) {updateTimerPoi();}
        if (timerPoi == null) {return;}

        if (timerPoi.getTransform().getAxis().equals(Axis.X)) {
            placeDigitsXAxis(minutes, seconds);
        } else {
            placeDigitsZAxis(minutes, seconds);
        }

        secondsLeft = Math.max(0, secondsLeft - dt);
    }

    @Override
    public @Nullable Query<EntityStore> getQuery() {
        return Player.getComponentType();
    }

    public static void updateTimerPoi() {
        timerPoi = GGConfig.get().getTimerPoi();
        poiWorld = Universe.get().getWorld(timerPoi.getWorldName());
    }

    // placeDigitsZAxis and placeDigitsXAxis are just me being lazy... It was easier to just hard
    // code values than try to figure out the maths to place them correctly.

    private void placeDigitsZAxis(int minutes, int seconds) {
        // Seconds
        if (Integer.toString(seconds).length() > 1) {
            placeDigit(Integer.toString(seconds).charAt(1), 6, 0);
            placeDigit(Integer.toString(seconds).charAt(0), 0, 0);
            placeDigit(Integer.toString(minutes).charAt(1), -14, -4);
            placeDigit(Integer.toString(minutes).charAt(0), -8, -4);
        } else {
//            placeDigit(Integer.toString(seconds).charAt(0), 6, 0);
            placeDigit('0', 0, 0);
            placeDigit(Integer.toString(minutes).charAt(0), -8, -4);
            placeDigit('0', -14, -4);
        }

        // Minutes
        if (Integer.toString(minutes).length() > 1) {
            placeDigit(Integer.toString(minutes).charAt(1), -8, 0);
            placeDigit(Integer.toString(minutes).charAt(0), -14, 0);
            placeDigit(Integer.toString(seconds).charAt(1), 0, -4);
            placeDigit(Integer.toString(seconds).charAt(0), 6, -4);
        } else {
            placeDigit('0', -14, 0);
            placeDigit(Integer.toString(minutes).charAt(0), -8, 0);
            placeDigit('0', 6, -4);
            placeDigit(Integer.toString(seconds).charAt(0), 0, -4);
        }
    }

    private void placeDigitsXAxis(int minutes, int seconds) {
        // Seconds
        if (Integer.toString(seconds).length() > 1) {
            placeDigit(Integer.toString(seconds).charAt(1), -14,0);
            placeDigit(Integer.toString(seconds).charAt(0), -8, 0);
            placeDigit(Integer.toString(seconds).charAt(1), 6,-4);
            placeDigit(Integer.toString(seconds).charAt(0), 0, -4);
        } else {
            placeDigit(Integer.toString(seconds).charAt(0), -14, 0);
            placeDigit('0', -8, 0);
            placeDigit(Integer.toString(seconds).charAt(0), 6,-4);
            placeDigit('0', 0, -4);
        }

        // Minutes
        if (Integer.toString(minutes).length() > 1) {
            placeDigit(Integer.toString(minutes).charAt(1), 0, 0);
            placeDigit(Integer.toString(minutes).charAt(0), 6, 0);
            placeDigit(Integer.toString(minutes).charAt(1), -8, -4);
            placeDigit(Integer.toString(minutes).charAt(0), -14,-4);
        } else {
            placeDigit('0', 6, 0);
            placeDigit(Integer.toString(minutes).charAt(0), 0, 0);
            placeDigit('0', -14, -4);
            placeDigit(Integer.toString(minutes).charAt(0), -8,-4);
        }
    }

    private void placeDigit(char digit, int horizontalOffset, int depthOffset) {
        Transform transform = timerPoi.getTransform();

        Path foundPath = PrefabStore.get().findAssetPrefabPath("Timer/Lightblock_Char_" + digit + ".prefab.json");
        assert foundPath != null;
        BlockSelection blockSelection = PrefabStore.get().getPrefab(foundPath);
        Vector3i pos = new Vector3i(
            (int) transform.getPosition().x, (int) transform.getPosition().y, (int) transform.getPosition().z
        );

        if (transform.getAxis().equals(Axis.Z)) {
            blockSelection = blockSelection.rotate(Axis.Y, -90);
            pos.add(new Vector3i(horizontalOffset, 0, depthOffset));
        } else {
            pos.add(new Vector3i(depthOffset, 0, horizontalOffset));
        }

        if (depthOffset < 0) {
            blockSelection = blockSelection.flip(transform.getAxis() == Axis.X ? Axis.Z : Axis.X);
        }

        blockSelection.place(ConsoleSender.INSTANCE, poiWorld, pos, null,
            (Ref<EntityStore> ref) -> {}
        );
    }
}
