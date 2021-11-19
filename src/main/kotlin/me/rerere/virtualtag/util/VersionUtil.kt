package me.rerere.virtualtag.util

import org.bukkit.Bukkit

// NMS包名前缀
private const val NMS_PREFIX = "net.minecraft."

// CraftBukkit包名前缀
private val CRAFTBUKKIT_PREFIX by lazy {
    val clazz = Bukkit.getServer().javaClass.name
    clazz.split(".").subList(0, 4).joinToString(".")
}

fun nmsVersion() = Bukkit.getServer().javaClass.name.split(".")[3]

/**
 * 根据类相对路径获得NMS类名
 *
 * @param simpleName 相对路径
 * @return NMS完整类名
 */
fun nmsClassName(simpleName: String): String = "$NMS_PREFIX$simpleName"

/**
 * 根据类相对路径获得CB类名
 *
 * @param simpleName 相对路径
 * @return CB完整类名
 */
fun craftbukkitClassName(simpleName: String): String = "$CRAFTBUKKIT_PREFIX.$simpleName"