package me.dreamhopping.pml.launch;

import me.dreamhopping.pml.mods.launch.loader.RuntimeTransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InnerClassNode;
import org.objectweb.asm.tree.MethodNode;

public class AccessTransformer implements RuntimeTransformer {
    private final int allAccess = (Opcodes.ACC_PUBLIC | Opcodes.ACC_PRIVATE | Opcodes.ACC_PROTECTED);

    public boolean willTransform(String name) {
        return true;
    }

    public ClassNode transform(ClassNode node) {
        // Change access for class
        node.access |= changeAccess(node.access);

        // Change access for inner classes
        for (InnerClassNode innerClassNode : node.innerClasses) {
            innerClassNode.access = changeAccess(innerClassNode.access);
        }

        // Change access for methods
        for (MethodNode methodNode : node.methods) {
            methodNode.access = changeAccess(methodNode.access);
        }

        // Change access for fields
        for (FieldNode fieldNode : node.fields) {
            fieldNode.access = changeAccess(fieldNode.access);
        }

        return node;
    }

    private int changeAccess(int nodeAccess) {
        if ((nodeAccess & allAccess) != 0) {
            return (nodeAccess & ~allAccess) | Opcodes.ACC_PUBLIC;
        }

        return nodeAccess;
    }
}
