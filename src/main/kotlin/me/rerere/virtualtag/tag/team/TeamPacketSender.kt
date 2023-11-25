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

    fun addPlayer(virtualTeam: VirtualTeam, entities: Set<String>)

    fun removePlayer(virtualTeam: VirtualTeam, entities: Set<String>)
}

val teamPacketSender: TeamPacketSender = when (nmsVersion()) {
    // 1.17.x, 1.18.x, 1.19.x, 1.20.x
    "v1_17_R1","v1_18_R1", "v1_18_R2", "v1_19_R1", "v1_19_R2", "v1_19_R3", "v1_20_R1", "v1_20_R2"  -> {
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