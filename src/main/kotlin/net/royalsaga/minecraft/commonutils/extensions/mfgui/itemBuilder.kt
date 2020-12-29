package net.royalsaga.minecraft.commonutils.extensions.mfgui

import me.mattstudios.mfgui.gui.components.ItemBuilder
import net.royalsaga.minecraft.commonutils.extensions.bukkit.color
import net.royalsaga.minecraft.commonutils.extensions.collections.color
import org.bukkit.enchantments.Enchantment

fun ItemBuilder.setName(name: String, color: Boolean, rgb: Boolean = color): ItemBuilder {
    if (color) {
        setName(name.color(rgb))
    } else {
        setName(name)
    }

    return this
}

fun ItemBuilder.setLore(lore: List<String>, color: Boolean, rgb: Boolean = color): ItemBuilder {
    if (color) {
        setLore(lore.color(rgb))
    } else {
        setLore(lore)
    }

    return this
}

fun ItemBuilder.addEnchantments(map: Map<Enchantment, Int>, ignoreLevelRestriction: Boolean = true): ItemBuilder {
    map.forEach { (enchantment, level) -> addEnchantment(enchantment, level, ignoreLevelRestriction) }
    return this
}