package me.rerere.virtualtag.configuration

import me.rerere.virtualtag.util.loadYamlConfig
import me.rerere.virtualtag.virtualTag
import java.io.File

class VirtualTagConfig {
    init {
        virtualTag().dataFolder.takeIf { !it.exists() }?.mkdirs()
    }
    private val file = File(virtualTag().dataFolder, "config.yml").apply {
        if(!exists()){
            virtualTag().saveResource("config.yml", false)
        }
    }
    private val yamlConfig = file.loadYamlConfig()


}