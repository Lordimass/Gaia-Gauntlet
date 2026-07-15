package com.gaiagauntlet.gg_server_plugin;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.math.vector.Transform;
import com.hypixel.hytale.server.core.util.Config;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.annotation.Nullable;

@ToString
public class GGConfig {
    public static final BuilderCodec<GGConfig> CODEC =
        BuilderCodec.builder(GGConfig.class, GGConfig::new)
            .append(
                new KeyedCodec<>("LobbyTimer", GGPoi.CODEC),
                GGConfig::setTimerPoi,
                GGConfig::getTimerPoi
            )
            .documentation("Origin location for the countdown timer before the game starts.")
            .add()
            .append(
                new KeyedCodec<>("PortalBlock", GGPoi.CODEC),
                GGConfig::setTimerPoi,
                GGConfig::getTimerPoi
            )
            .documentation("Origin location for the portal block to be placed on.")
            .add()
            .build();

    @Setter @Getter @Nullable private GGPoi timerPoi;
    @Setter @Getter @Nullable private GGPoi portalPoi;

    public static GGConfig get() {
        return GGServerPlugin.get().config.get();
    }

    public static Config<GGConfig> getConfig() {
        return GGServerPlugin.get().config;
    }

    @ToString
    public static class GGPoi {
        public static final BuilderCodec<GGPoi> CODEC =
            BuilderCodec.builder(GGPoi.class, GGPoi::new)
                .append(
                    new KeyedCodec<>("Coordinates", Transform.CODEC),
                    (obj, val) -> obj.transform = val,
                    GGPoi::getTransform
                )
                .documentation("Where the POI is and what its orientation should be.")
                .add()
                .append(
                    new KeyedCodec<>("World", Codec.STRING),
                    (obj, val) -> obj.worldName = val,
                    GGPoi::getWorldName
                )
                .documentation("The name of the world that this POI is in.")
                .add()
                .build();

        @Getter private Transform transform;
        @Getter private String worldName;

        protected GGPoi() {}
        public GGPoi(Transform transform, String worldName) {
            this.transform = transform;
            this.worldName = worldName;
        }

    }
}
