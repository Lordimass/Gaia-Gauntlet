package com.gaiagauntlet.gg_server_plugin.commands;

import com.gaiagauntlet.gg_server_plugin.GGConfig;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.AbstractCommand;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.CompletableFuture;


public abstract class ConfigArrayCommand<T> extends AbstractCommand {
    final GetConfigListFunction<T> getConfigListFunction;
    final SetConfigListFunction<T> setConfigListFunction;

    public ConfigArrayCommand(
        @Nullable String name,
        @Nullable String description,
        GetConfigListFunction<T> getConfigListFunction,
        SetConfigListFunction<T> setConfigListFunction
    ) {
        super(name, description);
        this.getConfigListFunction = getConfigListFunction;
        this.setConfigListFunction = setConfigListFunction;
    }

    HashSet<T> getConfigArrayAsSet() {
        T[] configArray = getConfigListFunction.apply();
        return new HashSet<>(Arrays.stream(configArray).toList());
    }

    @FunctionalInterface
    public interface GetConfigListFunction<T> {
        T[] apply();
    }

    @FunctionalInterface
    public interface SetConfigListFunction<T> {
        void apply(T[] configList);
    }

    public static class AppendCommand<T> extends ConfigArrayCommand<T> {
        RequiredArg<T> toAppend;
        String argName;
        String argDescription;
        ArgumentType<T> argType;

        public AppendCommand(@Nullable String name, @Nullable String description, String argName, String argDescription, ArgumentType<T> argType, GetConfigListFunction<T> getConfigListFunction, SetConfigListFunction<T> setConfigListFunction) {
            super(name, description, getConfigListFunction, setConfigListFunction);
            this.argName = argName;
            this.argDescription = argDescription;
            this.argType = argType;
            toAppend = this.withRequiredArg(argName, argDescription, argType);
        }

        @Override
        protected @Nullable CompletableFuture<Void> execute(@NonNull CommandContext commandContext) {
            T[] configArray = getConfigListFunction.apply();
            T itemToAppend = toAppend.get(commandContext);
            HashSet<T> configList = getConfigArrayAsSet();
            configList.add(itemToAppend);
            T[] newConfigArray = configList.toArray(configArray);
            setConfigListFunction.apply(newConfigArray);

            commandContext.sender().sendMessage(Message.raw(configList.toString()));

            GGConfig.getConfig().save();

            return null;
        }
    }

    public static class RemoveCommand<T> extends ConfigArrayCommand<T> {
        RequiredArg<T> toRemove;
        String argName;
        String argDescription;
        ArgumentType<T> argType;

        public RemoveCommand(@Nullable String name, @Nullable String description, String argName, String argDescription, ArgumentType<T> argType, GetConfigListFunction<T> getConfigListFunction, SetConfigListFunction<T> setConfigListFunction) {
            super(name, description, getConfigListFunction, setConfigListFunction);
            this.argName = argName;
            this.argDescription = argDescription;
            this.argType = argType;
            toRemove = this.withRequiredArg(argName, argDescription, argType);
        }

        @Override
        protected @Nullable CompletableFuture<Void> execute(@NonNull CommandContext commandContext) {
            T[] configArray = getConfigListFunction.apply();
            T itemToAppend = toRemove.get(commandContext);
            HashSet<T> configSet = getConfigArrayAsSet();
            configSet.remove(itemToAppend);

            T[] newConfigArray = Arrays.copyOf(configArray, configSet.size());
            setConfigListFunction.apply(newConfigArray);

            commandContext.sender().sendMessage(Message.raw(configSet.toString()));

            GGConfig.getConfig().save();

            return null;
        }
    }

    public static class LogCommand<T> extends ConfigArrayCommand<T> {
        public LogCommand(@Nullable String name, @Nullable String description, GetConfigListFunction<T> getConfigListFunction) {
            super(name, description, getConfigListFunction, null);
        }

        @Override
        protected @Nullable CompletableFuture<Void> execute(@NonNull CommandContext commandContext) {
            HashSet<T> configList = getConfigArrayAsSet();
            commandContext.sender().sendMessage(Message.raw(configList.toString()));
            return null;
        }
    }
}
