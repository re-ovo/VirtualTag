package me.rerere.virtualtag.tag

import me.rerere.virtualtag.api.Tag
import me.rerere.virtualtag.api.colorful
import me.rerere.virtualtag.hook.applyPlaceholderAPI
import me.rerere.virtualtag.util.allPlayers
import me.rerere.virtualtag.util.timerTask
import me.rerere.virtualtag.virtualTag
import org.bukkit.entity.Player
import java.util.*

class VirtualTagManager {
    private val previousTagCache = hashMapOf<UUID, Tag>()
    val task = timerTask(
        interval = virtualTag().configModule.mainConfig.updateInterval.toLong()
    ) {
        updateAll()
    }

    private fun updateAll() {
        allPlayers {
            updatePlayerTag(it)
        }
    }

    fun updatePlayerTag(player: Player) {
        val mainConfig = virtualTag().configModule.mainConfig
        val matchedTags = mainConfig.groups
            .filter {
                it.permission.isBlank() || player.isOp || player.hasPermission(it.permission)
            }
            .sortedByDescending { it.priority }
            .let {
                if (mainConfig.multipleNameTags) {
                    it
                } else {
                    listOf(it.firstOrNull())
                }
            }
        val targetTag = Tag(
            prefix = matchedTags.joinToString(separator = "") { it?.prefix ?: "" },
            suffix = matchedTags.joinToString(separator = "") { it?.suffix ?: "" }
        ).apply {
            colorful()
            applyPlaceholderAPI(player)
        }
        val previousTag = previousTagCache[player.uniqueId]

        // Name tag changed, require update
        if (targetTag != previousTag) {
            previousTagCache[player.uniqueId] = targetTag
            virtualTag().tagHandler.setPlayerTag(player, targetTag)
        }
    }

    fun playerQuit(player: Player) {
        previousTagCache.remove(player.uniqueId)
    }
}