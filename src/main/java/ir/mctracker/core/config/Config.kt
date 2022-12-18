package ir.mctracker.core.config

import ir.jeykey.megacore.MegaPlugin
import ir.jeykey.megacore.config.Configurable

class Config(instance: MegaPlugin?) : Configurable(instance, "config.yml") {
    override fun init() {
        SERVER_ID = getConfig().getInt("server-id")
        VOTE_NEEDS_PERMISSION = getConfig().getBoolean("vote-needs-permission")
        VOTE_URL = "https://mctracker.ir/server/" + SERVER_ID + "/vote"
        API_ENDPOINT = "https://mctracker.ir/api/server/" + SERVER_ID + "/votes"
        CYCLE = getConfig().getInt("cycle") * 60 * 20
        REWARD_ACTIONS = getConfig().getStringList("reward-actions")
    }

    companion object {
        @JvmField
        var SERVER_ID: Int? = null
        @JvmField
        var VOTE_NEEDS_PERMISSION = false
        @JvmField
        var VOTE_URL: String? = null
        @JvmField
        var API_ENDPOINT: String? = null
        @JvmField
        var CYCLE: Int? = null
        @JvmField
        var REWARD_ACTIONS: List<String>? = null
    }
}