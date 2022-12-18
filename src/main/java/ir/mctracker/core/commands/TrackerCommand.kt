package ir.mctracker.core.commands

import ir.mctracker.core.MCTrackerVote
import ir.mctracker.core.config.Messages
import ir.mctracker.core.utilities.Utils.colorize
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class TrackerCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<String>): Boolean {
        if (args.size == 0) {
            sender.sendMessage(colorize(Messages.PREFIX + "&a&lMCTrackerVote &aplugin By: &cCipher&7 & &cAlijk"))
            sender.sendMessage(colorize(Messages.PREFIX + "&eWebsite: &bhttps://mctracker.ir"))
        } else if (args.size == 1) {
            if (args[0].equals("reload", ignoreCase = true)) {
                if (sender.hasPermission("mctracker.commands.reload")) {
                    MCTrackerVote.getConfigManager().reloadAll()
                    sender.sendMessage(colorize(Messages.PREFIX + "&aConfig Reloaded."))
                }
            }
        } else {
            sender.sendMessage(colorize(Messages.PREFIX + "&cEntered arg is Not Valid!"))
        }
        return true
    }
}