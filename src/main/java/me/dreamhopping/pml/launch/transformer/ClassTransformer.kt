package me.dreamhopping.pml.launch.transformer

import org.objectweb.asm.tree.ClassNode

interface ClassTransformer {
    fun willTransform(name: String): Boolean
    fun transformClass(classNode: ClassNode): ClassNode
}
