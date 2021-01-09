package me.dreamhopping.pml.transformers;

import me.dreamhopping.pml.mods.launch.loader.RuntimeTransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.lang.reflect.Method;

public class GuiNewChatTransformer implements RuntimeTransformer {
    @Override
    public boolean willTransform(String name) {
        return name.equals("net/minecraft/client/gui/GuiNewChat");
    }

    @Override
    public ClassNode transform(ClassNode classNode) {
        for (MethodNode methodNode : classNode.methods) {
            if (methodNode.name.equals("printChatMessage")) {
                InsnList list = new InsnList();
                list.add(new VarInsnNode(Opcodes.ALOAD, 1));
                list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "me/dreamhopping/pml/transformers/impl/GuiNewChatTransformerImpl", "printChatMessage", "(Lnet/minecraft/util/IChatComponent;)V"));

                methodNode.instructions.insertBefore(methodNode.instructions.getFirst(), list);
            }
        }

        return classNode;
    }
}
