package me.rerere.virtualtag.listener

import me.rerere.virtualtag.util.TaskUtil
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
        val updateTask: () -> Unit = {
            virtualTag().tagHandler.sendCurrentNameTags(player)
            virtualTag().tagManager.updatePlayerTag(player)
        }
        if (virtualTag().configModule.mainConfig.asyncUpdate) {
            TaskUtil.asyncTask(updateTask)
        } else {
            updateTask.invoke()
        }

    }

    @EventHandler(priority = EventPriority.LOWEST)
    fun onQuit(event: PlayerQuitEvent) {
        val player = event.player
        val removeTask: () -> Unit = {
            virtualTag().tagManager.playerQuit(player)
            virtualTag().tagHandler.removePlayerTag(player)
        }
        if (virtualTag().configModule.mainConfig.asyncUpdate) {
            TaskUtil.asyncTask(removeTask)
        } else {
            removeTask.invoke()
        }
    }
}