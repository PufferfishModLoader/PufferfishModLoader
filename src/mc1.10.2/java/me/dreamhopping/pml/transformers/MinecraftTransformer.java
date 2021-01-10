package me.dreamhopping.pml.transformers;

import me.dreamhopping.pml.mods.launch.loader.RuntimeTransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

public class MinecraftTransformer implements RuntimeTransformer {
    public boolean willTransform(String name) {
        return name.equals("net/minecraft/client/Minecraft");
    }

    public ClassNode transform(ClassNode classNode) {
        for (MethodNode methodNode : classNode.methods) {
            if (methodNode.name.equals("createDisplay")) {
                InsnList list = new InsnList();
                list.add(new LdcInsnNode("Minecraft 1.10.2 | PufferfishModLoader (dev/1.0)"));

                // Find the "toString" call and insert before that
                for (AbstractInsnNode node = methodNode.instructions.getLast(); node != null; node = node.getPrevious()) {
                    if (node.getOpcode() == Opcodes.INVOKESTATIC) {
                        MethodInsnNode castedNode = (MethodInsnNode) node;
                        if (castedNode.owner.equals("org/lwjgl/opengl/Display") && castedNode.name.equals("setTitle") && castedNode.desc.equals("(Ljava/lang/String;)V")) {
                            // Remove the previous "Minecraft (version)" LDC
                            methodNode.instructions.remove(node.getPrevious());
                            methodNode.instructions.insertBefore(castedNode, list);
                        }
                    }
                }

                break;
            }
        }

        return classNode;
    }
}
