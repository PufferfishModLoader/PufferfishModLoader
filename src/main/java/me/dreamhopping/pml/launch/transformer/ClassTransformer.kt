package me.dreamhopping.pml.launch.transformer

import org.objectweb.asm.tree.ClassNode

interface ClassTransformer {
    fun transformClass(classNode: ClassNode): ClassNode
}
