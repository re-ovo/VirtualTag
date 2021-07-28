package me.rerere.virtualtag.command.subcommands

import me.rerere.virtualtag.command.SubCommand
import me.rerere.virtualtag.util.coloring
import me.rerere.virtualtag.virtualTag
import org.bukkit.command.CommandSender

class ReloadCommand : SubCommand(
    name = "reload",
    args = "",
    description = "Reload config",
    permission = "virtualtag.reload"
){
    override fun execute(sender: CommandSender, args: Array<String>) {
        virtualTag().reload()
        sender.sendMessage("&aVirtualTag Reloaded".coloring())
    }
}