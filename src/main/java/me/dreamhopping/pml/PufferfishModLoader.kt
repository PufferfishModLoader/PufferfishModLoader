package me.dreamhopping.pml

import me.dreamhopping.pml.launch.PMLClientMain
import me.dreamhopping.pml.launch.PMLLauncher
import me.dreamhopping.pml.mods.ModLoader
import java.io.File
import kotlin.io.path.ExperimentalPathApi

/**
 * The main class for PufferfishModLoader
 * Called from the version specific class, PMLEntryPoint
 *
 * @see PMLLauncher
 * @see PMLClientMain
 */
@ExperimentalPathApi
object PufferfishModLoader {
    fun initialize(gameDir: File) {
        ModLoader.addExternalSource(File(gameDir, "mods"), File(gameDir, "pml${File.separator}mods"))

        ModLoader.load()
        Runtime.getRuntime().addShutdownHook(Thread(ModLoader::unload))
    }
}
