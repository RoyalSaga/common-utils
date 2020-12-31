package net.royalsaga.minecraft.commonutils.extensions.collections

import net.royalsaga.minecraft.commonutils.extensions.color
import net.royalsaga.minecraft.commonutils.extensions.toItemStack
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

fun List<String>.color(rgb: Boolean = true): List<String> {
    return this.map { it.color(rgb) }.toList()
}

fun List<String>.toItemsList(rgb: Boolean = true): List<ItemStack> {
    return this.mapNotNull { it.toItemStack(rgb) }.toList()
}

fun List<ItemStack>.addToInventory(player: Player) {
    val items = this.filter { it.amount > 0 && !it.type.isAir }.toTypedArray()

    player.inventory.addItem(*items).values.forEach {
        player.world.dropItemNaturally(player.location, it)
    }
}