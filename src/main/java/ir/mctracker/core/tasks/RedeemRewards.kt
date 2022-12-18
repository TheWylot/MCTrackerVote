package ir.mctracker.core.tasks

import ir.mctracker.api.api.PlayerVoteEvent
import ir.mctracker.api.api.PlayerVoteRewardReceiveEvent
import ir.mctracker.core.config.Config
import ir.mctracker.core.database.models.Vote
import ir.mctracker.core.utilities.Utils.colorize
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable
import java.sql.SQLException

class RedeemRewards : BukkitRunnable() {
    override fun run() {
        for (vote in Vote.getUnredeemed()) {
            val player = Bukkit.getPlayer(vote.username)
            if (player != null) {
                val voteEvent = PlayerVoteEvent(Bukkit.getOfflinePlayer(player.uniqueId))
                Bukkit.getPluginManager().callEvent(voteEvent)
                val rewardReceiveEvent = PlayerVoteRewardReceiveEvent(player.name)
                Bukkit.getPluginManager().callEvent(rewardReceiveEvent)
                if (rewardReceiveEvent.isCancelled) {
                    continue
                }
                for (action in Config.REWARD_ACTIONS!!) {
                    val action = action.replace("{player}", player.name)
                    if (action.startsWith("[message]")) {
                        player.sendMessage(
                            colorize(
                                action.replace("[message]", "").trim { it <= ' ' }
                            )
                        )
                    } else if (action.startsWith("[console]")) {
                        Bukkit.dispatchCommand(
                            Bukkit.getConsoleSender(),
                            colorize(action.replace("[console]", "").trim { it <= ' ' })
                        )
                    } else if (action.startsWith("[player]")) {
                        player.performCommand(
                            colorize(action.replace("[player]", "").trim { it <= ' ' })
                        )
                    } else if (action.startsWith("[broadcast]")) {
                        Bukkit.getServer().broadcastMessage(
                            colorize(
                                action.replace("[broadcast]", "").trim { it <= ' ' }
                            )
                        )
                    }
                }
                try {
                    vote.redeem()
                } catch (e: SQLException) {
                    e.printStackTrace()
                }
            }
        }
    }
}