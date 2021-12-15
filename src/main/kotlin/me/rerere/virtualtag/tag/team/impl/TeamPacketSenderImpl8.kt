package me.rerere.virtualtag.tag.team.impl

import com.comphenix.protocol.PacketType
import me.rerere.virtualtag.tag.VirtualTeam
import me.rerere.virtualtag.tag.team.TeamPacketSender
import me.rerere.virtualtag.util.broadcast
import me.rerere.virtualtag.util.createPacket
import me.rerere.virtualtag.util.lastChatColor
import me.rerere.virtualtag.util.send
import org.bukkit.entity.Player

class TeamPacketSenderImpl8 : TeamPacketSender {
    override fun createTeam(virtualTeam: VirtualTeam) {
        with(virtualTeam) {
            createPacket(PacketType.Play.Server.SCOREBOARD_TEAM) {
                // team mode
                integers.apply {
                    writeSafely(0,  lastChatColor(prefix).ordinal)
                    writeSafely(1, 0)
                    writeSafely(2, 0)
                }

                // players
                getSpecificModifier(Collection::class.java).writeSafely(0, players.toMutableList())

                // team info
                strings.apply {
                    writeSafely(0, name)
                    writeSafely(1, name)
                    writeSafely(2, prefix)
                    writeSafely(3, suffix)
                    writeSafely(4,"always")
                }
            }.broadcast()
        }
    }

    override fun createForPlayer(virtualTeam: VirtualTeam, player: Player) {
        with(virtualTeam){
            createPacket(PacketType.Play.Server.SCOREBOARD_TEAM) {
                // team mode
                integers.apply {
                    writeSafely(0,  lastChatColor(prefix).ordinal)
                    writeSafely(1, 0)
                    writeSafely(2, 0)
                }

                // players
                getSpecificModifier(Collection::class.java).writeSafely(0, players.toMutableList())

                // team info
                strings.apply {
                    writeSafely(0, name)
                    writeSafely(1, name)
                    writeSafely(2, prefix)
                    writeSafely(3, suffix)
                    writeSafely(4,"always")
                }
            }.send(player)
        }
    }

    override fun destroyTeam(virtualTeam: VirtualTeam) {
        with(virtualTeam){
            createPacket(PacketType.Play.Server.SCOREBOARD_TEAM) {
                strings.writeSafely(0, name)
                integers.writeSafely(1, 1) // mode

                // default packet data
                strings.writeSafely(4, "always")
                integers.writeSafely(0, -1)
                getSpecificModifier(Collection::class.java).writeSafely(0, ArrayList<String>())
                integers.writeSafely(2, 0)
            }.broadcast()
        }
    }

    override fun addPlayer(virtualTeam: VirtualTeam, entities: Set<String>) {
        with(virtualTeam){
            createPacket(PacketType.Play.Server.SCOREBOARD_TEAM) {
                strings.apply {
                    writeSafely(0, name) // team name
                    writeSafely(4, "always") // visible
                }

                integers.apply {
                    integers.writeSafely(1, 3) // mode
                    writeSafely(0, -1) // color
                    writeSafely(2, 0) // packData
                }

                getSpecificModifier(Collection::class.java).writeSafely(0, entities.toMutableList())
            }.broadcast()
        }
    }

    override fun removePlayer(virtualTeam: VirtualTeam, entities: Set<String>) {
        with(virtualTeam){
            createPacket(PacketType.Play.Server.SCOREBOARD_TEAM) {
                strings.apply {
                    writeSafely(0, name) // team name
                    writeSafely(4, "always") // visible
                }

                integers.apply {
                    integers.writeSafely(1, 4) // mode
                    writeSafely(0, -1) // color
                    writeSafely(2, 0) // packData
                }

                getSpecificModifier(Collection::class.java).writeSafely(0, entities.toMutableList())
            }.broadcast()
        }
    }
}