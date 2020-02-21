package me.aberdeener.commandexecutor;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HuhCommand {

    @Command(
            commandNames = {"huh", "tellallplayers"},
            playerOnly = true,
            permission = "huhcommand.use",
            usage = "/huh <message>"
    )
    public static void execute(Player sender, String[] args) {

        if (args == null) {
            sender.sendMessage("Oh ok");
        }

        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (String s : args) {
            if (first) {
                sb.append(s);
                first = false;
            }
            else sb.append(" " + s);
        }

        for (Player players : Bukkit.getOnlinePlayers()) {
            players.sendMessage(sender.getDisplayName() + " said: " + sb);
        }
        sender.sendMessage(ChatColor.YELLOW + "You " + ChatColor.WHITE + "said: " + ChatColor.RESET +  ChatColor.translateAlternateColorCodes('&', sb.toString()));
    }
}
