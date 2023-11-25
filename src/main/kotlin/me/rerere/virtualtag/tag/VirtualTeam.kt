package me.rerere.virtualtag.tag

import me.rerere.virtualtag.api.Tag
import me.rerere.virtualtag.tag.team.teamPacketSender
import org.bukkit.entity.Player

class VirtualTeam(
    val name: String,
    val prefix: String,
    val suffix: String
) {
    private var _valid = false // Make sure the team is valid for client

    val players: MutableSet<String> = hashSetOf()

    val tag: Tag
        get() = Tag(prefix, suffix)

    init {
        create()
    }

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
        if(_valid) return

        teamPacketSender.createTeam(this)
        _valid = true
    }

    fun createForPlayer(player: Player) {
        teamPacketSender.createForPlayer(this, player)
    }

    fun destroy() {
        if(!_valid) return

        teamPacketSender.destroyTeam(this)
        _valid = false
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