package me.rerere.virtualtag

import me.rerere.virtualtag.command.VirtualTagCommand
import me.rerere.virtualtag.listener.PlayerListener
import me.rerere.virtualtag.tag.VirtualTagHandler
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class VirtualTag : JavaPlugin() {
    init {
        instance = this
    }

    lateinit var tagHandler: VirtualTagHandler

    override fun onEnable() {
        logger.info("Start loading VirtualTag...")

        tagHandler = VirtualTagHandler()

        // Register Listeners
        Bukkit.getPluginManager().apply {
            registerEvents(PlayerListener(), this@VirtualTag)
        }

        // Register Commands
        Bukkit.getPluginCommand("virtualtag")?.setExecutor(VirtualTagCommand())
    }

    override fun onDisable() {

    }

    companion object {
        lateinit var instance: VirtualTag
    }
}

fun virtualTag() = VirtualTag.instance