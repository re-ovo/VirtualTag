package me.rerere.virtualtag.util

import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.ChatColor

fun lastChatColor(text: String): NamedTextColor {
    for (index in text.indices.reversed()) {
        if (text[index] != ChatColor.COLOR_CHAR) {
            val color = ChatColor.getByChar(text[index])
            color?.let {
                val namedTextColor = NamedTextColor.NAMES.value(it.name.lowercase());
                if (namedTextColor != null) {
                    return namedTextColor
                }
            }
        }
    }
    return NamedTextColor.WHITE
}