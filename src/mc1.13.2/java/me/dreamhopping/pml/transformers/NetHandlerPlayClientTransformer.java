package me.dreamhopping.pml.transformers;

import me.dreamhopping.pml.mods.launch.loader.RuntimeTransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

public class NetHandlerPlayClientTransformer implements RuntimeTransformer {
    public boolean willTransform(String name) {
        return name.equals("net/minecraft/client/network/NetHandlerPlayClient");
    }

    public ClassNode transform(ClassNode classNode) {
        for (MethodNode methodNode : classNode.methods) {
            if (methodNode.name.equals("handleJoinGame")) {
                InsnList list = new InsnList();
                list.add(new VarInsnNode(Opcodes.ALOAD, 0));
                list.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/client/network/NetHandlerPlayClient", "netManager", "Lnet/minecraft/network/NetworkManager;"));
                list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "net/minecraft/network/NetworkManager", "isLocalChannel", "()Z"));
                list.add(new VarInsnNode(Opcodes.ALOAD, 0));
                list.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/client/network/NetHandlerPlayClient", "netManager", "Lnet/minecraft/network/NetworkManager;"));
                list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "net/minecraft/network/NetworkManager", "getRemoteAddress", "()Ljava/net/SocketAddress;"));
                list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, getImplementationClass("NetHandlerPlayClient"), "handleJoinGame", "(ZLjava/net/SocketAddress;)V"));

                methodNode.instructions.insert(list);
            } else if (methodNode.name.equals("onDisconnect")) {
                InsnList list = new InsnList();
                list.add(new VarInsnNode(Opcodes.ALOAD, 1));
                list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, getImplementationClass("NetHandlerPlayClient"), "handleDisconnect", "(Lnet/minecraft/util/text/ITextComponent;)V"));

                methodNode.instructions.insert(list);
            }
        }

        return classNode;
    }
}
