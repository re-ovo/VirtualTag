package me.rerere.virtualtag.api

import me.rerere.virtualtag.util.coloring

data class Tag(
    var prefix: String,
    var suffix: String
)

fun Tag.colorful() {
    prefix = prefix.coloring()
    suffix = suffix.coloring()
}