package me.rerere.virtualtag.util

import com.github.retrooper.packetevents.PacketEvents
import com.github.retrooper.packetevents.wrapper.PacketWrapper
import org.bukkit.entity.Player

fun broadcastPacket(packet: PacketWrapper<*>) = allPlayers { player -> player.sendPacket(packet) }

fun Player.sendPacket(packet: PacketWrapper<*>) = PacketEvents.getAPI().playerManager.sendPacket(this, packet)

fun PacketWrapper<*>.send(player: Player) = player.sendPacket(this)

fun PacketWrapper<*>.broadcast() = broadcastPacket(this)