package me.rerere.virtualtag.tag

import me.rerere.virtualtag.api.Tag
import me.rerere.virtualtag.tag.team.teamPacketSender
import org.bukkit.entity.Player

class VirtualTeam(
    val name: String,
    val prefix: String,
    val suffix: String
) {
    val players: MutableSet<String> = hashSetOf()

    val tag: Tag
        get() = Tag(prefix, suffix)

    fun recreate() {
        destroy()
        create()
    }

    fun addPlayer(player: String) {
        players += player
        teamPacketSender.addPlayer(this, setOf(player))
    }

    fun removePlayer(player: String) {
        players -= player
        teamPacketSender.removePlayer(this, setOf(player))
    }

    private fun create() {
        teamPacketSender.createTeam(this)
    }

    fun createForPlayer(player: Player) {
        teamPacketSender.createForPlayer(this, player)
    }

    fun destroy() {
        teamPacketSender.destroyTeam(this)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as VirtualTeam

        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}