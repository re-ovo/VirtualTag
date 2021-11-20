package me.rerere.virtualtag.command.subcommands

import me.rerere.virtualtag.command.SubCommand
import me.rerere.virtualtag.util.coloring
import me.rerere.virtualtag.virtualTag
import org.bukkit.command.CommandSender

class DebugCommand :  SubCommand(
    name = "debug",
    args = "",
    description = "Debug mode",
    permission = "virtualtag.debug",
    hide = true
){
    override fun execute(sender: CommandSender, args: Array<String>) {
        virtualTag().tagHandler.virtualTeams.forEach {
            sender.sendMessage("&aTeam &f${it.name}&a:".coloring())
            sender.sendMessage("&7- &bPrefix: &r${it.prefix} &bSuffix: &r${it.suffix}".coloring())
            sender.sendMessage("&7- &bPlayers: &f${it.players.joinToString(",")}".coloring())
        }
    }
}