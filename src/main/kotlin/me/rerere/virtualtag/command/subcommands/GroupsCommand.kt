package me.rerere.virtualtag.command.subcommands

import me.rerere.virtualtag.command.SubCommand
import me.rerere.virtualtag.util.coloring
import me.rerere.virtualtag.virtualTag
import org.bukkit.command.CommandSender

class GroupsCommand : SubCommand(
    name = "groups",
    args = "",
    description = "Show all groups",
    permission = "virtualtag.groups",
) {
    override fun execute(sender: CommandSender, args: Array<String>) {
        virtualTag().configModule.mainConfig.groups.forEach {
            sender.sendMessage("&7### &6${it.name}&7 ###".coloring())
            sender.sendMessage("&7| &3Priority: &b${it.priority}".coloring())
            sender.sendMessage("&7| &3Permission: &b${it.permission}".coloring())
            sender.sendMessage("&7| &3Prefix: &r${it.prefix}".coloring())
            sender.sendMessage("&7| &3Suffix: &r${it.suffix}".coloring())
        }
    }
}