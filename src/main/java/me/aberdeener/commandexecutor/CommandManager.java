package me.aberdeener.commandexecutor;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Level;

public class CommandManager {

    private Map<String, Method> commands = new HashMap<>();

    public void init() {
        // we need to define our classes manually for now
        List<Class<?>> commandClasses = Arrays.asList(
                HmmCommand.class,
                HuhCommand.class
        );

        // loop through defined classes
        for (Class clazz : commandClasses) {
            // check the methods within said class
            for (Method method : clazz.getMethods()) {
                // if it does not have the @Command annotation, ignore and continue
                if (!method.isAnnotationPresent(Command.class)) {
                    continue;
                }
                // now we need to ensure they have the right parameters in their execute method
                if (method.getParameters().length != 2) {
                    Main.getInstance().getLogger().log(Level.SEVERE, method.toGenericString().replace("public static void ", "") + " annotated as command but parameters count != 2");
                    continue;
                }
                // make sure they have correct parameter for playerOnly boolean
                if (method.getAnnotation(Command.class).playerOnly()) {
                    if (method.getParameters()[0].getType() != Player.class) {
                        Main.getInstance().getLogger().log(Level.SEVERE, method.toGenericString().replace("public static void ", "") + " annotated as playerOnly but found CommandSender.class");
                        continue;
                    }
                } else {
                    if (method.getParameters()[0].getType() != CommandSender.class) {
                        Main.getInstance().getLogger().log(Level.SEVERE, method.toGenericString().replace("public static void ", "") + " annotated as player or console but found Player.class");
                        continue;
                    }
                }
                // make sure they have string[] parameter
                if (method.getParameters()[1].getType() != String[].class) {
                    Main.getInstance().getLogger().log(Level.SEVERE, method.toGenericString().replace("public static void ", "") + " annotated as command but parameter 2's type != String[]");
                    continue;
                }
                // assign all commandNames (aliases) to this method
                Command annotation = method.getAnnotation(Command.class);
                for (String commandName : annotation.commandNames()) {
                    Main.getInstance().getLogger().info("registering " + commandName);
                    commands.put(commandName.toLowerCase(), method);
                }
            }
        }
    }

    public boolean handle(CommandSender sender, String command, String[] args) {
        if (commands.containsKey(command.toLowerCase())) {
            try {
                Method commandMethod = commands.get(command.toLowerCase());
                Command commandAnnotation = commandMethod.getAnnotation(Command.class);
                if (!sender.hasPermission(commandAnnotation.permission())) {
                    sender.sendMessage(ChatColor.RED + "No permission.");
                    return true;
                }
                if (commandAnnotation.playerOnly() && !(sender instanceof Player)) {
                    sender.sendMessage(ChatColor.RED + "This command can be used by only players.");
                    return true;
                }
                commandMethod.invoke(null, sender, args);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
