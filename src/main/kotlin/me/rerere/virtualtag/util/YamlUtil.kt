package me.rerere.virtualtag.util

import org.bukkit.configuration.file.YamlConfiguration
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

fun File.loadYamlConfig(): YamlConfiguration = YamlConfiguration.loadConfiguration(
    BufferedReader(
        InputStreamReader(
            FileInputStream(this), StandardCharsets.UTF_8
        )
    )
)