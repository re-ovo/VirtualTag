package me.rerere.virtualtag.configuration.files

import me.rerere.virtualtag.configuration.ConfigFile
import me.rerere.virtualtag.tag.TagGroup

class MainConfig : ConfigFile("config.yml") {
    val updateInterval = int("general.update_interval", 20).coerceAtLeast(1)
    val multipleNameTags = boolean("general.multiple_name_tags", false)
    val groups = configuration.getConfigurationSection("groups")?.let { section ->
        section.getKeys(false).map {
            val groupSection = section.getConfigurationSection(it)!!
            val name = it
            val permission = groupSection.getString("permission", "")
            val priority = groupSection.getInt("priority", 0)
            val prefix = groupSection.getString("prefix", "")
            val suffix = groupSection.getString("suffix", "")
            TagGroup(
                name = name,
                permission = permission!!,
                priority = priority,
                prefix = prefix!!,
                suffix = suffix!!
            )
        }
    } ?: error("Please add `groups` section into config.yml!")
}