package me.dreamhopping.pml.launch

import me.dreamhopping.pml.launch.loader.PMLClassLoader
import me.dreamhopping.pml.launch.transformer.impl.AccessTransformer
import org.apache.logging.log4j.LogManager
import java.lang.reflect.InvocationTargetException
import java.util.*

object PMLLauncher {
    private val logger = LogManager.getLogger("PML: Launcher")

    @JvmStatic
    fun start(args: Array<String>?, client: Boolean) {
        val classLoader = javaClass.classLoader as PMLClassLoader
        classLoader.addTransformer(AccessTransformer())

        val properties = Properties()
        PMLLauncher::class.java.getResourceAsStream("/pml.properties").use { stream -> properties.load(stream) }

        val minecraftVersion = properties["version-package-name"]
        logger.info("Starting PufferfishModLoader ($minecraftVersion)")

        val entryPointName = "me.dreamhopping.pml.$minecraftVersion.launch.PMLEntryPoint"
        val entryPointClass = Class.forName(entryPointName, true, Thread.currentThread().contextClassLoader)
        val startMethod =
            entryPointClass.getMethod("start", Array<String>::class.java, Boolean::class.javaPrimitiveType)

        try {
            startMethod.invoke(null, args, !client)
        } catch (e: InvocationTargetException) {
            logger.error("An error occurred when invoking $entryPointClass#start: ", e)
        }
    }
}
