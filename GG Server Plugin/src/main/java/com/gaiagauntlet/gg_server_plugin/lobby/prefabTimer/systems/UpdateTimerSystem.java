package com.gaiagauntlet.gg_server_plugin.lobby.prefabTimer.systems;

import com.gaiagauntlet.gg_server_plugin.GGConfig;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.system.tick.TickingSystem;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.math.Axis;
import com.hypixel.hytale.math.vector.Transform;
import com.hypixel.hytale.server.core.console.ConsoleSender;
import com.hypixel.hytale.server.core.prefab.PrefabStore;
import com.hypixel.hytale.server.core.prefab.selection.standard.BlockSelection;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.joml.Vector3i;
import org.jspecify.annotations.NonNull;

import java.nio.file.Path;

public class UpdateTimerSystem extends TickingSystem<EntityStore> {
    private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();

    public static float secondsLeft = 0;
    public static Runnable nextCompletionCallback = null;

    public static float cumulativeSecond = 0;
    public static boolean paused = true;
    private static GGConfig.GGPoi timerPoi;
    private static World poiWorld;

    public UpdateTimerSystem() {
        super();
    }

    @Override
    public void tick(
        float dt,
        int index,
        @NonNull Store<EntityStore> store
    ) {
        if (paused) return;
        World world = store.getExternalData().getWorld();
        updateTimerPoi();
        if (timerPoi == null || world.getName().equals(timerPoi.getWorldName())) return;
        cumulativeSecond += dt;
        if (cumulativeSecond <= 1) return;

        paused = secondsLeft <= 0;
        if (paused) {
            // Timer complete
            if (nextCompletionCallback != null) {
                nextCompletionCallback.run();
                nextCompletionCallback = null;
            }
        }
        int secondsCeil = (int) Math.ceil(secondsLeft);

        int minutes = secondsCeil / 60;
        int seconds = secondsCeil % 60;

        if (timerPoi.getTransform().getAxis().equals(Axis.X)) {
            placeDigitsXAxis(minutes, seconds);
        } else {
            placeDigitsZAxis(minutes, seconds);
        }

        secondsLeft = Math.max(0, secondsLeft - cumulativeSecond);
        cumulativeSecond = 0;
    }

    public static void updateTimerPoi() {
        timerPoi = GGConfig.get().getTimerPoi();
        assert timerPoi != null;
        poiWorld = Universe.get().getWorld(timerPoi.getWorldName());
    }

    // placeDigitsZAxis and placeDigitsXAxis are just me being lazy... It was easier to just hard
    // code values than try to figure out the maths to place them correctly.

    private void placeDigitsZAxis(int minutes, int seconds) {
        // Seconds
        if (Integer.toString(seconds).length() > 1) {
            placeDigit(Integer.toString(seconds).charAt(1), 6, 0);
            placeDigit(Integer.toString(seconds).charAt(0), 0, 0);
            placeDigit(Integer.toString(seconds).charAt(1), -14, -4);
            placeDigit(Integer.toString(seconds).charAt(0), -8, -4);
        } else {
            placeDigit(Integer.toString(seconds).charAt(0), 6, 0);
            placeDigit('0', 0, 0);
            placeDigit(Integer.toString(seconds).charAt(0), -14, -4);
            placeDigit('0', -8, -4);
        }

        // Minutes
        if (Integer.toString(minutes).length() > 1) {
            placeDigit(Integer.toString(minutes).charAt(1), -8, 0);
            placeDigit(Integer.toString(minutes).charAt(0), -14, 0);
            placeDigit(Integer.toString(minutes).charAt(1), 0, -4);
            placeDigit(Integer.toString(minutes).charAt(0), 6, -4);
        } else {
            placeDigit('0', -14, 0);
            placeDigit(Integer.toString(minutes).charAt(0), -8, 0);
            placeDigit('0', 6, -4);
            placeDigit(Integer.toString(minutes).charAt(0), 0, -4);
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

        BlockSelection finalBlockSelection = blockSelection;
        poiWorld.execute(() -> {
            finalBlockSelection.place(ConsoleSender.INSTANCE, poiWorld, pos, null,
                (Ref<EntityStore> _) -> {}
            );
        });
    }
}
