package ir.mctracker.core.config

import ir.jeykey.megacore.MegaPlugin
import ir.jeykey.megacore.config.Configurable
import ir.mctracker.core.utilities.Utils.colorize
import java.util.stream.Collectors

class Messages(instance: MegaPlugin?) : Configurable(instance, "messages.yml") {
    override fun init() {
        PREFIX = format(getConfig().getString("prefix")!!)
        VOTE_MESSAGES = format(getConfig().getStringList("vote-messages"))
        NO_PERMISSION = format(getConfig().getString("no-permission")!!)
        CONSOLE_NOT_ALLOWED = format(getConfig().getString("console-not-allowed")!!)
        INVALID_ARG = format(getConfig().getString("invalid-arg")!!)
    }

    private fun format(texts: List<String>): List<String> {
        return texts.stream().map { text: String -> this.format(text) }.collect(Collectors.toList())
    }

    private fun format(text: String): String {
        return colorize(
            text.replace("{prefix}", (if (PREFIX != null) PREFIX else "")!!)
        )
    }

    companion object {
        @JvmField
        var PREFIX: String? = null
        @JvmField
        var VOTE_MESSAGES: List<String>? = null
        @JvmField
        var NO_PERMISSION: String? = null
        @JvmField
        var CONSOLE_NOT_ALLOWED: String? = null
        var INVALID_ARG: String? = null
    }
}