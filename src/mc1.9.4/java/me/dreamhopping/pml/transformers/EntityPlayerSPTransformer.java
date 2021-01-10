package me.dreamhopping.pml.transformers;

import me.dreamhopping.pml.mods.launch.loader.RuntimeTransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

public class EntityPlayerSPTransformer implements RuntimeTransformer {
    @Override
    public boolean willTransform(String name) {
        return name.equals("net/minecraft/client/entity/EntityPlayerSP");
    }

    @Override
    public ClassNode transform(ClassNode classNode) {
        for (MethodNode methodNode : classNode.methods) {
            if (methodNode.name.equals("sendChatMessage")) {
                InsnList list = new InsnList();
                list.add(new VarInsnNode(Opcodes.ALOAD, 1));
                list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "me/dreamhopping/pml/transformers/impl/EntityPlayerSPTransformerImpl", "sendChatMessage", "(Ljava/lang/String;)V"));

                methodNode.instructions.insertBefore(methodNode.instructions.getFirst(), list);
            }
        }

        return classNode;
    }
}
