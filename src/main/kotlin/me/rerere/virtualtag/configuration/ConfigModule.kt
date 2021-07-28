package me.rerere.virtualtag.configuration

import me.rerere.virtualtag.configuration.files.MainConfig
import me.rerere.virtualtag.virtualTag

class ConfigModule {
    // Make sure the plugin folder exists
    init {
        virtualTag().dataFolder.let {
            if (!it.exists())
                it.mkdirs()
        }
    }

    // 配置文件
    val mainConfig = MainConfig()
}