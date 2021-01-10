package me.dreamhopping.pml.transformers;

import me.dreamhopping.pml.mods.launch.loader.RuntimeTransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

public class ClientPlayerEntityTransformer implements RuntimeTransformer {
    @Override
    public boolean willTransform(String name) {
        return name.equals("net/minecraft/client/network/ClientPlayerEntity");
    }

    @Override
    public ClassNode transform(ClassNode classNode) {
        for (MethodNode methodNode : classNode.methods) {
            if (methodNode.name.equals("sendChatMessage")) {
                InsnList list = new InsnList();
                list.add(new VarInsnNode(Opcodes.ALOAD, 1));
                list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "me/dreamhopping/pml/transformers/impl/ClientPlayerEntityTransformerImpl", "sendChatMessage", "(Ljava/lang/String;)V"));

                methodNode.instructions.insertBefore(methodNode.instructions.getFirst(), list);
            } else if (methodNode.name.equals("dropSelectedItem")) {
                InsnList list = new InsnList();
                list.add(new VarInsnNode(Opcodes.ILOAD, 1));
                list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "me/dreamhopping/pml/transformers/impl/ClientPlayerEntityTransformerImpl", "dropSelectedItem", "(Z)V"));

                methodNode.instructions.insertBefore(methodNode.instructions.getFirst(), list);
            }
        }

        return classNode;
    }
}
