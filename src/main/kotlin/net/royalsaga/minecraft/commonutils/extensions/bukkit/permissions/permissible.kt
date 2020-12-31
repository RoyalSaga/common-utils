package net.royalsaga.minecraft.commonutils.extensions.bukkit.permissions

import org.bukkit.permissions.Permissible

/**
 * Check if a [Permissible] has all permissions or at least one
 * @param permissions permissions to check
 * @param all true if the [Permissible] should have all permissions otherwise false for at least one
 */
fun Permissible.hasPermission(vararg permissions: String, all: Boolean = true): Boolean {
    return if (all) permissions.all(this::hasPermission) else permissions.any(this::hasPermission)
}