package me.rerere.virtualtag.util

import me.rerere.virtualtag.virtualTag
import net.md_5.bungee.api.ChatColor
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.regex.Matcher
import java.util.regex.Pattern

fun String.coloring(colorCode: Char = '&'): String =
    ChatColor.translateAlternateColorCodes(colorCode, with(HexColorUtil) { this@coloring.toHex() })

fun List<String>.coloring(colorCode: Char = '&'): List<String> = this.map { it.coloring(colorCode) }

object HexColorUtil {
    private val PATTERN = Pattern.compile("(?<!\\\\)(#[a-fA-F0-9]{6})")

    fun String.toHex(): String {
        var text = this
        var matcher: Matcher = PATTERN.matcher(text)
        while (matcher.find()) {
            val color = text.substring(matcher.start(), matcher.end())
            text = text.replace(color, ChatColor.of(color).toString())
            matcher = PATTERN.matcher(text) // 更新matcher
        }
        return text
    }
}

fun timerTask(delay: Long = 0, interval: Long = 1, task: () -> Unit) = Bukkit.getScheduler().runTaskTimer(
        virtualTag(), Runnable(task), delay, interval
    )

fun asyncTask(task: () -> Unit) = Bukkit.getScheduler().runTaskAsynchronously(virtualTag(), Runnable(task))

inline fun allPlayers(handler: (Player) -> Unit) {
    Bukkit.getOnlinePlayers().forEach {
        handler(it)
    }
}