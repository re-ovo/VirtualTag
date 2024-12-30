package me.rerere.virtualtag.tag

import net.kyori.adventure.text.format.NamedTextColor

class TagGroup(
    val name: String,
    val permission: String,
    val priority: Int,
    val prefix: String,
    val suffix: String,
    val color: NamedTextColor
)