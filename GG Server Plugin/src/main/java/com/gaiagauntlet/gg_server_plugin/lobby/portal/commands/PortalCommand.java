package com.gaiagauntlet.gg_server_plugin.lobby.portal.commands;

import com.gaiagauntlet.gg_server_plugin.GGConfig;
import com.gaiagauntlet.gg_server_plugin.commands.ConfigArrayCommand;
import com.gaiagauntlet.gg_server_plugin.commands.SetPoiCommand;
import com.hypixel.hytale.builtin.triggervolumes.TriggerVolumesPlugin;
import com.hypixel.hytale.builtin.triggervolumes.manager.TriggerVolumeManager;
import com.hypixel.hytale.builtin.triggervolumes.manager.VolumeEntry;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.math.Axis;
import com.hypixel.hytale.math.vector.Transform;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.AbstractCommand;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.prefab.PrefabStore;
import com.hypixel.hytale.server.core.prefab.selection.standard.BlockSelection;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.joml.Vector3i;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.awt.*;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

import static com.gaiagauntlet.gg_server_plugin.lobby.prefabTimer.TimerPrefabUtils.placeBlockSelection;

public class PortalCommand extends AbstractCommand {
    public PortalCommand() {
        super("portal", "Commands related to the portal from the lobby to the game world");

        addSubCommand(new OpenCommand());
        addSubCommand(new CloseCommand());

        addSubCommand(new SetPoiCommand(
            "setorigin",
            "Set the origin location for the portal block to your current location",
            () -> GGConfig.get().getPortalPoi(),
            (GGConfig.GGPoi poi) -> GGConfig.get().setPortalPoi(poi),
            "Portal Origin"
        ));

        ConfigArrayCommand.GetConfigListFunction<String> getPortalTVs = () -> GGConfig.get().getPortalTVs();
        ConfigArrayCommand.SetConfigListFunction<String> setPortalTVs = (String[] tvs) -> GGConfig.get().setPortalTVs(tvs);
        addSubCommand(new ConfigArrayCommand.AppendCommand<>(
            "addtriggervolume", "Add a reference to a trigger volume that will be made active whenever the portal is open",
            "id", "The ID of the trigger volume to be added", ArgTypes.STRING,
            getPortalTVs, setPortalTVs
        ));
        addSubCommand(new ConfigArrayCommand.RemoveCommand<>(
            "removetriggervolume", "Remove a reference to a trigger volume that will be made active whenever the portal is open",
            "id", "The ID of the trigger volume to be removed", ArgTypes.STRING,
            getPortalTVs, setPortalTVs
        ));
        addSubCommand(new ConfigArrayCommand.LogCommand<>(
            "gettriggervolumes", "Returns the list of trigger volumes that will be made active whenever the portal is open",
            getPortalTVs
        ));
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

    @Override
    protected @Nullable CompletableFuture<Void> execute(@NonNull CommandContext var1) {
        return null;
    }

    protected static void setTriggerVolumesEnabled(Store<EntityStore> store, boolean enabled) {
        TriggerVolumesPlugin plugin = TriggerVolumesPlugin.get();
        TriggerVolumeManager manager = store.getResource(plugin.getManagerResourceType());

        String[] portalTVs = GGConfig.get().getPortalTVs();
        for (String portalTV : portalTVs) {
            if (portalTV == null) continue;
            VolumeEntry tv = manager.getVolume(portalTV);
            if (tv == null) continue;
            tv.setEnabled(enabled);
        }
    }
}
