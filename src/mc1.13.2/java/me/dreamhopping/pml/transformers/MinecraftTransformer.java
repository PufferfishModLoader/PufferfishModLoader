package me.dreamhopping.pml.transformers;

import me.dreamhopping.pml.mods.launch.loader.RuntimeTransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class MinecraftTransformer implements RuntimeTransformer {
    public boolean willTransform(String name) {
        return name.equals("net/minecraft/client/Minecraft");
    }

    public ClassNode transform(ClassNode classNode) {
        for (MethodNode methodNode : classNode.methods) {
            if (methodNode.name.equals("runTick")) {
                methodNode.instructions.insert(new MethodInsnNode(Opcodes.INVOKESTATIC, getImplementationClass("Minecraft"), "runTick", "()V"));
            } else if (methodNode.name.equals("runGameLoop")) {
                methodNode.instructions.insert(new MethodInsnNode(Opcodes.INVOKESTATIC, getImplementationClass("Minecraft"), "runGameLoop", "()V"));
            }
        }
        return classNode;
    }
}
