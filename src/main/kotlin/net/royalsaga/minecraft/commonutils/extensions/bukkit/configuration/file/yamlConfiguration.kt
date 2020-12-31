package net.royalsaga.minecraft.commonutils.extensions.bukkit.configuration.file

import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.io.IOException
import java.util.logging.Level
import java.util.logging.Logger

fun YamlConfiguration.saveToFile(file: File, logger: Logger? = null) {
    try {
        this.save(file)
    } catch (e: IOException) {
        if (logger == null) {
            e.printStackTrace()
        } else {
            logger.log(Level.SEVERE, "An error occurred while saving ${file.path}", e)
        }
    }
}