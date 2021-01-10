package me.dreamhopping.pml.transformers;

import me.dreamhopping.pml.mods.launch.loader.RuntimeTransformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class MainWindowTransformer implements RuntimeTransformer {
    public boolean willTransform(String name) {
        return name.equals("net/minecraft/client/MainWindow");
    }

    public ClassNode transform(ClassNode classNode) {
        for (MethodNode methodNode : classNode.methods) {
            if (methodNode.name.equals("<init>")) {
                for (AbstractInsnNode node = methodNode.instructions.getLast(); node != null; node = node.getPrevious()) {
                    if (node instanceof LdcInsnNode) {
                        LdcInsnNode castedNode = (LdcInsnNode) node;
                        if (castedNode.cst.equals("Minecraft 1.13.2")) {
                            methodNode.instructions.set(node, new LdcInsnNode("Minecraft 1.13.2 | PufferfishModLoader (dev/1.0)"));
                        }
                    }
                }

                break;
            }
        }

        return classNode;
    }
}
