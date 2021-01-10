package me.dreamhopping.pml.transformers;

import me.dreamhopping.pml.mods.launch.loader.RuntimeTransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

public class MinecraftClientTransformer implements RuntimeTransformer {
    public boolean willTransform(String name) {
        return name.equals("net/minecraft/client/MinecraftClient");
    }

    public ClassNode transform(ClassNode classNode) {
        for (MethodNode methodNode : classNode.methods) {
            switch (methodNode.name) {
                case "init":
                    InsnList list = new InsnList();
                    list.add(new LdcInsnNode(" | PufferfishModLoader (dev/1.0)"));
                    list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;"));

                    // Find the "toString" call and insert before that
                    for (AbstractInsnNode node = methodNode.instructions.getLast(); node != null; node = node.getPrevious()) {
                        if (node.getOpcode() == Opcodes.INVOKEVIRTUAL) {
                            MethodInsnNode castedNode = (MethodInsnNode) node;
                            if (castedNode.owner.equals("java/lang/StringBuilder") && castedNode.name.equals("toString") && castedNode.desc.equals("()Ljava/lang/String;")) {
                                methodNode.instructions.insertBefore(castedNode, list);
                            }
                        }
                    }

                    break;
                case "render":
                    methodNode.instructions.insert(new MethodInsnNode(Opcodes.INVOKESTATIC, getImplementationClass("MinecraftClient"), "render", "()V"));
                    break;
                case "tick":
                    methodNode.instructions.insert(new MethodInsnNode(Opcodes.INVOKESTATIC, getImplementationClass("MinecraftClient"), "tick", "()V"));
                    break;
            }
        }

        return classNode;
    }
}
