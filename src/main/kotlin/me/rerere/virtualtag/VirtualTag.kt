package me.rerere.virtualtag

import me.rerere.virtualtag.command.VirtualTagCommand
import me.rerere.virtualtag.configuration.ConfigModule
import me.rerere.virtualtag.listener.PlayerListener
import me.rerere.virtualtag.tag.VirtualTagHandler
import me.rerere.virtualtag.tag.VirtualTagManager
import me.rerere.virtualtag.util.UpdateChecker
import me.rerere.virtualtag.util.asyncTask
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class VirtualTag : JavaPlugin() {
    init {
        instance = this
    }

    lateinit var configModule: ConfigModule
    lateinit var tagHandler: VirtualTagHandler
    lateinit var tagManager: VirtualTagManager

    override fun onEnable() {
        logger.info("Loading Config Files")
        configModule = ConfigModule()
        logger.info("Loading TagHandler")
        tagHandler = VirtualTagHandler()
        logger.info("Loading TagManager")
        tagManager = VirtualTagManager()

        // Register Listeners
        logger.info("Registering event listeners")
        Bukkit.getPluginManager().apply {
            registerEvents(PlayerListener(), this@VirtualTag)
        }

        // Register Commands
        logger.info("Registering command")
        Bukkit.getPluginCommand("virtualtag")?.setExecutor(VirtualTagCommand())

        logger.info("VirtualTag is successfully loaded!")

        checkUpdate()
    }

    fun reload() {
        logger.info("Reloading VirtualTag...")
        configModule = ConfigModule()
        tagManager.task.apply {
            try {
                cancel()
            } catch (_: Exception) {
            }
        }
        tagManager = VirtualTagManager()
    }

    override fun onDisable() {
        Bukkit.getScheduler().cancelTasks(this)
    }

    private fun checkUpdate() {
        asyncTask {
            logger.info("Checking update...")
            val latest = UpdateChecker.getLatestVersion()
            if (this.description.version != latest) {
                logger.info("(!) Found a update: $latest, you are still using ${description.version}")
            } else {
                logger.info("You are using the latest version!")
            }
        }
    }

    companion object {
        lateinit var instance: VirtualTag
    }
}

// Get plugin instance
fun virtualTag() = VirtualTag.instance