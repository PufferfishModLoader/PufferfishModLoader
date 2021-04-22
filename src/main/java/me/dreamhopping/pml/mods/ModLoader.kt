package me.dreamhopping.pml.mods

import me.dreamhopping.pml.mods.json.PMLModJson
import me.dreamhopping.pml.util.fromJson
import org.apache.logging.log4j.LogManager
import java.io.File
import java.lang.reflect.Method
import java.net.URL
import java.net.URLClassLoader

object ModLoader {
    private lateinit var addUrlMethod: Method

    private val externalModLocations = mutableListOf<File>()
    private val discoveredMods = mutableListOf<PMLModJson.Entry>()
    private val loadedMods = mutableMapOf<String, Mod>()
    private val logger = LogManager.getLogger("PML: ModLoader")

    init {
        try {
            addUrlMethod = URLClassLoader::class.java.getDeclaredMethod("addURL", URL::class.java)
            addUrlMethod.isAccessible = true
        } catch (t: Throwable) {
            logger.error("An error occurred initializing ModLoader: ", t)
        }
    }

    /**
     * Searches in all external mod locations and on the classpath for a mod
     */
    private fun searchForMods() {
        externalModLocations.forEach {
            it.listFiles()?.forEach { file ->
                if (file.name.endsWith(".jar") || file.name.endsWith(".zip"))
                    addUrlMethod.invoke(file.toURI().toURL(), this::class.java.classLoader)
            }
        }
    }

    /**
     * Searches on the classpath and in external mod locations for a pml-mod.json file
     * @see Mod
     */
    private fun discoverMods() {
        val loader = this.javaClass.classLoader as URLClassLoader

        loader.getResources("pml-mod.json").iterator().forEach { file ->
            val modJson: PMLModJson = file.readText().fromJson() ?: return@forEach
            discoveredMods.addAll(modJson.mods)
        }

        logger.info("Discovered " + discoveredMods.size + " mod(s)!")
    }

    /**
     * Loads all discovered mods
     * The class has an instance created, then the [Mod.initialize] function is called
     * If the specified class is not an instance of [Mod], or the identifier does not match, the mod will be skipped
     */
    private fun loadMods() {
        discoveredMods.forEach {
            val clazz = Class.forName(it.clazz, false, this::class.java.classLoader)
            val instance = clazz.newInstance() as? Mod
                ?: return@forEach logger.warn("${it.clazz} (${it.id}) is not an instance of Mod! Skipping...")

            if (instance.id != it.id)
                return@forEach logger.warn("${it.id} (pml-mod.json) does not match ${instance.id} (${it.clazz})! Skipping...")

            instance.initialize()
            loadedMods[it.id] = instance
        }
    }

    /**
     * Discovers all mods and loads them
     *
     * @see ModLoader.searchForMods
     * @see ModLoader.discoverMods
     */
    fun load() {
        try {
            searchForMods()
            discoverMods()
            loadMods()
        } catch (t: Throwable) {
            logger.error("An error occurred whilst loading mods: ", t)
        }
    }

    /**
     * Unloads all PML Mods
     *
     * @see ModLoader.load
     */
    fun unload() {
        loadedMods.forEach { (_, mod) ->
            mod.unload()
        }
    }

    /**
     * Adds directories as an "external source"
     * This will be checked when [ModLoader.searchForMods] is called
     */
    fun addExternalSource(vararg sources: File) {
        sources.forEach {
            if (!it.isDirectory && it.exists()) return@forEach logger.warn("Skipping invalid source: ${it.absolutePath}")

            it.mkdirs()
            externalModLocations.add(it)
        }
    }
}
