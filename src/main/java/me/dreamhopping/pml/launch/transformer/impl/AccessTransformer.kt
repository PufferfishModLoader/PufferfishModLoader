package me.dreamhopping.pml.launch.transformer.impl

import me.dreamhopping.pml.launch.transformer.ClassTransformer
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.ClassNode

class AccessTransformer : ClassTransformer {
    private val allAccess: Int = Opcodes.ACC_PUBLIC or Opcodes.ACC_PRIVATE or Opcodes.ACC_PROTECTED

    override fun willTransform(name: String) = true

    override fun transformClass(classNode: ClassNode) =
        classNode.apply {
            // Change access for class
            classNode.access = classNode.access or changeAccess(classNode.access)

            // Change access for inner classes
            classNode.innerClasses.forEach { it.access = changeAccess(it.access) }

            // Change access for methods
            classNode.methods.forEach { it.access = changeAccess(it.access) }

            // Change access for fields
            classNode.fields.forEach { it.access = changeAccess(it.access) }
        }

    private fun changeAccess(nodeAccess: Int) =
        if (nodeAccess and allAccess != 0) (nodeAccess and allAccess.inv() or Opcodes.ACC_PUBLIC) else nodeAccess
}