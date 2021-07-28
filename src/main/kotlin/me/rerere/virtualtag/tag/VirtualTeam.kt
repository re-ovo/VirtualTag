package me.rerere.virtualtag.tag

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.wrappers.WrappedChatComponent
import me.rerere.virtualtag.util.*
import me.rerere.virtualtag.virtualTag
import java.util.*


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
        createPacket(PacketType.Play.Server.SCOREBOARD_TEAM){
            strings.writeSafely(0, name)
            integers.writeSafely(0, 3)
            getSpecificModifier(Collection::class.java).writeSafely(0, players)
            optionalStructures.writeSafely(0, Optional.empty())
        }.broadcast()
    }

    fun removePlayer(player: String) {
        players -= player
        createPacket(PacketType.Play.Server.SCOREBOARD_TEAM){
            strings.writeSafely(0, name)
            integers.writeSafely(0, 4)
            getSpecificModifier(Collection::class.java).writeSafely(0, players)
            optionalStructures.writeSafely(0, Optional.empty())
        }.broadcast()
    }

    private fun create() {
        createPacket(PacketType.Play.Server.SCOREBOARD_TEAM){
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
                    writeSafely(1, WrappedChatComponent.fromText(prefix))
                    // Suffix
                    writeSafely(2, WrappedChatComponent.fromText(suffix))
                }
                strings.apply {
                    writeSafely(0,"always")
                    writeSafely(1, "never")
                }
                // color
                getSpecificModifier(ChatFormatConverter.chatFormatClass).writeSafely(0, lastChatColor(prefix).toNmsChatFormat())
                // friendly tags
                integers.writeSafely(0, 0x0)
            }
        }.broadcast()
    }

    fun destroy() {
        virtualTag().logger.info("Removed team: $name")
        createPacket(PacketType.Play.Server.SCOREBOARD_TEAM){
            strings.writeSafely(0, name)
            integers.writeSafely(0, 1)
            optionalStructures.writeSafely(0, Optional.empty())
        }.broadcast()
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