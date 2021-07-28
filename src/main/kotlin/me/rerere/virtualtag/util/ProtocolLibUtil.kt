package me.rerere.virtualtag.util

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.events.PacketContainer
import org.bukkit.entity.Player

private val protocolManager by lazy {
    ProtocolLibrary.getProtocolManager()
}

inline fun createPacket(packetType: PacketType, builder: PacketContainer.() -> Unit) = PacketContainer(packetType).apply(builder)

fun broadcastPacket(packet: PacketContainer) = protocolManager.broadcastServerPacket(packet)

fun Player.sendPacket(packet: PacketContainer) = protocolManager.sendServerPacket(this, packet)

fun PacketContainer.send(player: Player) = player.sendPacket(this)

fun PacketContainer.broadcast() = broadcastPacket(this)