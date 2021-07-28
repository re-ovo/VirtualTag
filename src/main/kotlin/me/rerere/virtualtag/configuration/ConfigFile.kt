package me.rerere.virtualtag.configuration

import me.rerere.virtualtag.util.loadYamlConfig
import me.rerere.virtualtag.virtualTag
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

open class ConfigFile(
    private val configName: String,
) {
    init {
        virtualTag().logger.info("Loading config: $configName")
    }

    private val file = File(virtualTag().dataFolder, configName).apply {
        if (!exists()) {
            virtualTag().saveResource(configName, false)
            virtualTag().logger.info("Generated config file: $configName")
        }
    }

    var configuration: YamlConfiguration = file.loadYamlConfig()

    fun reload() {
        if (!file.exists()) {
            virtualTag().saveResource(configName, false)
        }
        configuration = file.loadYamlConfig()
    }

    fun save() {
        try {
            configuration.save(file)
        } catch (e: Exception) {
            virtualTag().logger.info("Failed to save config: $configName")
        }
    }

    fun boolean(path: String, defaultValue: Boolean = true) = configuration.getBoolean(path, defaultValue)
    fun int(path: String, defaultValue: Int = 0) = configuration.getInt(path, defaultValue)
    fun string(path: String, defaultVal: String = "NOT EXISTS: $path") = configuration.getString(path, defaultVal)!!
    fun stringList(path: String) : List<String> = configuration.getStringList(path)
}