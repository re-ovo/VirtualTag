package me.rerere.virtualtag.hook

import me.clip.placeholderapi.PlaceholderAPI
import me.rerere.virtualtag.api.Tag
import org.bukkit.Bukkit
import org.bukkit.entity.Player

object PlaceholderAPIHook {
    val enable = Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null

    fun applyPlaceholder(player: Player, text: String): String = PlaceholderAPI.setPlaceholders(player, text)
}

fun Tag.applyPlaceholderAPI(player: Player) = if (PlaceholderAPIHook.enable) {
    this.apply {
        prefix = PlaceholderAPIHook.applyPlaceholder(player, prefix)
        suffix = PlaceholderAPIHook.applyPlaceholder(player, suffix)
    }
} else this