package ir.mctracker.core.utilities

import ir.mctracker.core.config.Messages
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL
import java.net.UnknownHostException
import javax.net.ssl.HttpsURLConnection

object Utils {

    @JvmStatic
    fun colorize(msg: String?): String {
        return ChatColor.translateAlternateColorCodes('&', msg!!)
    }

    @JvmStatic
    fun sendToConsole(message: String?) {
        Bukkit.getConsoleSender().sendMessage(Messages.PREFIX + colorize(message))
    }

    @JvmStatic
    fun fetchJson(url: String?): String? {
        var con: HttpsURLConnection? = null
        try {
            val br: BufferedReader
            try {
                val u = URL(url)
                con = u.openConnection() as HttpsURLConnection
                con.connect()
                br = BufferedReader(InputStreamReader(con!!.inputStream))
            } catch (exception: UnknownHostException) {
                return null
            } catch (exception: FileNotFoundException) {
                return null
            }
            val sb = StringBuilder()
            var line: String
            while (br.readLine().also { line = it } != null) {
                sb.append(
                    """
    $line
    
    """.trimIndent()
                )
            }
            br.close()
            return sb.toString()
        } catch (ex: IOException) {
            ex.printStackTrace()
        } finally {
            con?.disconnect()
        }
        return null
    }
}