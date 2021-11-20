package me.rerere.virtualtag.tag.team

import me.rerere.virtualtag.tag.VirtualTeam
import me.rerere.virtualtag.tag.team.impl.TeamPacketSenderImpl17
import me.rerere.virtualtag.tag.team.impl.TeamPacketSenderImpl8
import me.rerere.virtualtag.util.nmsVersion
import me.rerere.virtualtag.virtualTag
import org.bukkit.entity.Player

interface TeamPacketSender {
    fun createTeam(virtualTeam: VirtualTeam)

    fun createForPlayer(virtualTeam: VirtualTeam, player: Player)

    fun destroyTeam(virtualTeam: VirtualTeam)

    fun addPlayer(virtualTeam: VirtualTeam)

    fun removePlayer(virtualTeam: VirtualTeam)
}

val teamPacketSender: TeamPacketSender = when (nmsVersion()) {
    // 1.17.x
    "v1_17_R1" -> {
        TeamPacketSenderImpl17()
    }

    // 1.8.8
    "v1_8_R3" -> {
        TeamPacketSenderImpl8()
    }

    else -> {
        error("VirtualTag does not support your server version: ${nmsVersion()}")
    }
}.also {
    virtualTag().logger.info("Loaded packet sender: ${it.javaClass.simpleName}")
}