package me.rerere.virtualtag.command

import me.rerere.virtualtag.command.subcommands.GroupsCommand
import me.rerere.virtualtag.command.subcommands.ReloadCommand
import me.rerere.virtualtag.util.coloring
import me.rerere.virtualtag.virtualTag
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class VirtualTagCommand : CommandExecutor,TabCompleter {
    private val subCommands: List<SubCommand> = listOf(
        GroupsCommand(),
        ReloadCommand()
    )

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (args.isEmpty()) {
            sender.sendMessage("&6VirtualTag &7- &3(${virtualTag().description.version}&7/&3${Bukkit.getVersion()})".coloring())
            subCommands.filter { sender.hasPermission(it.permission) && !it.hide }.forEach {
                sender.sendMessage("/${label.lowercase()} %1\$s &b%2\$s &7- &e%3\$s".format(it.name, it.args,it.description).coloring())
            }
        } else {
            val subCommand = subCommands.find { it.name == args[0] }
            subCommand?.let {
                if (sender.hasPermission(it.permission)) {
                    try {
                        subCommand.execute(sender, args.copyOfRange(1, args.size))
                    }catch (e: Exception){
                        e.printStackTrace()
                        sender.sendMessage("&cFailed to execute the subcommand: ${subCommand.name}".coloring())
                    }
                } else {
                    sender.sendMessage("&cYou don't have permission: ${subCommand.permission}".coloring())
                }
            } ?: run {
                sender.sendMessage("&cCan't find subcommand: ${args[0]}".coloring())
            }
        }
        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>
    ): List<String>? {
        fun String.toList() = listOf(this)
        if(alias.isEmpty()){
            return this.subCommands.map { it.name }
        }
        if(args.size == 1) {
            return this.subCommands.find { it.name.startsWith(args[0])  }?.name?.toList()
                ?: this.subCommands.map { it.name }
        }
        return null
    }
}

abstract class SubCommand(
    val name: String,
    val args: String,
    val description: String,
    val permission: String,
    val hide : Boolean = false
) {
    abstract fun execute(sender: CommandSender, args: Array<String>)
}