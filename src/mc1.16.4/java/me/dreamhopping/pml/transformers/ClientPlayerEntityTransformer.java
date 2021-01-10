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
            switch (methodNode.name) {
                case "sendChatMessage": {
                    InsnList list = new InsnList();
                    list.add(new VarInsnNode(Opcodes.ALOAD, 1));
                    list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "me/dreamhopping/pml/transformers/impl/ClientPlayerEntityTransformerImpl", "sendChatMessage", "(Ljava/lang/String;)V"));

                    methodNode.instructions.insert(list);
                    break;
                }
                case "dropSelectedItem": {
                    InsnList list = new InsnList();
                    list.add(new VarInsnNode(Opcodes.ILOAD, 1));
                    list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "me/dreamhopping/pml/transformers/impl/ClientPlayerEntityTransformerImpl", "dropSelectedItem", "(Z)V"));

                    methodNode.instructions.insert(list);
                    break;
                }
                case "requestRespawn": {
                    methodNode.instructions.insert(new MethodInsnNode(Opcodes.INVOKESTATIC, "me/dreamhopping/pml/transformers/impl/ClientPlayerEntityTransformerImpl", "respawn", "()V"));
                }
            }
        }

        return classNode;
    }
}
