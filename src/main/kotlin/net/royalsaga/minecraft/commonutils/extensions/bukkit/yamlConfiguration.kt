package net.royalsaga.minecraft.commonutils.extensions.bukkit

import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.io.IOException

fun YamlConfiguration.saveToFile(file: File) {
    try {
        this.save(file)
    } catch (e: IOException) {
        println("An error occurred while saving ${file.path}")
    }
}