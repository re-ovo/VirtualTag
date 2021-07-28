package me.rerere.virtualtag.api

import me.rerere.virtualtag.virtualTag
import org.bukkit.entity.Player

@Suppress("unused")
object VirtualTagAPI {
    @JvmStatic
    fun setPlayerTag(player: Player, prefix: String, suffix: String) {
        virtualTag().tagHandler.setPlayerTag(
            player, Tag(
                prefix = prefix,
                suffix = suffix
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