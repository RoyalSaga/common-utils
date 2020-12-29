package net.royalsaga.minecraft.commonutils.extensions.bukkit

import net.md_5.bungee.api.ChatColor
import org.apache.commons.lang.StringUtils
import java.util.regex.Pattern

private val hexPattern = Pattern.compile(
        "<(#[a-f0-9]{6}|aqua|black|blue|dark_(aqua|blue|gray|green|purple|red)|gray|gold|green|light_purple|red|white|yellow)>",
        Pattern.CASE_INSENSITIVE
)

fun String.color(rgb: Boolean = true): String {
    if (isBlank()) {
        return this
    }

    if (!rgb) {
        return ChatColor.translateAlternateColorCodes('&', this)
    }

    val matcher = hexPattern.matcher(this)
    var str = this

    while (matcher.find()) {
        try {
            val chatColor = ChatColor.of(matcher.group(1))

            if (chatColor != null) {
                str = StringUtils.replace(str, matcher.group(), chatColor.toString())
            }
        } catch (ignored: IllegalArgumentException) { }
    }

    return ChatColor.translateAlternateColorCodes('&', str)
}

/*
fun String.setPlaceholders(player: Player, color: Boolean = true): String {
    return PlaceholderAPI.setPlaceholders(player, this, color)
}

fun String.setPlaceholders(player: OfflinePlayer, color: Boolean = true): String {
    return PlaceholderAPI.setPlaceholders(player, this, color)
}
 */