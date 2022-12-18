package ir.mctracker.core.commands;

import ir.mctracker.api.api.PlayerPreVoteEvent;
import ir.mctracker.core.config.Config;
import ir.mctracker.core.config.Messages;
import ir.mctracker.core.utilities.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VoteCommand implements CommandExecutor {
    private String VOTE_PERMISSION = "mctracker.commands.vote";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Messages.CONSOLE_NOT_ALLOWED);
            return true;
        }

        Player p = (Player) sender;

        PlayerPreVoteEvent playerPreVoteEvent = new PlayerPreVoteEvent(p);
        Bukkit.getPluginManager().callEvent(playerPreVoteEvent);
        if (playerPreVoteEvent.isCancelled()) {
            return false;
        }

        if (args.length == 0) {
            if (sender.hasPermission(VOTE_PERMISSION) || !Config.VOTE_NEEDS_PERMISSION) {
                for (String s : Messages.VOTE_MESSAGES) {
                    sender.sendMessage(Utils.colorize(s.replace("{player}", p.getName()).replace("{vote_url}", Config.VOTE_URL)));
                }
            } else {
                sender.sendMessage(Messages.NO_PERMISSION);
            }
        }
        return true;
    }
}
