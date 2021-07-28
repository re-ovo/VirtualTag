package me.rerere.virtualtag.listener

import me.rerere.virtualtag.tag.Tag
import me.rerere.virtualtag.util.coloring
import me.rerere.virtualtag.virtualTag
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerListener : Listener {
    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        val player = event.player
        virtualTag().tagHandler.setPlayerTag(
            player, Tag(
                prefix = "&a&l[测试Prefix, 长度测试测试微积分的北覅吧fi就brio]".coloring(),
                suffix = "&e[测试Suffix]".coloring()
            )
        )
    }
}