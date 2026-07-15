package com.gaiagauntlet.gg_server_plugin;

import com.gaiagauntlet.gg_server_plugin.commands.GaiaGauntletCommand;
import com.gaiagauntlet.gg_server_plugin.lobby.prefabTimer.systems.UpdateTimerSystem;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.util.Config;

import java.util.logging.Level;

public class GGServerPlugin extends JavaPlugin {
    protected final Config<GGConfig> config = this.withConfig("GGConfig", GGConfig.CODEC);
    private static GGServerPlugin INSTANCE;

    public static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();

    public GGServerPlugin(JavaPluginInit init) {
        super(init);
        INSTANCE = this;
        config.load();
    }

    public static GGServerPlugin get() {return INSTANCE;}

    @Override
    protected void start() {
        LOGGER.at(Level.INFO).log("Starting GG Server Plugin!");
        this.getEntityStoreRegistry().registerSystem(new UpdateTimerSystem());
    }

    @Override
    protected void setup() {
        LOGGER.at(Level.INFO).log("Setting up GG Server Plugin!");
        config.save(); // Ensures the config file is created if it doesn't exist
        this.getCommandRegistry().registerCommand(new GaiaGauntletCommand());
    }

    @Override
    protected void shutdown() {
        LOGGER.at(Level.INFO).log("Shutting down GG Server Plugin!");
    }
}
