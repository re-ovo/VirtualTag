package me.rerere.virtualtag.api

import me.rerere.virtualtag.util.coloring
import net.kyori.adventure.text.format.NamedTextColor

data class Tag(
    var prefix: String,
    var suffix: String,
    val color: NamedTextColor
)

fun Tag.colorful() {
    prefix = prefix.coloring()
    suffix = suffix.coloring()
}