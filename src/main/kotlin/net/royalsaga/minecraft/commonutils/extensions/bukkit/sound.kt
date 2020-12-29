package net.royalsaga.minecraft.commonutils.extensions.bukkit

import org.bukkit.Sound
import org.bukkit.SoundCategory
import org.bukkit.entity.Player

fun Sound.playFor(player: Player, volume: Float = 1f, pitch: Float = 1f) {
    player.playSound(player.location, this, volume, pitch)
}

fun Sound.playFor(player: Player, category: SoundCategory = SoundCategory.MASTER, volume: Float = 1f, pitch: Float = 1f) {
    player.playSound(player.location, this, category, volume, pitch)
}