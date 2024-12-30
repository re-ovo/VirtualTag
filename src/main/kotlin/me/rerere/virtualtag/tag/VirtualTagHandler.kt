package me.rerere.virtualtag.tag

import me.rerere.virtualtag.api.Tag
import me.rerere.virtualtag.virtualTag
import org.bukkit.entity.Player

class VirtualTagHandler {
    val virtualTeams = hashSetOf<VirtualTeam>()

    // Generate a unique team name
    private fun generateTeamName(): String {
        repeat(2000) { id ->
            val name = "virtualteam_$id"
            if (!virtualTeams.any { it.name == name }) {
                return name
            }
        }
        // WTF?
        return "vt_error".also {
            virtualTag().logger.warning("Can't generate a new virtual team name!")
        }
    }

    fun sendCurrentNameTags(player: Player) {
        virtualTeams.forEach {
            it.createForPlayer(player)
        }
    }

    fun setPlayerTag(player: Player, tag: Tag) {
        player.setPlayerListName(null)

        val oldTeam = this.getPlayerCurrentTeam(player)
        oldTeam?.takeIf { it.tag != tag }?.removePlayer(player.name)
        val team = this.getVirtualTeamByTag(tag) ?: VirtualTeam(
            name = this.generateTeamName(),
            prefix = tag.prefix,
            suffix = tag.suffix,
            color = tag.color
        )
        team.recreate()
        team.addPlayer(player.name)
        virtualTeams += team
        this.cleanVirtualTeams()
    }

    fun removePlayerTag(player: Player) {
        this.getPlayerCurrentTeam(player)?.removePlayer(player.name)
        cleanVirtualTeams()
    }

    private fun getPlayerCurrentTeam(player: Player): VirtualTeam? = virtualTeams.find {
        it.players.contains(player.name)
    }

    fun getPlayerCurrentTag(player: Player): Tag? = virtualTeams.find {
        it.players.contains(player.name)
    }?.let {
        Tag(
            it.prefix,
            it.suffix,
            it.color
        )
    }

    private fun cleanVirtualTeams() {
        virtualTeams.removeIf { team ->
            team.players.isEmpty().also {
                if (it) {
                    team.destroy()
                }
            }
        }
    }

    private fun getVirtualTeamByTag(tag: Tag): VirtualTeam? = virtualTeams.find {
        it.tag == tag
    }
}