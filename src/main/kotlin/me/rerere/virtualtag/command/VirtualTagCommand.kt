package me.rerere.virtualtag.command

import me.rerere.virtualtag.api.Tag
import me.rerere.virtualtag.util.coloring
import me.rerere.virtualtag.virtualTag
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class VirtualTagCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, param: Array<out String>): Boolean {
        if (param.isEmpty()) {
            // Show help
        } else {
            when (param[0].lowercase()) {
                "set" -> {
                    Bukkit.getScheduler().runTaskTimer(virtualTag(), Runnable {
                        virtualTag().tagHandler.setPlayerTag(
                            sender as Player, Tag(
                                "&a[${Math.random()}]&r ".coloring(), ""
                            )
                        )
                    }, 0, 1)
                }
            }
        }
        return true
    }
}