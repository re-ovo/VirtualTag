package me.rerere.virtualtag.api

import me.rerere.virtualtag.virtualTag
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.entity.Player

@Suppress("unused")
object VirtualTagAPI {
    @JvmStatic
    fun setPlayerTag(player: Player, prefix: String, suffix: String, color: NamedTextColor = NamedTextColor.WHITE) {
        virtualTag().tagHandler.setPlayerTag(
            player, Tag(
                prefix = prefix,
                suffix = suffix,
                color = color
            )
        )
    }

    @JvmStatic
    fun removePlayerTag(player: Player) {
        virtualTag().tagHandler.removePlayerTag(player)
    }

    @JvmStatic
    fun getPlayerCurrentTag(player: Player): Tag? = virtualTag().tagHandler.getPlayerCurrentTag(player)
}