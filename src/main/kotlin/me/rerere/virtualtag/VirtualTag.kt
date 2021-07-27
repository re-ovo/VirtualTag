package me.rerere.virtualtag

import org.bukkit.plugin.java.JavaPlugin

class VirtualTag : JavaPlugin() {
    init {
        instance = this
    }

    override fun onEnable() {

    }

    override fun onDisable() {

    }

    companion object {
        lateinit var instance: VirtualTag
    }
}

fun virtualTag() = VirtualTag.instance