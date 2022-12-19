package ir.mctracker.core.commands;

import ir.mctracker.core.MCTrackerVote;
import ir.mctracker.core.config.Config;
import ir.mctracker.core.config.Messages;
import ir.mctracker.core.utilities.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TrackerCommand implements CommandExecutor {

    private static MCTrackerVote instance;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(Utils.colorize(Messages.PREFIX + "&a&lMCTrackerVote &aplugin By: &cCipher&7 & &cAlijk"));
            sender.sendMessage(Utils.colorize(Messages.PREFIX + "&eWebsite: &bhttps://mctracker.ir"));
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                if (sender.hasPermission("mctracker.commands.reload")) {
                    instance.reloadConfig();
                    sender.sendMessage(Utils.colorize(Messages.PREFIX + "&aConfig Reloaded."));
                }
            }
        } else {
            sender.sendMessage(Utils.colorize(Messages.PREFIX + "&cEntered arg is Not Valid!"));
        }
        return true;
    }

}