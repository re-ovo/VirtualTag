package me.rerere.virtualtag.tag.team.impl

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.wrappers.WrappedChatComponent
import me.rerere.virtualtag.tag.VirtualTeam
import me.rerere.virtualtag.tag.team.TeamPacketSender
import me.rerere.virtualtag.util.*
import org.bukkit.entity.Player
import java.util.*

class TeamPacketSenderImpl17 : TeamPacketSender {
    init {
        println("code: 1")
    }

    override fun createTeam(virtualTeam: VirtualTeam) {
        with(virtualTeam) {
            createPacket(PacketType.Play.Server.SCOREBOARD_TEAM) {
                // team name
                strings.writeSafely(0, name)

                // team mode
                integers.writeSafely(0, 0)

                // players
                getSpecificModifier(Collection::class.java).writeSafely(0, players)

                // team info
                optionalStructures.readSafely(0).get().apply {
                    chatComponents.apply {
                        // DisplayName
                        writeSafely(0, WrappedChatComponent.fromText(name))
                        // Prefix
                        writeSafely(1, WrappedChatComponent.fromChatMessage(prefix)[0])
                        // Suffix
                        writeSafely(2, WrappedChatComponent.fromChatMessage(suffix)[0])
                    }
                    strings.apply {
                        writeSafely(0, "always")
                        writeSafely(1, "never")
                    }
                    // color
                    getSpecificModifier(ChatFormatConverter.chatFormatClass).writeSafely(
                        0,
                        lastChatColor(prefix).toNmsChatFormat()
                    )
                    // friendly tags
                    integers.writeSafely(0, 0x0)
                }
            }.broadcast()
        }
    }

    override fun createForPlayer(virtualTeam: VirtualTeam, player: Player) {
        with(virtualTeam){
            createPacket(PacketType.Play.Server.SCOREBOARD_TEAM) {
                // team name
                strings.writeSafely(0, name)

                // team mode
                integers.writeSafely(0, 0)

                // players
                getSpecificModifier(Collection::class.java).writeSafely(0, players)

                // team info
                optionalStructures.readSafely(0).get().apply {
                    chatComponents.apply {
                        // DisplayName
                        writeSafely(0, WrappedChatComponent.fromChatMessage(name)[0])
                        // Prefix
                        writeSafely(1, WrappedChatComponent.fromChatMessage(prefix)[0])
                        // Suffix
                        writeSafely(2, WrappedChatComponent.fromChatMessage(suffix)[0])
                    }
                    strings.apply {
                        writeSafely(0, "always")
                        writeSafely(1, "never")
                    }
                    // color
                    getSpecificModifier(ChatFormatConverter.chatFormatClass).writeSafely(
                        0,
                        lastChatColor(prefix).toNmsChatFormat()
                    )
                    // friendly tags
                    integers.writeSafely(0, 0x0)
                }
            }.send(player)
        }
    }

    override fun destroyTeam(virtualTeam: VirtualTeam) {
        with(virtualTeam){
            createPacket(PacketType.Play.Server.SCOREBOARD_TEAM) {
                strings.writeSafely(0, name)
                integers.writeSafely(0, 1)
                optionalStructures.writeSafely(0, Optional.empty())
            }.broadcast()
        }
    }

    override fun addPlayer(virtualTeam: VirtualTeam) {
        with(virtualTeam){
            createPacket(PacketType.Play.Server.SCOREBOARD_TEAM) {
                strings.writeSafely(0, name)
                integers.writeSafely(0, 3)
                getSpecificModifier(Collection::class.java).writeSafely(0, players)
                optionalStructures.writeSafely(0, Optional.empty())
            }.broadcast()
        }
    }

    override fun removePlayer(virtualTeam: VirtualTeam) {
        with(virtualTeam){
            createPacket(PacketType.Play.Server.SCOREBOARD_TEAM) {
                strings.writeSafely(0, name)
                integers.writeSafely(0, 4)
                getSpecificModifier(Collection::class.java).writeSafely(0, players)
                optionalStructures.writeSafely(0, Optional.empty())
            }.broadcast()
        }
    }
}