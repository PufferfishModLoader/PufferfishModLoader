package me.dreamhopping.pml.launch

import me.dreamhopping.pml.launch.loader.PMLClassLoader
import java.lang.reflect.InvocationTargetException

object PMLClientMain {
    @JvmStatic
    fun main(args: Array<String>) {
        val classLoader = PMLClassLoader()
        Thread.currentThread().contextClassLoader = classLoader

        val entryPointClass = classLoader.loadClass("me.dreamhopping.pml.launch.PMLLauncher")
        val startMethod =
            entryPointClass.getDeclaredMethod("start", Array<String>::class.java, Boolean::class.javaPrimitiveType)

        try {
            startMethod.invoke(null, args, true)
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }
    }
}
