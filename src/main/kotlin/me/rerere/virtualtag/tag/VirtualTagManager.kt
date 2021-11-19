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

    // Cache the tag when the player uploads the update to prevent frequent tag updates
    private val previousTagCache = hashMapOf<UUID, Tag>()

    // Ensure that the tag can be updated every certain time even though it has not changed
    private val noUpdateTicks = hashMapOf<UUID, Int>()

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
        if (targetTag != previousTag || (noUpdateTicks[player.uniqueId] ?: 0) > 100) {
            previousTagCache[player.uniqueId] = targetTag
            noUpdateTicks[player.uniqueId] = 0

            virtualTag().tagHandler.setPlayerTag(player, targetTag)
        } else {
            noUpdateTicks[player.uniqueId] = noUpdateTicks[player.uniqueId]?.plus(1) ?: 1
        }
    }

    fun playerQuit(player: Player) {
        previousTagCache -= player.uniqueId
        noUpdateTicks -= player.uniqueId
    }
}