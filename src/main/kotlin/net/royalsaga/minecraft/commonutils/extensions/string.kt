package net.royalsaga.minecraft.commonutils.extensions

import com.google.common.base.Enums
import me.mattstudios.mfgui.gui.components.ItemBuilder
import me.mattstudios.mfgui.gui.components.xseries.XMaterial
import net.md_5.bungee.api.ChatColor
import net.royalsaga.minecraft.commonutils.extensions.java.util.regex.get
import net.royalsaga.minecraft.commonutils.extensions.mfgui.addEnchantments
import org.apache.commons.lang.StringUtils
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
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

private val keyValuePattern = Pattern.compile("(?<key>\\w+):(?<value>.+)")

/**
 * Attempt to turn the given string into an [ItemStack]
 * @param rgb if the RGB colors from name and lore should be replaced
 */
fun String.toItemStack(rgb: Boolean = true): ItemStack? {
    val args = split(" ")

    if (args.isEmpty()) {
        return null
    }

    var material: Material? = null
    var amount = 0

    var name = ""
    var lore = emptyList<String>()
    var itemFlags = emptyArray<ItemFlag>()
    val enchantments: MutableMap<Enchantment, Int> = mutableMapOf()
    var unbreakable = false
    var texture = ""

    for (arg in args) {
        if (material == null) {
            material = XMaterial.matchXMaterial(arg).orElse(null)?.parseMaterial() ?: continue
            continue
        }

        if (amount == 0) {
            amount = arg.toIntOrNull() ?: 0
            continue
        }

        val matcher = keyValuePattern.matcher(arg)

        if (!matcher.matches()) {
            continue
        }

        val key = matcher["key"]
        val value = matcher["value"]

        when (key) {
            "name" -> {
                name = StringUtils.replace(value, "_", " ").color(rgb)
            }

            "lore" -> {
                lore = StringUtils.replace(value, "_", " ").split("(?<!\\\\)\\|")
                    .map { StringUtils.replace(it, "_", " ").color(rgb) }
                    .toList()
            }

            "flags" -> {
                itemFlags = value.split(",")
                    .mapNotNull { Enums.getIfPresent(ItemFlag::class.java, it).orNull() }
                    .toTypedArray()
            }

            "unbreakable" -> {
                unbreakable = value.toBoolean()
            }

            "texture" -> {
                texture = value
            }

            else -> {
                Enchantment.getByName(key)?.let {
                    val level = value.toIntOrNull() ?: return@let
                    enchantments[it] = level
                }
            }
        }
    }

    if (material == null) {
        return null
    }

    return ItemBuilder.from(material)
        .setAmount(amount)
        .setName(name)
        .setLore(lore)
        .addItemFlags(*itemFlags)
        .addEnchantments(enchantments)
        .setUnbreakable(unbreakable)
        .setSkullTexture(texture)
        .build()
}

/*
fun String.setPlaceholders(player: Player, color: Boolean = true): String {
    return PlaceholderAPI.setPlaceholders(player, this, color)
}

fun String.setPlaceholders(player: OfflinePlayer, color: Boolean = true): String {
    return PlaceholderAPI.setPlaceholders(player, this, color)
}
 */