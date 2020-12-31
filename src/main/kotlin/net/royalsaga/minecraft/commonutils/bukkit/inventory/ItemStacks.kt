package net.royalsaga.minecraft.commonutils.bukkit.inventory

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * Add items to [Player]'s inventory and drop the leftovers on the ground
 */
fun ItemStack.addToInventory(player: Player) {
    if (this.amount < 1) {
        return
    }

    player.inventory.addItem(this).values.forEach {
        player.location.world.dropItemNaturally(player.location, it)
    }
}