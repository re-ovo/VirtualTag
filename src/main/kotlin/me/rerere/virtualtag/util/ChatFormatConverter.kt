package me.rerere.virtualtag.util

import com.comphenix.protocol.utility.MinecraftReflection
import org.bukkit.ChatColor

object ChatFormatConverter {
    @Suppress("UNCHECKED_CAST")
    val chatFormatClass = MinecraftReflection.getMinecraftClass("EnumChatFormat") as Class<Any>
    val chatFormatResetFields: Array<Any> = chatFormatClass.enumConstants
}

fun ChatColor.toNmsChatFormat() = ChatFormatConverter.chatFormatResetFields[this.ordinal]

fun lastChatColor(text: String) : ChatColor {
    for(index in text.indices.reversed()){
        if(text[index] != ChatColor.COLOR_CHAR){
            val color = ChatColor.getByChar(text[index])
            color?.let {
                if(it.isColor || it == ChatColor.RESET){
                    return it
                }
            }
        }
    }
    return ChatColor.RESET
}