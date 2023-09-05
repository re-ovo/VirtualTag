package me.rerere.virtualtag.listener

import me.rerere.virtualtag.virtualTag
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class PlayerListener : Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    fun onJoin(event: PlayerJoinEvent) {
        val player = event.player
        virtualTag().tagHandler.sendCurrentNameTags(player)
        virtualTag().tagManager.updatePlayerTag(player)
    }

    @EventHandler(priority = EventPriority.LOWEST)
    fun onQuit(event: PlayerQuitEvent){
        virtualTag().tagManager.playerQuit(event.player)
        virtualTag().tagHandler.removePlayerTag(event.player)
    }
}