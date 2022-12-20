package ir.mctracker.core.commands

import ir.mctracker.api.api.PlayerPreVoteEvent
import ir.mctracker.core.config.Config
import ir.mctracker.core.config.Messages
import ir.mctracker.core.utilities.Utils.colorize
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class VoteCommand : CommandExecutor {
    private val VOTE_PERMISSION = "mctracker.commands.vote"
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage(Messages.CONSOLE_NOT_ALLOWED)
            return true
        }
        val p = sender
        val playerPreVoteEvent = PlayerPreVoteEvent(p)
        Bukkit.getPluginManager().callEvent(playerPreVoteEvent)
        if (playerPreVoteEvent.isCancelled) {
            return false
        }
        if (args.size == 0) {
            if (sender.hasPermission(VOTE_PERMISSION) || !Config.VOTE_NEEDS_PERMISSION) {
                for (s in Messages.VOTE_MESSAGES!!) {
                    sender.sendMessage(colorize(s.replace("{player}", p.name).replace("{vote_url}", Config.VOTE_URL!!)))
                }
            } else {
                sender.sendMessage(Messages.NO_PERMISSION)
            }
        }
        return true
    }
}