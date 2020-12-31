package net.royalsaga.minecraft.commonutils.extensions.bukkit.configuration

import com.google.common.base.Enums
import me.mattstudios.mfgui.gui.components.ItemBuilder
import me.mattstudios.mfgui.gui.components.xseries.XMaterial
import net.royalsaga.minecraft.commonutils.extensions.collections.color
import net.royalsaga.minecraft.commonutils.extensions.color
import net.royalsaga.minecraft.commonutils.extensions.mfgui.addEnchantments
import org.bukkit.Material
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

@Suppress("DEPRECATION")
fun ConfigurationSection.toItemStack(rgb: Boolean = false, defaultMaterial: Material = Material.PAPER): ItemStack {
    val flags = getStringList("flags")
        .mapNotNull { Enums.getIfPresent(ItemFlag::class.java, it).orNull() }
        .toTypedArray()

    val enchantments = getStringList("enchatments")
        .mapNotNull {
            val parts = it.split(";")

            if (parts.size < 2) {
                return@mapNotNull null
            }

            val enchantment = Enchantment.getByName(parts[0]) ?: return@mapNotNull null
            val level = parts[1].toIntOrNull() ?: return@mapNotNull null
            return@mapNotNull enchantment to level
        }.toMap()

    return ItemBuilder.from(XMaterial.matchXMaterial(getString("material", "PAPER") ?: "paper").get().parseMaterial() ?: defaultMaterial)
        .setName(getString("displayName")?.color(rgb) ?: "")
        .setLore(getStringList("lore").color(rgb))
        .glow(getBoolean("glow"))
        .setSkullTexture(getString("texture") ?: "")
        .addItemFlags(*flags)
        .addEnchantments(enchantments)
        .setUnbreakable(getBoolean("unbreakable"))
        .build()
}