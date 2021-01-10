package me.dreamhopping.pml.transformers;

import me.dreamhopping.pml.mods.launch.loader.RuntimeTransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

public class EntityPlayerSPTransformer implements RuntimeTransformer {
    public boolean willTransform(String name) {
        return name.equals("net/minecraft/client/entity/EntityPlayerSP");
    }

    public ClassNode transform(ClassNode classNode) {
        for (MethodNode methodNode : classNode.methods) {
            switch (methodNode.name) {
                case "sendChatMessage": {
                    InsnList list = new InsnList();
                    list.add(new VarInsnNode(Opcodes.ALOAD, 1));
                    list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, getImplementationClass("EntityPlayerSP"), "sendChatMessage", "(Ljava/lang/String;)V"));

                    methodNode.instructions.insert(list);
                    break;
                }
                case "dropOneItem": {
                    InsnList list = new InsnList();
                    list.add(new VarInsnNode(Opcodes.ILOAD, 1));
                    list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, getImplementationClass("EntityPlayerSP"), "dropItem", "(Z)V"));

                    methodNode.instructions.insert(list);
                    break;
                }
                case "respawnPlayer": {
                    methodNode.instructions.insert(new MethodInsnNode(Opcodes.INVOKESTATIC, getImplementationClass("EntityPlayerSP"), "respawn", "()V"));
                    break;
                }
            }
        }

        return classNode;
    }
}
