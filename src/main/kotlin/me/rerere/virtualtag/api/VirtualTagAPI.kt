package me.rerere.virtualtag.api

import me.rerere.virtualtag.virtualTag
import org.bukkit.entity.Player

object VirtualTagAPI {
    @JvmStatic
    @Suppress("unused")
    fun setPlayerTag(player: Player, prefix: String, suffix: String) {
        virtualTag().tagHandler.setPlayerTag(
            player, Tag(
                prefix = prefix,
                suffix = suffix
            )
        )
    }

    @JvmStatic
    @Suppress("unused")
    fun removePlayerTag(player: Player) {
        virtualTag().tagHandler.removePlayerTag(player)
    }

    @JvmStatic
    @Suppress("unused")
    fun getPlayerCurrentTag(player: Player): Tag? = virtualTag().tagHandler.getPlayerCurrentTag(player)
}