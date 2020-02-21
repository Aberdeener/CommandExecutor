package me.aberdeener.commandexecutor;

import lombok.Getter;
import org.bukkit.command.CommandSender;

public class HmmCommand {
    @Getter
    private org.bukkit.command.Command command;

    @Command(
            commandNames = "hmm",
            playerOnly = false,
            permission = "hmmcommand.use",
            usage = "/hmm"
    )
    public static void execute(CommandSender sender, String[] args) {
        sender.sendMessage("you ran the HmmCommand ");
    }
}
