package net.royalsaga.minecraft.commonutils.extensions.collections

import net.royalsaga.minecraft.commonutils.extensions.bukkit.color

fun List<String>.color(rgb: Boolean = true): List<String> {
    return this.map { it.color(rgb) }.toList()
}