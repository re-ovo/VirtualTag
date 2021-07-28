package me.rerere.virtualtag.listener

import me.rerere.virtualtag.virtualTag
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class PlayerListener : Listener {
    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        val player = event.player
        virtualTag().tagManager.updatePlayerTag(player)
    }

    @EventHandler
    fun onQuit(event: PlayerQuitEvent){
        virtualTag().tagHandler.removePlayerTag(event.player)
    }
}