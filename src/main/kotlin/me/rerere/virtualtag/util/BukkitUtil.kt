package me.rerere.virtualtag.util

import net.md_5.bungee.api.ChatColor
import java.util.regex.Matcher
import java.util.regex.Pattern

fun String.coloring(colorCode: Char = '&'): String = ChatColor.translateAlternateColorCodes(colorCode, this.toHex())

fun List<String>.coloring(colorCode: Char = '&'): List<String> = this.map { it.coloring(colorCode) }

private val PATTERN = Pattern.compile("(?<!\\\\)(#[a-fA-F0-9]{6})")

private fun String.toHex(): String {
    var text = this
    var matcher: Matcher = PATTERN.matcher(text)
    while (matcher.find()) {
        val color = text.substring(matcher.start(), matcher.end())
        text = text.replace(color, ChatColor.of(color).toString())
        matcher = PATTERN.matcher(text) // 更新matcher
    }
    return text
}