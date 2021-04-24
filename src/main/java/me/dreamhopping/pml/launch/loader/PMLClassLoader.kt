package me.dreamhopping.pml.launch.loader

import me.dreamhopping.pml.launch.transformer.ClassTransformer
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.tree.ClassNode
import java.io.File
import java.net.URL
import java.net.URLClassLoader
import java.util.*

/**
 * The custom classloader that PML uses
 * This will allow us to transform classes if we wish to do so
 */
class PMLClassLoader : URLClassLoader(emptyArray(), null) {
    private val exclusions =
        mutableListOf(
            "java.",
            "kotlin.",
            "sun.",
            "javax.",
            "argo.",
            "org.objectweb.asm.",
            "me.dreamhopping.pml.launch.loader",
            "me.dreamhopping.pml.launch.transformer"
        )
    private val transformers = mutableListOf<ClassTransformer>()
    private val exportTransformedClass = System.getProperty("exportTransformedClass", "false").toBoolean()
    private val cachedClasses = mutableMapOf<String, Class<*>>()

    override fun loadClass(name: String): Class<*> {
        if (exclusions.any { name.startsWith(it) }) return javaClass.classLoader.loadClass(name)

        val cachedClass = cachedClasses[name]
        if (cachedClass != null) return cachedClass

        val pathName = name.replace(".", "/")
        var bytes = getResourceAsStream("$pathName.class")?.use { it.readBytes() } ?: throw ClassNotFoundException()

        transformers.forEach { transformer ->
            if (!transformer.willTransform(pathName)) return@forEach

            // Setup a classreader and read the class from the bytes
            val classReader = ClassReader(bytes)
            val classNode = ClassNode()
            classReader.accept(classNode, ClassReader.EXPAND_FRAMES)

            // Transform the class and write the bytes
            val classWriter = ClassWriter(ClassWriter.COMPUTE_MAXS)
            transformer.transformClass(classNode).accept(classWriter)

            bytes = classWriter.toByteArray()
        }

        // Write the transformed class if exportTransformedClass is true and the class actually has been transformed
        if (transformers.isNotEmpty() && exportTransformedClass) {
            val transformedFile = File("transformed/${pathName}.class")
            transformedFile.parentFile.mkdirs()
            transformedFile.writeBytes(bytes)
        }

        val clazz = defineClass(name, bytes, 0, bytes.size)
        cachedClasses[name] = clazz

        return clazz
    }

    override fun getResource(name: String): URL? = javaClass.classLoader.getResource(name)
    override fun getResources(name: String?): Enumeration<URL> = javaClass.classLoader.getResources(name)
    fun addTransformer(transformer: ClassTransformer) = transformers.add(transformer)
}