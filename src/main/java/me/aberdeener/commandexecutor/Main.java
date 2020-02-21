package me.aberdeener.commandexecutor;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.logging.Level;

public class Main extends JavaPlugin {

    @Getter
    private static Main instance;
    private CommandManager commandManager = new CommandManager();

    public void onEnable() {
        instance = this;
        getLogger().log(Level.INFO, "cmdex eneabled bro");
        commandManager.init();

    }

    public void onDisable() {
        getLogger().log(Level.INFO, "cmdex disabled bro");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (args.length == 0) {
            return commandManager.handle(sender, cmd.getLabel(), null);
        } else {
            return commandManager.handle(sender, cmd.getLabel(), Arrays.stream(args).skip(0).toArray(String[]::new));
        }
    }
}
