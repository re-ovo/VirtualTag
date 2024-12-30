package me.rerere.virtualtag.tag.team

import com.github.retrooper.packetevents.util.adventure.AdventureSerializer
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerTeams
import me.rerere.virtualtag.tag.VirtualTeam
import me.rerere.virtualtag.util.broadcast
import me.rerere.virtualtag.util.lastChatColor
import me.rerere.virtualtag.util.send
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.entity.Player
import java.util.*

class TeamPacketSender {
    fun createTeam(virtualTeam: VirtualTeam) {
        with(virtualTeam) {
            WrapperPlayServerTeams(
                name,
                WrapperPlayServerTeams.TeamMode.CREATE,
                WrapperPlayServerTeams.ScoreBoardTeamInfo(
                    AdventureSerializer.fromLegacyFormat(name),
                    AdventureSerializer.fromLegacyFormat(prefix),
                    AdventureSerializer.fromLegacyFormat(suffix),
                    WrapperPlayServerTeams.NameTagVisibility.ALWAYS,
                    WrapperPlayServerTeams.CollisionRule.NEVER,
                    lastChatColor(prefix),
                    WrapperPlayServerTeams.OptionData.NONE
                ),
                players
            ).broadcast()
        }
    }

    fun createForPlayer(virtualTeam: VirtualTeam, player: Player) {
        with(virtualTeam) {
            WrapperPlayServerTeams(
                name,
                WrapperPlayServerTeams.TeamMode.CREATE,
                WrapperPlayServerTeams.ScoreBoardTeamInfo(
                    AdventureSerializer.fromLegacyFormat(name),
                    AdventureSerializer.fromLegacyFormat(prefix),
                    AdventureSerializer.fromLegacyFormat(suffix),
                    WrapperPlayServerTeams.NameTagVisibility.ALWAYS,
                    WrapperPlayServerTeams.CollisionRule.NEVER,
                    lastChatColor(prefix),
                    WrapperPlayServerTeams.OptionData.NONE
                ),
                players
            ).send(player)
        }
    }

    fun destroyTeam(virtualTeam: VirtualTeam) {
        with(virtualTeam) {
            WrapperPlayServerTeams(
                name,
                WrapperPlayServerTeams.TeamMode.REMOVE,
                null as WrapperPlayServerTeams.ScoreBoardTeamInfo?,
                players
            )
        }.broadcast()

    }

    fun addPlayer(virtualTeam: VirtualTeam, entities: Set<String>) {
        with(virtualTeam) {
            WrapperPlayServerTeams(
                name,
                WrapperPlayServerTeams.TeamMode.ADD_ENTITIES,
                null as WrapperPlayServerTeams.ScoreBoardTeamInfo?,
                entities
            )
        }.broadcast()
    }

    fun removePlayer(virtualTeam: VirtualTeam, entities: Set<String>) {
        with(virtualTeam) {
            WrapperPlayServerTeams(
                name,
                WrapperPlayServerTeams.TeamMode.REMOVE_ENTITIES,
                null as WrapperPlayServerTeams.ScoreBoardTeamInfo?,
                entities
            )
        }.broadcast()
    }
}

val teamPacketSender: TeamPacketSender by lazy {
    TeamPacketSender()
}