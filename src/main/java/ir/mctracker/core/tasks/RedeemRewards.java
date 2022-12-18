package ir.mctracker.core.tasks;

import ir.mctracker.api.api.PlayerVoteEvent;
import ir.mctracker.api.api.PlayerVoteRewardReceiveEvent;
import ir.mctracker.core.config.Config;
import ir.mctracker.core.database.models.Vote;
import ir.mctracker.core.utilities.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;

public class RedeemRewards extends BukkitRunnable {
    @Override
    public void run() {
        for (Vote vote : Vote.getUnredeemed()) {
            Player player = Bukkit.getPlayer(vote.getUsername());
            if (player != null) {
                PlayerVoteEvent voteEvent = new PlayerVoteEvent(Bukkit.getOfflinePlayer(player.getUniqueId()));
                Bukkit.getPluginManager().callEvent(voteEvent);
                PlayerVoteRewardReceiveEvent rewardReceiveEvent = new PlayerVoteRewardReceiveEvent(player.getName());
                Bukkit.getPluginManager().callEvent(rewardReceiveEvent);
                if (rewardReceiveEvent.isCancelled()) {
                    continue;
                }

                for (String action : Config.REWARD_ACTIONS) {
                    action = action.replace("{player}", player.getName());

                    if (action.startsWith("[message]")) {
                        player.sendMessage(
                                Utils.colorize(
                                        action.replace("[message]", "").trim()
                                )
                        );
                    } else if (action.startsWith("[console]")) {
                        Bukkit.dispatchCommand(
                                Bukkit.getConsoleSender(),
                                Utils.colorize(action.replace("[console]", "").trim())
                        );
                    } else if (action.startsWith("[player]")) {
                        player.performCommand(
                                Utils.colorize(action .replace("[player]", "").trim())
                        );
                    } else if (action.startsWith("[broadcast]")) {
                        Bukkit.getServer().broadcastMessage(
                                Utils.colorize(
                                        action.replace("[broadcast]", "").trim()
                                )
                        );
                    }
                }

                try {
                    vote.redeem();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
