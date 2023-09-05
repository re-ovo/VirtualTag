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
                integers.apply {
                    // the RESET color index in 1.8 client is -1
                    // but the lastChatColor function will return 21 index, on 1.8 client the max index is 15
                    // this will make client NullPointerException and crash
                    // so just check if the index > 15, if so, set it to -1
                    writeSafely(0, if (lastChatColor(prefix).ordinal > 15) -1 else lastChatColor(prefix).ordinal) // color
                    writeSafely(1, 0) // action - 0 = create team and add players
                    writeSafely(2, 0) // friendlyFlags
                }

                // players
                getSpecificModifier(Collection::class.java).writeSafely(0, players.toMutableList())

                // team info
                strings.apply {
                    writeSafely(0, name)
                    writeSafely(1, name)
                    writeSafely(2, prefix.take(16))
                    writeSafely(3, suffix.take(16))
                    writeSafely(4,"always")
                }
            }.broadcast()
        }
    }

    override fun createForPlayer(virtualTeam: VirtualTeam, player: Player) {
        with(virtualTeam){
            createPacket(PacketType.Play.Server.SCOREBOARD_TEAM) {
                integers.apply {
                    writeSafely(0, if (lastChatColor(prefix).ordinal > 15) -1 else lastChatColor(prefix).ordinal) // color
                    writeSafely(1, 0) // action - 0 = create team and add players
                    writeSafely(2, 0) // friendlyFlags
                }

                // players
                getSpecificModifier(Collection::class.java).writeSafely(0, players.toMutableList())

                // team info
                strings.apply {
                    writeSafely(0, name)
                    writeSafely(1, name)
                    writeSafely(2, prefix.take(16))
                    writeSafely(3, suffix.take(16))
                    writeSafely(4,"always")
                }
            }.send(player)
        }
    }

    override fun destroyTeam(virtualTeam: VirtualTeam) {
        with(virtualTeam){
            createPacket(PacketType.Play.Server.SCOREBOARD_TEAM) {
                strings.writeSafely(0, name)
                integers.writeSafely(1, 1) // action - 1 = remove team

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
                    writeSafely(0, -1) // color
                    writeSafely(1, 3) // action - 3 = add player(s)
                    writeSafely(2, 0) // friendlyFlags
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
                    writeSafely(0, -1) // color
                    writeSafely(1, 4) // action - 4 = remove player(s)
                    writeSafely(2, 0) // friendlyFlags
                }

                getSpecificModifier(Collection::class.java).writeSafely(0, entities.toMutableList())
            }.broadcast()
        }
    }
}